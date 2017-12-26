package sadman.volme;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class Activity_Event extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private FirebaseAuth mFirebaseAuth;

    private DatabaseReference users;



    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private ImageView delete_event;
    private Button like_event;
    private Button like_event_full;
    private Button call_org;
    private TextView event_title;
    private TextView event_organizator;
    private TextView event_description;
    private TextView event_data;
    private TextView event_location;
    private TextView event_tag;
    private TextView subscribers_list;
    private String username;
    private String event_key;
    private String user_Uid;
    private String newly_added_user_key;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private CountDownTimer timer;
    private String userkeyyx;
    private String org_teleph;
    private String org_userkey;

    // это будет именем файла настроек
    public static final String Newly_added_key = "newlyaddedkey";
    public static final String Newly_added_keyx = "newlyaddedkeyx"; // имя кота
    private static final int RC_SIGN_IN = 1;

    SharedPreferences mnewlyaddedkey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mnewlyaddedkey = getSharedPreferences(Newly_added_key, Context.MODE_PRIVATE);

        event_key = getIntent().getStringExtra("event_key");
        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");
        mFirebaseAuth = FirebaseAuth.getInstance();


        // Initialize references to views
        call_org = findViewById(R.id.call_start_button);
        delete_event = findViewById(R.id.delete_event);
        event_title = findViewById(R.id.name);
        event_organizator = findViewById(R.id.organizator_text);
        event_description = findViewById(R.id.event_description);
        event_data = findViewById(R.id.calendar_text);
        event_location = findViewById(R.id.location_textview);
        event_tag = findViewById(R.id.tag_text);
        like_event = findViewById(R.id.like);
        like_event_full = findViewById(R.id.like_full);
        subscribers_list = findViewById(R.id.subscribers_list_button);


        delete_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventsDatabaseReference.child(event_key).removeValue();
                finish();

            }
        });




        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // Check if user's email is verified
                    // boolean emailVerified = user.isEmailVerified();

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    user_Uid = user.getUid();
                    username = user.getDisplayName();

                    Query pizda = mEventsDatabaseReference.child(event_key).orderByChild("org_uid").equalTo(user_Uid);
                    pizda.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.exists()){
                                delete_event.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                   Query query = mEventsDatabaseReference.child(event_key).child("subscribers").orderByChild("userUid").equalTo(user_Uid);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                like_event.setVisibility(View.INVISIBLE);
                                like_event_full.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }
        };


        // Event Title
        DatabaseReference titleref = mEventsDatabaseReference.child(event_key).child("event_title");
        titleref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                event_title.setText(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // Event Organizator name
        DatabaseReference org_nameref = mEventsDatabaseReference.child(event_key).child("organization_name");
        org_nameref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                event_organizator.setText(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // Event description
        DatabaseReference ev_descriptionref = mEventsDatabaseReference.child(event_key).child("event_description");
        ev_descriptionref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                event_description.setText(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // Event date
        DatabaseReference dataref = mEventsDatabaseReference.child(event_key).child("event_data");
        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                event_data.setText(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // Event location
        DatabaseReference locref = mEventsDatabaseReference.child(event_key).child("event_location");
        locref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                event_location.setText(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //Event tag
        DatabaseReference tagref = mEventsDatabaseReference.child(event_key).child("event_tag");
        tagref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                event_tag.setText(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // set buttons initial visibility
        users = mEventsDatabaseReference.child(event_key).child("subscribers");
        timer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user_Uid).exists()){
                            like_event.setVisibility(View.INVISIBLE);
                            like_event_full.setVisibility(View.VISIBLE);
                        } else {
                            like_event.setVisibility(View.VISIBLE);
                            like_event_full.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };



        // check if user is already subscribed



        //set like button to add users Uid to event's database
        like_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User newSub = new User(username,null,user_Uid);
                mEventsDatabaseReference.child(event_key).child("subscribers").push().setValue(newSub, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        newly_added_user_key = databaseReference.getKey();
                        SharedPreferences.Editor editor = mnewlyaddedkey.edit();
                        editor.putString(Newly_added_keyx, newly_added_user_key);
                        editor.apply();
                    }
                });
                like_event.setVisibility(View.INVISIBLE);
                like_event_full.setVisibility(View.VISIBLE);
               }

        });

        //set like button to remove users Uid to event's database
        like_event_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mnewlyaddedkey.contains(Newly_added_keyx)) {
                    userkeyyx = mnewlyaddedkey.getString(Newly_added_keyx, "");
                }
                mEventsDatabaseReference.child(event_key).child("subscribers").child(userkeyyx).removeValue();
                like_event.setVisibility(View.VISIBLE);
                like_event_full.setVisibility(View.INVISIBLE);

            }
        });

        subscribers_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent subscribers_list_intent = new Intent(Activity_Event.this, List_Subscribers.class);
                subscribers_list_intent.putExtra("event_key", event_key);
                startActivity(subscribers_list_intent);
            }
        });

        call_org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference userkey_ref = mEventsDatabaseReference.child(event_key).child("event_organizator").child("org_userkey");
                userkey_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        org_userkey = dataSnapshot.getValue(String.class);
                        DatabaseReference tel_ref = FirebaseDatabase.getInstance().getReference().child("user").child(org_userkey).child("user_tel");
                        tel_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                org_teleph = dataSnapshot.getValue(String.class);
                                if ( ContextCompat.checkSelfPermission( getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                    callIntent.setData(Uri.parse("tel:"+org_teleph));
                                    startActivity(callIntent);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
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
