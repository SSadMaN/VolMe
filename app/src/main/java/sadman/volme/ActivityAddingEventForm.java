package sadman.volme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by sadman on 05/12/17.
 * Edited by Nikita Kiselov on 25/02/18
 */

public class ActivityAddingEventForm extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private DatabaseReference mDatafer;
    private FirebaseAuth mFirebaseAuth;




    private DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar;
    private EditText mEditEventTitle;
    private EditText mEditEventOrgName;
    private EditText mEditEventDescription;
    private EditText mEditEventDate;
    private Spinner mEditEventTag;
    private EditText mEditEventLocation;
    private String organizatorUid;
    private String newEventKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent_form);

        //TODO delete this code
       // mnewlyaddedkey = getSharedPreferences(Newly_added_key, Context.MODE_PRIVATE);

//        if(mnewlyaddedkey.contains(Newly_added_keyx)) {
//            userkey = mnewlyaddedkey.getString(Newly_added_keyx, "");
//        }

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        //getting organizer UID
        organizatorUid = user.getUid();




        // Initialize references to views
        mEditEventTitle = findViewById(R.id.add_event_title_name);
        mEditEventOrgName = findViewById(R.id.add_organization_name);
        mEditEventDescription = findViewById(R.id.add_event_description);
        mEditEventDate = findViewById(R.id.add_event_date);
        mEditEventTag = findViewById(R.id.add_event_tag);
        mEditEventLocation = findViewById(R.id.add_event_location);

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
                new DatePickerDialog(ActivityAddingEventForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        Button mButton = findViewById(R.id.submit_event_form);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:set all fields on click
                if(organizatorUid != null) {
                    Event event = new Event(mEditEventOrgName.getText().toString(), mEditEventTitle.getText().toString(), mEditEventDescription.getText().toString(), mEditEventDate.getText().toString(), mEditEventLocation.getText().toString(), mEditEventTag.getSelectedItem().toString());
                    if(mEditEventOrgName.getText().toString().matches("") || mEditEventTitle.getText().toString().matches("") || mEditEventDescription.getText().toString().matches("") || mEditEventDate.getText().toString().matches("") || mEditEventLocation.getText().toString().matches("")){
                        Toast.makeText(ActivityAddingEventForm.this, "Please, fill all fields", Toast.LENGTH_SHORT).show();
                    } else {

                        mEventsDatabaseReference.push().setValue(event, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                newEventKey = databaseReference.getKey();
                                FirebaseDatabase.getInstance().getReference().child("events").child(newEventKey).child("event_organizator").child("org_uid").setValue(organizatorUid);

                                Intent intentToMainMenu = new Intent(ActivityAddingEventForm.this, ActivityHome.class);
                                startActivity(intentToMainMenu);
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
        Spinner spinner = findViewById(R.id.add_event_tag);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.events_array, R.layout.simple_tag_spinner);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //TODO what is this
        spinner.setPrompt("Select your favorite Planet!");

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mEditEventDate.setText(sdf.format(myCalendar.getTime()));
    }

    //TODO delete this code
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }
}
