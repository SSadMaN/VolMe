package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sadman on 03/12/17.
 */

public class Organizator_main extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private ChildEventListener mChildEventListener;

    private EventsAdapter mEventCardsAdapter;
    private ListView mEventListView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizator_main);

        ImageView swither_org = (ImageView) findViewById(R.id.switch_button);
        swither_org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent org_mainIntent = new Intent(Organizator_main.this, home.class);
                startActivity(org_mainIntent);
                overridePendingTransition(0, 0);
            }
        });

        ImageView addeventform = (ImageView) findViewById(R.id.open_event_addform);
        addeventform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_event_form = new Intent(Organizator_main.this, AddEventForm.class);
                startActivity(add_event_form);
                overridePendingTransition(0, 0);
            }
        });


        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");


        // Initialize references to views
        mEventListView = (ListView) findViewById(R.id.org_listview);


        // Initialize message ListView and its adapter
        final List<Event> newevent = new ArrayList<>();
        mEventCardsAdapter = new EventsAdapter(this, R.layout.item_event, newevent);
        mEventListView.setAdapter(mEventCardsAdapter);


        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event newevent = dataSnapshot.getValue(Event.class);
                mEventCardsAdapter.add(newevent);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        mEventsDatabaseReference.addChildEventListener(mChildEventListener);
    }
}
