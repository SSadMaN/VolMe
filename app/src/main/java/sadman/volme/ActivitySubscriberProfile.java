package sadman.volme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by sadman on 18/12/17.
 * Edited by Nikita Kiselov 25/02/18
 */

public class ActivitySubscriberProfile extends AppCompatActivity {

    private DatabaseReference mUserRef;

    private String userUid; //userUid got from sublist
    private String userName;
    private String userEmail;
    private String profileImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_profile);

        userUid = getIntent().getStringExtra("SubscriberUid");
        mUserRef = FirebaseDatabase.getInstance().getReference().child("user");

        //getting references for views
        final TextView nameTextView = (TextView) findViewById(R.id.profile_full_name);
        final TextView emailTextView = findViewById(R.id.profile_email);
        final ImageView profileImageView = findViewById(R.id.profile_img);


        // get subscriber's name
        mUserRef.child(userUid).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userName = dataSnapshot.getValue(String.class);
                nameTextView.setText(userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //get subscriber's email
        mUserRef.child(userUid).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userEmail = dataSnapshot.getValue(String.class);
                emailTextView.setText(userEmail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //get subscriber's photo
        mUserRef.child(userUid).child("userImageURL").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profileImageUri = dataSnapshot.getValue(String.class);
                if(profileImageUri != null) {
                    Picasso.with(ActivitySubscriberProfile.this).load(profileImageUri)
                            .into(profileImageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
