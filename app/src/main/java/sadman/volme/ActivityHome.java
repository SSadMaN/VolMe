package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;



public class ActivityHome extends AppCompatActivity {

    public String name;
    public String email;
    public String uid;

    private GoogleApiClient mGoogleApiClient;


    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = findViewById(R.id.tabs);

        // Initialize Firebase components
        mFirebaseAuth = FirebaseAuth.getInstance();

        //-------------Setting intent to profile view---------------------------
        ImageView getProfileButton = findViewById(R.id.profile_button);
        getProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(ActivityHome.this, ActivityProfile.class);
                profileIntent.putExtra("name", name);
                profileIntent.putExtra("email", email);
                profileIntent.putExtra("keyu", uid);
                startActivity(profileIntent);
                overridePendingTransition(0, 0);
            }
        });
        //---------------------------------------------------------------------

        //---------------Setting intent to add new event-----------------------
        ImageView addEventForm = findViewById(R.id.openEventAddForm);
        addEventForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addEventForm = new Intent(ActivityHome.this, ActivityAddingEventForm.class);
                startActivity(addEventForm);
                overridePendingTransition(0, 0);
            }
        });
        //----------------------------------------------------------------------

        //----------------Setting new intent to bonuses-------------------------
        ImageView bonuses = findViewById(R.id.bonuses_button);
        bonuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addBonuses = new Intent(ActivityHome.this, ActivityBonuses.class);
                startActivity(addBonuses);
                overridePendingTransition(0, 0);
            }
        });
        //----------------------------------------------------------------------

        //setting sign out
        ImageView signOut = findViewById(R.id.sign_out);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });


        //==============================Setting ViewPager=========================

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);
        //=========================================================================


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

//------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!--------------------------
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                final FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User signed in
//                    // Name, email address, and profile photo Url
//                    name = user.getDisplayName();
//                    email = user.getEmail();
//
//                    // Check if user's email is verified
//                    // boolean emailVerified = user.isEmailVerified();
//
//                    // The user's ID, unique to the Firebase project. Do NOT use this value to
//                    // authenticate with your backend server, if you have one. Use
//                    // FirebaseUser.getToken() instead.
//                    uid = user.getUid();
//
//                    //check if email already exists
//                    Query query = FirebaseDatabase.getInstance().getReference().child("user").orderByChild("userUid").equalTo(uid);
//                    query.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.getChildrenCount() < 1) {
//                                // write retrieved data to database
//                                User new_user = new User(name, email, uid);
//                                mDatabaseReference.push().setValue(new_user, new DatabaseReference.CompletionListener() {
//                                    @Override
//                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                        userkey = databaseReference.getKey();
//                                        SharedPreferences.Editor editor = mnewlyaddedkey.edit();
//                                        editor.putString(Newly_added_keyx, userkey);
//                                        editor.apply();
//                                    }
//                                });
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                        }
//                    });
//
//
//                } else {
//
//                }
//            }
//        };
        //------------------------------------------------------------------------------------

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }


    public void signOut() {
        mFirebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);

        // user is logged out
        startActivity(new Intent(ActivityHome.this, ActivityLauncher.class));
        ActivityHome.this.finish();
    }


}
