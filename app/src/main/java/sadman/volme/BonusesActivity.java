package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BonusesActivity extends AppCompatActivity {


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ListView mEventListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonuses);


        ImageView gethome = (ImageView) findViewById(R.id.get_home_button);
        gethome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_event_form = new Intent(BonusesActivity.this, home.class);
                startActivity(add_event_form);
                overridePendingTransition(0, 0);
            }
        });

        ImageView getprofile = (ImageView) findViewById(R.id.profile_button);
        getprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_event_form = new Intent(BonusesActivity.this, ProfileActivity.class);
                startActivity(add_event_form);
                overridePendingTransition(0, 0);
            }
        });

        ImageView addevent = (ImageView) findViewById(R.id.open_event_addform);
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_event_form = new Intent(BonusesActivity.this, AddEventForm.class);
                startActivity(add_event_form);
                overridePendingTransition(0, 0);
            }
        });


    }
}
