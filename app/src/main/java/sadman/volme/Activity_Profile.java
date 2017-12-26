package sadman.volme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sadman on 11/12/17.
 */

public class Activity_Profile extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;

    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String name;
    private String email;
    private String userkey;
    private FirebaseAuth mFirebaseAuth;
    private String usertel;


    // это будет именем файла настроек
    public static final String Newly_added_key = "newlyaddedkey";
    public static final String Newly_added_keyx = "newlyaddedkeyx"; // имя кота

    SharedPreferences mnewlyaddedkey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_profile);

        mnewlyaddedkey = getSharedPreferences(Newly_added_key, Context.MODE_PRIVATE);

        mFirebaseAuth =FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User signed in
                    // Name, email address, and profile photo Url
                    name = user.getDisplayName();
                    email = user.getEmail();

                    if(mnewlyaddedkey.contains(Newly_added_keyx)) {
                        userkey = mnewlyaddedkey.getString(Newly_added_keyx, "");
                    }


                    ImageView bonuses = (ImageView) findViewById(R.id.bonuses_button);
                    bonuses.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent add_event_form = new Intent(Activity_Profile.this, Activity_Bonuses.class);
                            startActivity(add_event_form);
                            overridePendingTransition(0, 0);
                        }
                    });

                    ImageView gethome = (ImageView) findViewById(R.id.get_home_button);
                    gethome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent add_event_form = new Intent(Activity_Profile.this, Activity_home.class);
                            startActivity(add_event_form);
                            overridePendingTransition(0, 0);
                        }
                    });

                    ImageView addevent = (ImageView) findViewById(R.id.open_event_addform);
                    addevent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent add_event_form = new Intent(Activity_Profile.this, AddEventForm.class);
                            startActivity(add_event_form);
                            overridePendingTransition(0, 0);
                        }
                    });


                    final TextView telephone_textview = (TextView) findViewById(R.id.profile_tel);
                    final EditText telephone_edittext = (EditText) findViewById(R.id.profile_tel_edit);
                    final Button change_info = (Button) findViewById(R.id.change_info_button);
                    final Button save_info = (Button) findViewById(R.id.save_info_button);
                    change_info.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            telephone_edittext.setText(usertel, TextView.BufferType.EDITABLE);
                            telephone_textview.setVisibility(View.INVISIBLE);
                            telephone_edittext.setVisibility(View.VISIBLE);
                            telephone_edittext.setText(telephone_textview.getText().toString());
                            change_info.setVisibility(View.INVISIBLE);
                            save_info.setVisibility(View.VISIBLE);
                        }
                    });

                    save_info.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(telephone_edittext.getText().length() == 10){
                            usertel = telephone_edittext.getText().toString();
                            mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("user").child(userkey).child("user_tel");
                            mDatabaseRef.removeValue();
                            mDatabaseRef.setValue(usertel);
                            telephone_textview.setText(usertel);
                            } else {
                                Toast.makeText(Activity_Profile.this, "Please, enter correct number", Toast.LENGTH_SHORT).show();
                            }


                            telephone_edittext.setVisibility(View.INVISIBLE);
                            telephone_textview.setVisibility(View.VISIBLE);
                            save_info.setVisibility(View.INVISIBLE);
                            change_info.setVisibility(View.VISIBLE);

                        }
                    });


                    TextView name_textview = (TextView) findViewById(R.id.profile_full_name);
                    name_textview.setText(name);

                    TextView email_textview = (TextView) findViewById(R.id.profile_email);
                    email_textview.setText(email);


                    DatabaseReference telref = FirebaseDatabase.getInstance().getReference().child("user").child(userkey).child("user_tel");
                    telref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue(String.class) != "") {
                                telephone_textview.setText(dataSnapshot.getValue(String.class));
                                telephone_edittext.setText(dataSnapshot.getValue(String.class));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        };
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
