package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sadman on 13/12/17.
 */

public class List_Subscribers extends AppCompatActivity {

    private DatabaseReference mSubscribersDatabaseReference;
    private FirebaseListAdapter<User> mAdapter;
    private ListView mSubscribersListView;

    private String event_ukey;
    private String sub_uId;
    private String sub_key;
    private ImageView sub_imgview;

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

        mSubscribersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                sub_key = mAdapter.getRef(position).getKey();
                mSubscribersDatabaseReference.child(sub_key).child("userUid").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        sub_uId = dataSnapshot.getValue(String.class);
                        Toast.makeText(List_Subscribers.this, sub_uId, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });

                Intent event_deployed_activity = new Intent(List_Subscribers.this, Activity_Sub_Profile.class);

                event_deployed_activity.putExtra("Sub_uId", sub_uId);

                startActivity(event_deployed_activity);
                List_Subscribers.this.overridePendingTransition(0, 0);
            }
        });
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
