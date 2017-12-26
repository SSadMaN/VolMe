package sadman.volme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;


public class Activity_home extends AppCompatActivity {

    public String name;
    public String surname;
    public String email;
    public String userkey;
    public String uid;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private GoogleApiClient mGoogleApiClient;

   // private GoogleSignInClient mGoogleSignInClient;
    //private GoogleApiClient mGoogleApiClient;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private static final int RC_SIGN_IN = 1;

    // это будет именем файла настроек
    public static final String Newly_added_key = "newlyaddedkey";
    public static final String Newly_added_keyx = "newlyaddedkeyx"; // имя кота

    SharedPreferences mnewlyaddedkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mnewlyaddedkey = getSharedPreferences(Newly_added_key, Context.MODE_PRIVATE);

        // Initialize Firebase components
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        mFirebaseAuth = FirebaseAuth.getInstance();

        ImageView get_profile = (ImageView) findViewById(R.id.profile_button);
        get_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prof_mainIntent = new Intent(Activity_home.this, Activity_Profile.class);
                prof_mainIntent.putExtra("name", name);
                prof_mainIntent.putExtra("email", email);
                prof_mainIntent.putExtra("keyu", uid);
                startActivity(prof_mainIntent);
                overridePendingTransition(0, 0);
            }
        });

        ImageView addeventform = (ImageView) findViewById(R.id.open_event_addform);
        addeventform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_event_form = new Intent(Activity_home.this, AddEventForm.class);
                startActivity(add_event_form);
                overridePendingTransition(0, 0);
            }
        });

        ImageView bonuses = (ImageView) findViewById(R.id.bonuses_button);
        bonuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_event_form = new Intent(Activity_home.this, Activity_Bonuses.class);
                startActivity(add_event_form);
                overridePendingTransition(0, 0);
            }
        });

        ImageView sign_out = (ImageView) findViewById(R.id.sign_out);
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // FirebaseAuth.getInstance().signOut();
                signout();
            }
        });


        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        CategoryAdapter adapter = new CategoryAdapter(this,getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);


        // ADD GOOGLE SIGN IN

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User signed in
                    // Name, email address, and profile photo Url
                    name = user.getDisplayName();
                    email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();

                    // Check if user's email is verified
                    // boolean emailVerified = user.isEmailVerified();

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    uid = user.getUid();

                    //check if email already exists
                    Query query = FirebaseDatabase.getInstance().getReference().child("user").orderByChild("userUid").equalTo(uid);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() < 1) {
                                // write retrieved data to database
                                User new_user = new User(name,email,uid);
                                mDatabaseReference.push().setValue(new_user, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        userkey = databaseReference.getKey();
                                        SharedPreferences.Editor editor = mnewlyaddedkey.edit();
                                        editor.putString(Newly_added_keyx, userkey);
                                        editor.apply();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });



                } else {
                    // user is logged out
                    startActivity(new Intent(Activity_home.this, Activity_Started.class));
                }



            }
        };

    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


    public void signout(){
        mFirebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
    }


    //retrieving user's key

   /* Query mThisUsersPosts;

    public static final String TAG = "blah" ;

    DatabaseReference mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("posts");
    DatabaseReference mThisUsersPosts = mDatabaseUsers.child(mCurrentUser);

mThisUsersPosts.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange (DataSnapshot dataSnapshot){
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                System.out.println(child.getKey());
                System.out.println(child.child("title").getValue());
            }
        }
    }*/






}
