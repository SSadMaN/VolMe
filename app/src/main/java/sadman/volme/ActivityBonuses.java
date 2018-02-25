package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityBonuses extends AppCompatActivity {


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ListView mEventListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonuses);


        ImageView gethome = findViewById(R.id.get_home_button);
        gethome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_event_form = new Intent(ActivityBonuses.this, ActivityHome.class);
                startActivity(add_event_form);
                overridePendingTransition(0, 0);
            }
        });

        ImageView getprofile = findViewById(R.id.profile_button);
        getprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_event_form = new Intent(ActivityBonuses.this, ActivityProfile.class);
                startActivity(add_event_form);
                overridePendingTransition(0, 0);
            }
        });

        ImageView addevent = findViewById(R.id.openEventAddForm);
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_event_form = new Intent(ActivityBonuses.this, ActivityAddingEventForm.class);
                startActivity(add_event_form);
                overridePendingTransition(0, 0);
            }
        });


    }
}
