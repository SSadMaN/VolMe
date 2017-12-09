package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by sadman on 03/12/17.
 */

public class Organizator_main extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private FirebaseListAdapter<Event> mAdapter;

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

        Query query = FirebaseDatabase.getInstance().getReference().child("events");

        FirebaseListOptions<Event> options =
                new FirebaseListOptions.Builder<Event>()
                        .setQuery(query, Event.class)
                        .setLayout(R.layout.item_event)
                        .build();
        mAdapter = new FirebaseListAdapter<Event>(options){
            @Override
            protected void populateView(View v, Event eventview, int position) {
                ((TextView)v.findViewById(R.id.event_oranisation_name)).setText(eventview.getOrganization_name());
                ((TextView)v.findViewById(R.id.event_title_name)).setText(eventview.getEvent_title());
                ((TextView)v.findViewById(R.id.event_quick_description)).setText(eventview.getEvent_description());
                ((TextView)v.findViewById(R.id.event_date_textview)).setText(eventview.getEvent_data());
                ((TextView)v.findViewById(R.id.location_textview)).setText(eventview.getEvent_location());
                ((TextView)v.findViewById(R.id.tag_small_textview)).setText(eventview.getEvent_tag());
            }


        };

        mEventListView.setAdapter(mAdapter);

        mEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String itemkey = mAdapter.getRef(position).getKey();

                Intent event_deployed_activity = new Intent(Organizator_main.this, EventActivity.class);

                event_deployed_activity.putExtra("key", itemkey);

                startActivity(event_deployed_activity);
                overridePendingTransition(0, 0);
            }
        });
    }

   @Override
   protected void onStart() {
       super.onStart();
       mAdapter.startListening();
   }


    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}
