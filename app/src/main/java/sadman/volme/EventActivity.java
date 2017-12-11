package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;



    private Event newevent;
    private TextView event_title;
    private TextView event_organizator;
    private TextView event_description;
    private TextView event_data;
    private TextView event_location;
    private TextView event_tag;
    private String event_key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");

        // Initialize references to views
        event_title = findViewById(R.id.name);
        event_organizator = findViewById(R.id.organizator_text);
        event_description = findViewById(R.id.event_description);
        event_data = findViewById(R.id.calendar_text);
        event_location = findViewById(R.id.location_textview);
        event_tag = findViewById(R.id.tag_text);

        event_key = getIntent().getStringExtra("key");

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
    }


}