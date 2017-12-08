package sadman.volme;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;



public class ThisWeekFragment extends Fragment{

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private FirebaseListAdapter<Event> mAdapter;
    private ListView mEventListView;

    public ThisWeekFragment(){
        //requires empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.eventcard_list, container, false);

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");

        // Initialize references to views
        mEventListView = (ListView) rootView.findViewById(R.id.eventcard_list);


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
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }



}