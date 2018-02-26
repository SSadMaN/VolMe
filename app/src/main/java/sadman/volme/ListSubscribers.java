package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by sadman on 13/12/17.
 * Edited by Nikita Kiselov 25/02/18
 */

public class ListSubscribers extends AppCompatActivity {

    private DatabaseReference mSubscribersDatabaseReference;
    private FirebaseListAdapter<User> mAdapter;
    private ListView mSubscribersListView;

    private String eventKey;
    private String subscriberUid;
    private String subscriberInListKey;
    private ImageView subscriberImgageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribers_list);
        eventKey = getIntent().getStringExtra("eventKey");

        //Initialize Firebase components
        //TODO: retrieve user id from events/eventID/subscribers!!!!
        mSubscribersDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("events").child(eventKey).child("subscribers");


        // Initialize references to views
        mSubscribersListView = findViewById(R.id.subscribers_list_listview);


        //-------------------------Setting firebase list adapter------------------------------
        Query query = mSubscribersDatabaseReference;

        FirebaseListOptions<User> options =
                new FirebaseListOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .setLayout(R.layout.item_user)
                        .build();
        mAdapter = new FirebaseListAdapter<User>(options) {
            @Override
            protected void populateView(View v, User user, int position) {
                ((TextView) v.findViewById(R.id.user_name_textview)).setText(user.getName());
                if(user.getUserImageURL() != null) {
                    //TODO Add placeholder
                    Picasso.with(ListSubscribers.this).load(user.getUserImageURL())
                            .into((ImageView) v.findViewById(R.id.user_logo_imageview));
                }
            }
        };

        mSubscribersListView.setAdapter(mAdapter);
        //------------------------------------------------------------------------------------

        //Setting listener to click and intent to sub-profile
        mSubscribersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                subscriberInListKey = mAdapter.getRef(position).getKey();
                mSubscribersDatabaseReference.child(subscriberInListKey).child("userUid").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        subscriberUid = dataSnapshot.getValue(String.class);
                        startSubscriberProfileIntent();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

    }

    private void startSubscriberProfileIntent() {

        //-------------------Creating and start SubscriberProfile intent--------------------
        Intent intentSubscriberProfile = new Intent(ListSubscribers.this, ActivitySubscriberProfile.class);

        intentSubscriberProfile.putExtra("SubscriberUid", subscriberUid);

        startActivity(intentSubscriberProfile);
        ListSubscribers.this.overridePendingTransition(0, 0);
        //----------------------------------------------------------------------------------
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
