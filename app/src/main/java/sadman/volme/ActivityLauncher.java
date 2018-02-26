package sadman.volme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sadman on 25/12/17.
 * Edited by Nikita Kiselov on 25/02/18
 */

public class ActivityLauncher extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabaseReference;


    private GoogleApiClient mGoogleApiClient;


    private Button mSignInGoogleButton;

    private static final int RC_SIGN_IN = 1;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //------------------------------Enabling offline capabilities--------------------------
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //----------------------------------------------------------------------
        if (mFirebaseUser != null) {
            startActivity(new Intent(ActivityLauncher.this, ActivityHome.class));
        }
        //-----------------------------------------------------------------------
        setContentView(R.layout.activity_started);
        progressBar = findViewById(R.id.progressBarInAuthorization);




        mSignInGoogleButton = findViewById(R.id.sign_in_google_button);

        mSignInGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            //getting result of Sign in
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                //progressDialog.dismiss();
                Toast.makeText(ActivityLauncher.this, "Google Sign in is failed",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(ActivityLauncher.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            //adding user(if not exist)
                            addUser();

                            //finish progress dialog
                            progressBar.setVisibility(View.GONE);

                            //Starting MainActivity
                            Intent mainIntent = new Intent(ActivityLauncher.this,
                                    ActivityHome.class);

                            ActivityLauncher.this.startActivity(mainIntent);
                            ActivityLauncher.this.finish();
                        }
                    }
                });
    }


    private void signIn() {

        //Set visibility of progress bar
        progressBar.setVisibility(View.VISIBLE);

        //Using default builder to construct SignInOptions
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        //Getting Google Sign In API
        mGoogleApiClient = new GoogleApiClient.Builder(ActivityLauncher.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Setting Progress Dialog
//        progressDialog = new ProgressDialog(ChooserActivity.this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Logging in...");
//        progressDialog.show();

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void addUser() {
        //adding user to database
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("user");

        //Name, email address, and profile photo Url
        final String name = mFirebaseUser.getDisplayName();
        final String email = mFirebaseUser.getEmail();
        final String uid = mFirebaseUser.getUid();
        final String profileImage = String.valueOf(mFirebaseUser.getPhotoUrl()).replace("/s96-c/","/s120-c/");

        Query query = FirebaseDatabase.getInstance().getReference().child("user").orderByChild("userUid").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() < 1) {

                    // write retrieved data to database
                    User newUser = new User(name, email, uid, profileImage);
                    mDatabaseReference.child(uid).setValue(newUser, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError != null) {
                                Toast.makeText(ActivityLauncher.this, databaseError.getMessage(), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
