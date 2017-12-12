package sadman.volme;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;


    private ImageView delete_event;
    private ImageView like_event;
    private ImageView like_event_full;
    private TextView event_title;
    private TextView event_organizator;
    private TextView event_description;
    private TextView event_data;
    private TextView event_location;
    private TextView event_tag;
    private String event_key;
    private String user_Uid;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");


        // Initialize references to views
        delete_event = findViewById(R.id.delete_event);
        event_title = findViewById(R.id.name);
        event_organizator = findViewById(R.id.organizator_text);
        event_description = findViewById(R.id.event_description);
        event_data = findViewById(R.id.calendar_text);
        event_location = findViewById(R.id.location_textview);
        event_tag = findViewById(R.id.tag_text);
        like_event = findViewById(R.id.like);
        like_event_full = findViewById(R.id.like_full);


        event_key = getIntent().getStringExtra("key");

        delete_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventsDatabaseReference.child(event_key).removeValue();
                finish();

            }
        });


        mFirebaseAuth = FirebaseAuth.getInstance();
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


        //set like button to add users Uid to event's database
        like_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventsDatabaseReference.child(event_key).child("subscribers").setValue(user_Uid.toString());
                like_event.setVisibility(View.INVISIBLE);
                like_event_full.setVisibility(View.VISIBLE);
                Toast.makeText(EventActivity.this, "subscribed", Toast.LENGTH_LONG).show();}

        });

        //set like button to remove users Uid to event's database
        like_event_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventsDatabaseReference.child(event_key).child("subscribers").removeValue();
                like_event.setVisibility(View.VISIBLE);
                like_event_full.setVisibility(View.INVISIBLE);

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
