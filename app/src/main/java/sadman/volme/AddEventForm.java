package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

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
    private Spinner mEditEventTag;
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
        mEditEventTag = (Spinner) findViewById(R.id.add_event_tag);
        mEditEventLocation = (EditText) findViewById(R.id.add_event_location);




        Button mButton = (Button) findViewById(R.id.submit_event_form);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:set all fields on click
                Event oneevent = new Event(mEditEventOrgName.getText().toString(), mEditEventTitle.getText().toString(), mEditEventDescription.getText().toString(), mEditEventDate.getText().toString(), mEditEventLocation.getText().toString(), mEditEventTag.getSelectedItem().toString());
                mEventsDatabaseReference.push().setValue(oneevent);

                Intent go_back_to_main_menu = new Intent(AddEventForm.this, home.class);
                startActivity(go_back_to_main_menu);
                overridePendingTransition(0, 0);


                // Clear input box
                mEditEventTitle.setText("");
                mEditEventOrgName.setText("");
                mEditEventDescription.setText("");
                mEditEventDate.setText("");
                mEditEventLocation.setText("");



            }
        });

//spinner


        Spinner spinner = (Spinner) findViewById(R.id.add_event_tag);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.events_array, R.layout.simple_tag_spinner);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setPrompt("Select your favorite Planet!");

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }


        });
    }
}
