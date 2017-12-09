package sadman.volme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;



    private TextView event_title;
    private TextView event_organizator;
    private TextView event_description;
    private TextView event_data;
    private TextView event_location;
    private TextView event_tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");

        // Initialize references to views
        event_title = findViewById(R.id.name);
        event_organizator = findViewById(R.id.organizator_text);
        event_description = findViewById(R.id.event_description);
        event_data = findViewById(R.id.calendar_text);
        event_location = findViewById(R.id.location_textview);
        event_tag = findViewById(R.id.tag_text);


    }


}
