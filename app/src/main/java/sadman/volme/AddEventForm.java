package sadman.volme;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by sadman on 05/12/17.
 */

public class AddEventForm extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private DatabaseReference mDatafer;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;




    private DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar;
    private EditText mEditEventTitle;
    private EditText mEditEventOrgName;
    private EditText mEditEventDescription;
    private EditText mEditEventDate;
    private Spinner mEditEventTag;
    private EditText mEditEventLocation;
    private String organizator_Uid;
    private String new_event_key;
    private String org_tel;
    private String userkey;


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEditEventDate.setText(sdf.format(myCalendar.getTime()));
    }

    // это будет именем файла настроек
    public static final String Newly_added_key = "newlyaddedkey";
    public static final String Newly_added_keyx = "newlyaddedkeyx"; // имя кота
    private static final int RC_SIGN_IN = 1;

    SharedPreferences mnewlyaddedkey;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent_form);

        mnewlyaddedkey = getSharedPreferences(Newly_added_key, Context.MODE_PRIVATE);

        if(mnewlyaddedkey.contains(Newly_added_keyx)) {
            userkey = mnewlyaddedkey.getString(Newly_added_keyx, "");
        }

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatafer = FirebaseDatabase.getInstance().getReference().child("user").child(userkey).child("user_tel");


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    organizator_Uid = user.getUid();
                }
            }
        };



        // Initialize references to views
        mEditEventTitle = (EditText) findViewById(R.id.add_event_title_name);
        mEditEventOrgName = (EditText) findViewById(R.id.add_organization_name);
        mEditEventDescription = (EditText) findViewById(R.id.add_event_description);
        mEditEventDate = (EditText) findViewById(R.id.add_event_date);
        mEditEventTag = (Spinner) findViewById(R.id.add_event_tag);
        mEditEventLocation = (EditText) findViewById(R.id.add_event_location);

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mEditEventDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddEventForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        Button mButton = (Button) findViewById(R.id.submit_event_form);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:set all fields on click
                if(organizator_Uid != null) {
                    Event oneevent = new Event(mEditEventOrgName.getText().toString(), mEditEventTitle.getText().toString(), mEditEventDescription.getText().toString(), mEditEventDate.getText().toString(), mEditEventLocation.getText().toString(), mEditEventTag.getSelectedItem().toString());
                    if(mEditEventOrgName.getText().toString().matches("") || mEditEventTitle.getText().toString().matches("") || mEditEventDescription.getText().toString().matches("") || mEditEventDate.getText().toString().matches("") || mEditEventLocation.getText().toString().matches("")){
                        Toast.makeText(AddEventForm.this, "Please, fill all fields", Toast.LENGTH_SHORT).show();
                    } else {

                        mEventsDatabaseReference.push().setValue(oneevent, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                new_event_key = databaseReference.getKey();
                                FirebaseDatabase.getInstance().getReference().child("events").child(new_event_key).child("event_organizator").child("org_uid").setValue(organizator_Uid);
                                FirebaseDatabase.getInstance().getReference().child("events").child(new_event_key).child("event_organizator").child("org_userkey").setValue(userkey);

                                Intent go_back_to_main_menu = new Intent(AddEventForm.this, Activity_home.class);
                                startActivity(go_back_to_main_menu);
                                overridePendingTransition(0, 0);


                                // Clear input box
                                mEditEventTitle.setText("");
                                mEditEventOrgName.setText("");
                                mEditEventDescription.setText("");
                                mEditEventDate.setText("");
                                mEditEventLocation.setText("");
                            }
                        });
                    }
                }
            }
        });

//spinner


        Spinner spinner = (Spinner) findViewById(R.id.add_event_tag);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.events_array, R.layout.simple_tag_spinner);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setPrompt("Select your favorite Planet!");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
