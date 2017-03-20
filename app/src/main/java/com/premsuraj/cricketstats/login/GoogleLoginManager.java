package com.premsuraj.cricketstats.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.crash.FirebaseCrash;
import com.premsuraj.cricketstats.R;

import java.io.Serializable;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * Created by Premsuraj
 */

public class GoogleLoginManager {
    private static final int RC_SIGN_IN = 4531;
    public GoogleApiClient googleApiClient;
    private UserDetails userDetails;
    private FragmentActivity activity;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
            // ...
        }
    };

    public GoogleLoginManager(FragmentActivity activity) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
    }

    public void login() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("577020833184-svou101l6ud398pn481i22gpbj8g25fa.apps.googleusercontent.com")
                .build();
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(activity)
                    .enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Snackbar.make(activity.getWindow().getDecorView(), R.string.google_login_failed, Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Snackbar.make(activity.getWindow().getDecorView(), R.string.google_login_failed, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        userDetails = new UserDetails();
        userDetails.userName = acct.getDisplayName();
        userDetails.imageUrl = acct.getPhotoUrl().toString();
        userDetails.idToken = acct.getIdToken();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            FirebaseCrash.report(task.getException());
                            Snackbar.make(activity.getWindow().getDecorView(), R.string.google_login_failed, Snackbar.LENGTH_LONG).show();
                            return;
                        }

                        if(activity instanceof LoginListener) {
                            ((LoginListener) activity).loginSucceeded(userDetails);
                        }
                    }
                });
    }

    public void removeAuthListener() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void addAuthListener() {
        mAuth.addAuthStateListener(mAuthListener);
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public interface LoginListener {
        void loginSucceeded(UserDetails userDetails);
    }

    public static class UserDetails implements Serializable {
        public String userName;
        public String imageUrl;
        public String idToken;
    }
}
