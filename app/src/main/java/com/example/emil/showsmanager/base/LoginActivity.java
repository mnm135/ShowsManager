package com.example.emil.showsmanager.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.models.firebase.User;
import com.example.emil.showsmanager.subscribedshows.SubscribedShowsActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(getApplicationContext(), SubscribedShowsActivity.class));

                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setProviders(AuthUI.EMAIL_PROVIDER,
                                            AuthUI.GOOGLE_PROVIDER)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };


    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabaseReference.child("users").child(userId).setValue(user);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mDatabaseReference = mFirebaseDatabase.getReference();

                String username = mFirebaseAuth.getCurrentUser().getDisplayName();
                String email = mFirebaseAuth.getCurrentUser().getEmail();
                String userId = mFirebaseAuth.getCurrentUser().getUid();
                writeNewUser(userId, username, email);


                startActivity(new Intent(this, SubscribedShowsActivity.class));
                return;
            }
            if (resultCode == RESULT_CANCELED) {
                finish();
            }
            if (resultCode == ResultCodes.RESULT_NO_NETWORK) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}
