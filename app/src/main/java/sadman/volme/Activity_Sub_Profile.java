package sadman.volme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sadman on 18/12/17.
 */

public class Activity_Sub_Profile extends AppCompatActivity {

    private DatabaseReference mUserRef;

    private String user_Uid; //useruid got from sublist
    private String user_key;
    private String user_name;
    private String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_profile);

        user_Uid = getIntent().getStringExtra("Sub_uId");
        mUserRef = FirebaseDatabase.getInstance().getReference().child("user");



        mUserRef = FirebaseDatabase.getInstance().getReference().child("users");

        Query query1 = mUserRef.orderByChild("userUid").equalTo(user_Uid);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(user_Uid != null){
                    user_key = dataSnapshot.getRef().child("users").getKey();
                    Toast.makeText(Activity_Sub_Profile.this, user_key, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // get subscriber's name
        /*mUserRef.child(user_key).child("user_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { user_name = dataSnapshot.getValue(String.class);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //get subscriber's email
        mUserRef.child(user_key).child("user_email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { user_email = dataSnapshot.getValue(String.class);}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        TextView name_textview = (TextView) findViewById(R.id.profile_full_name);
        name_textview.setText(user_name);

        TextView email_textview = findViewById(R.id.profile_email);
        email_textview.setText(user_email);*/
    }
}
