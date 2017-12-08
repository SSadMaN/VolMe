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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class ThisWeekFragment extends Fragment{

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private ChildEventListener mChildEventListener;

    private EventsAdapter mEventCardsAdapter;
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


        // Initialize message ListView and its adapter
        final List<Event> newevent = new ArrayList<>();
        mEventCardsAdapter = new EventsAdapter(getContext(), R.layout.item_event, newevent);
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
        return rootView;
    }



}