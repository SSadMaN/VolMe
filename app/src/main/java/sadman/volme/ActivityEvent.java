package sadman.volme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ActivityEvent extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEventsDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser firebaseUser;

    private DatabaseReference users;


    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private ImageView delete_event;
    private Button likeEvent;
    private Button likeEventFull;
    private Button buttonToMakeCall;
    private TextView eventTitle;
    private TextView eventOrganizator;
    private TextView eventDescription;
    private TextView eventDate;
    private TextView eventLocation;
    private TextView eventTag;
    private TextView subscribersList;
    private String username;
    private String eventKey;
    private String userUid;
    private CountDownTimer timer;
    private String organizatorTelephone;
    private String organizatorUserUID;
    private String userPhotoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventKey = getIntent().getStringExtra("eventKey");

        //-----------------------Initialize Firebase components---------------------------
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        userUid = user.getUid();
        username = user.getDisplayName();
        userPhotoUri = user.getPhotoUrl().toString().replace("/s96-c/","/s120-c/");
        //--------------------------------------------------------------------------------

        //=======================Initialize references to views=============================
        buttonToMakeCall = findViewById(R.id.call_start_button);
        delete_event = findViewById(R.id.delete_event);
        eventTitle = findViewById(R.id.name);
        eventOrganizator = findViewById(R.id.organizator_text);
        eventDescription = findViewById(R.id.event_description);
        eventDate = findViewById(R.id.calendar_text);
        eventLocation = findViewById(R.id.location_textview);
        eventTag = findViewById(R.id.tag_text);
        likeEvent = findViewById(R.id.like);
        likeEventFull = findViewById(R.id.like_full);
        subscribersList = findViewById(R.id.subscribers_list_button);
        //======================================================================================

        //setting visibility of delete button
        //TODO remove it when activity deleted

        Query deleteButtonQuery = mEventsDatabaseReference.child(eventKey).orderByChild("org_uid")
                .equalTo(userUid);
        deleteButtonQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    delete_event.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        delete_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventsDatabaseReference.child(eventKey).removeValue();
                finish();

            }
        });

        //TODO add object to do ONLY one query =====================================================
        //=========================================HERE=============================================

        // Event subscribers
        final Query query = mEventsDatabaseReference.child(eventKey).child("subscribers")
                .orderByKey().equalTo(userUid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //TODO fix it
                    likeEvent.setVisibility(View.INVISIBLE);
                    likeEventFull.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Event Title
        DatabaseReference titleDatabaseReference = mEventsDatabaseReference.child(eventKey).child("event_title");
        titleDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventTitle.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // Event Organizator name
        DatabaseReference organizationNameDatabaseReference = mEventsDatabaseReference.child(eventKey)
                .child("organization_name");
        organizationNameDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventOrganizator.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // Event description
        DatabaseReference eventDescriptionReference = mEventsDatabaseReference.child(eventKey).child("event_description");
        eventDescriptionReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventDescription.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // Event date
        DatabaseReference eventDataDatabaseReference = mEventsDatabaseReference.child(eventKey).child("event_data");
        eventDataDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventDate.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // Event location
        DatabaseReference eventLocationDatabaseReference = mEventsDatabaseReference.child(eventKey).child("event_location");
        eventLocationDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventLocation.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //Event tag
        DatabaseReference eventTagDatabaseReference = mEventsDatabaseReference.child(eventKey).child("event_tag");
        eventTagDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventTag.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //==========================================================================================
        //==========================================================================================

        // set buttons initial visibility
        users = mEventsDatabaseReference.child(eventKey).child("subscribers");



        //TODO this timer
        timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(userUid).exists()) {
                            likeEvent.setVisibility(View.INVISIBLE);
                            likeEventFull.setVisibility(View.VISIBLE);
                        } else {
                            likeEvent.setVisibility(View.VISIBLE);
                            likeEventFull.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };


        //set like button to add users Uid to event's database
        //TODO add transaction
        likeEvent.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                User newSubscriber = new User(username, null, userUid, userPhotoUri);
                mEventsDatabaseReference.child(eventKey).child("subscribers/" + userUid).setValue(newSubscriber, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    }
                });

                likeEvent.setVisibility(View.INVISIBLE);
                likeEventFull.setVisibility(View.VISIBLE);

            }

        });

        //set like button to remove users Uid to event's database
        likeEventFull.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                mEventsDatabaseReference.child(eventKey).child("subscribers").child(userUid).removeValue();
                likeEvent.setVisibility(View.VISIBLE);
                likeEventFull.setVisibility(View.INVISIBLE);

            }
        });

        //setting button to open subscribers list
        subscribersList.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Intent subscribersListIntent = new Intent(ActivityEvent.this, ListSubscribers.class);
                subscribersListIntent.putExtra("eventKey", eventKey);
                startActivity(subscribersListIntent);
            }
        });

        //Make call to organization
        buttonToMakeCall.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                DatabaseReference organizatorUidDatabaseReference = mEventsDatabaseReference.child(eventKey)
                        .child("event_organizator").child("org_uid");
                organizatorUidDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        organizatorUserUID = dataSnapshot.getValue(String.class);
                        DatabaseReference telephoneDatabaseReference = FirebaseDatabase.getInstance().getReference()
                                .child("user").child(organizatorUserUID).child("user_tel");
                        telephoneDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                organizatorTelephone = dataSnapshot.getValue(String.class);
                                if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                    callIntent.setData(Uri.parse("tel:" + organizatorTelephone));
                                    startActivity(callIntent);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
