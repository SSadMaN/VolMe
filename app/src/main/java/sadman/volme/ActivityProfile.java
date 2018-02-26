package sadman.volme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * Created by sadman on 11/12/17.
 */

public class ActivityProfile extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;


    private String name;
    private String email;
    private Uri profileImage;
    private String userUID;
    private FirebaseAuth mFirebaseAuth;
    private String userTelephoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_profile);

        final TextView telephoneTextview = findViewById(R.id.profile_tel);
        final EditText telephoneEdittext = findViewById(R.id.profile_tel_edit);
        final Button changeInfo = findViewById(R.id.change_info_button);
        final Button saveInfo = findViewById(R.id.save_info_button);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        // User signed in
        // Name, email address, and profile photo Url
        name = user.getDisplayName();
        email = user.getEmail();
        userUID = user.getUid();
        profileImage = user.getPhotoUrl();
        //==============================Setting intents for menu=============================
        ImageView bonuses = findViewById(R.id.bonuses_button);
        bonuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_event_form = new Intent(ActivityProfile.this, ActivityBonuses.class);
                startActivity(add_event_form);
                overridePendingTransition(0, 0);
            }
        });

        ImageView gethome = findViewById(R.id.get_home_button);
        gethome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_event_form = new Intent(ActivityProfile.this, ActivityHome.class);
                startActivity(add_event_form);
                overridePendingTransition(0, 0);
            }
        });

        ImageView addevent = findViewById(R.id.openEventAddForm);
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_event_form = new Intent(ActivityProfile.this, ActivityAddingEventForm.class);
                startActivity(add_event_form);
                overridePendingTransition(0, 0);
            }
        });
        //====================================================================================

        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telephoneEdittext.setText(userTelephoneNumber, TextView.BufferType.EDITABLE);
                telephoneTextview.setVisibility(View.INVISIBLE);
                telephoneEdittext.setVisibility(View.VISIBLE);
                telephoneEdittext.setText(telephoneTextview.getText().toString());
                changeInfo.setVisibility(View.INVISIBLE);
                saveInfo.setVisibility(View.VISIBLE);
            }
        });

        //TODO додати нормальну перевірку номеру
        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (telephoneEdittext.getText().length() == 10) {
                    userTelephoneNumber = telephoneEdittext.getText().toString();
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("user").child(userUID).child("user_tel");
                    mDatabaseRef.removeValue();
                    mDatabaseRef.setValue(userTelephoneNumber);
                    telephoneTextview.setText(userTelephoneNumber);
                } else {
                    Toast.makeText(ActivityProfile.this, "Please, enter correct number", Toast.LENGTH_SHORT).show();
                }


                telephoneEdittext.setVisibility(View.INVISIBLE);
                telephoneTextview.setVisibility(View.VISIBLE);
                saveInfo.setVisibility(View.INVISIBLE);
                changeInfo.setVisibility(View.VISIBLE);

            }
        });


        TextView nameTextView = findViewById(R.id.profile_full_name);
        nameTextView.setText(name);

        TextView emailTextView = findViewById(R.id.profile_email);
        emailTextView.setText(email);

        ImageView profileImageView = findViewById(R.id.profile_img);




        //Setting listener to change telephone
        DatabaseReference telephoneDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(userUID).child("user_telephone");
        telephoneDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!Objects.equals(dataSnapshot.getValue(String.class), "")) {
                    telephoneTextview.setText(dataSnapshot.getValue(String.class));
                    telephoneEdittext.setText(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
