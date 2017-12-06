package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sadman on 05/12/17.
 */

public class AddEventForm extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;




    private EditText mEditEventTitle;
    private EditText mEditEventOrgName;
    private EditText mEditEventDescription;
    private EditText mEditEventDate;
    private EditText mEditEventTag;
    private EditText mEditEventLocation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent_form);

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");



        // Initialize references to views
        mEditEventTitle = (EditText) findViewById(R.id.add_event_title_name);
        mEditEventOrgName = (EditText) findViewById(R.id.add_organization_name);
        mEditEventDescription = (EditText) findViewById(R.id.add_event_description);
        mEditEventDate = (EditText) findViewById(R.id.add_event_date);
        mEditEventTag = (EditText) findViewById(R.id.add_event_tag);
        mEditEventLocation = (EditText) findViewById(R.id.add_event_location);




        Button mButton = (Button) findViewById(R.id.submit_event_form);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:set all fields on click
                Event oneevent = new Event(mEditEventOrgName.getText().toString(), mEditEventTitle.getText().toString(), mEditEventDescription.getText().toString(), mEditEventDate.getText().toString(), mEditEventLocation.getText().toString(), mEditEventTag.getText().toString());
                mEventsDatabaseReference.push().setValue(oneevent);

                Intent go_back_to_organizator_screen = new Intent(AddEventForm.this, Organizator_main.class);
                startActivity(go_back_to_organizator_screen);
                overridePendingTransition(0, 0);


                // Clear input box
                mEditEventTitle.setText("");
                mEditEventOrgName.setText("");
                mEditEventDescription.setText("");
                mEditEventDate.setText("");
                mEditEventTag.setText("");
                mEditEventLocation.setText("");



            }
        });
    }
}
