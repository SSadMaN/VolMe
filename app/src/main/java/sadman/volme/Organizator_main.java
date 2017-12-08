package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
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

    private static final String TAG = "Organizator_main";




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

        mEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent event_deployed_activity = new Intent(Organizator_main.this, EventActivity.class);
                startActivity(event_deployed_activity);
                overridePendingTransition(0, 0);
            }
        });


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
                Log.d(TAG,"dataREMOVE "+ dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        mEventsDatabaseReference.addChildEventListener(mChildEventListener);





       /* RelativeLayout event_card_relative_layout = (RelativeLayout) findViewById(R.id.item_event_relative_layout);
        event_card_relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent event_deployed_activity = new Intent(Organizator_main.this, EventActivity.class);
                startActivity(event_deployed_activity);
                overridePendingTransition(0, 0);
            }
        });*/


       /* //swipe refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this); */
    }


   /* @Override
    public void onRefresh() {
        setContentView(R.layout.organizator_main);

    }*/
}
