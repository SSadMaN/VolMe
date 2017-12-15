package sadman.volme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by sadman on 13/12/17.
 */

public class List_Subscribers extends AppCompatActivity {

    private DatabaseReference mSubscribersDatabaseReference;
    private FirebaseListAdapter<User> mAdapter;
    private ListView mSubscribersListView;

    private String event_ukey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribers_list);


        event_ukey = getIntent().getStringExtra("event_key");
        //Initialize Firebase components
        //TODO: retrieve user id from events/eventID/subscribers!!!!
        mSubscribersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("events").child(event_ukey).child("subscribers");


        // Initialize references to views
        mSubscribersListView = (ListView) findViewById(R.id.subscribers_list_listview);


        Query query = mSubscribersDatabaseReference;

        FirebaseListOptions<User> options =
                new FirebaseListOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .setLayout(R.layout.item_user)
                        .build();
        mAdapter = new FirebaseListAdapter<User>(options){
            @Override
            protected void populateView(View v, User userview, int position) {
                ((TextView)v.findViewById(R.id.user_name_textview)).setText(userview.getUser_name());
            }
        };

        mSubscribersListView.setAdapter(mAdapter);
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
