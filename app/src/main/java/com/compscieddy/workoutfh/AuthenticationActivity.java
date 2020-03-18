package com.compscieddy.workoutfh;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;

import com.compscieddy.eddie_utils.etil.Etil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.compscieddy.workoutfh.Analytics.AUTHENTICATION_BUTTON;
import static com.compscieddy.workoutfh.PreferenceConstants.FIRST_LOGIN_MILLIS;

public class AuthenticationActivity extends AppCompatActivity {

  public static final int REQUEST_CODE_SIGN_IN = 101;
  private static final int REQUEST_CODE_RESOLVE_CONNECTION = 102;

  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthStateListener;

  private GoogleApiClient mGoogleClient;

  @BindView(R.id.sign_in_button) SignInButton mGoogleLoginButton;
  @BindView(R.id.loading_screen) View mLoadingScreen;

  public static void launch(Activity activity) {
    Intent intent = new Intent(activity, AuthenticationActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_authentication);
    ButterKnife.bind(AuthenticationActivity.this);
    Analytics.track(Analytics.AUTHENTICATION_SCREEN);
    init();
    setListeners();
  }

  private void init() {
    mAuth = FirebaseAuth.getInstance();
    mAuthStateListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        handleSignInOrSignOut(firebaseAuth);
      }
    };

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.firebase_web_client_id))
        .requestEmail()
        .build();

    mGoogleClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this /* AuthenticationActivity */, new GoogleApiClient.OnConnectionFailedListener() {
          @Override
          public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Crash.log("onConnectionFailed() " + connectionResult);
            if (connectionResult.hasResolution()) {
              try {
                connectionResult.startResolutionForResult(AuthenticationActivity.this, REQUEST_CODE_RESOLVE_CONNECTION);
              } catch (IntentSender.SendIntentException e) {
                Crash.log("Google connection could not be established for Google API Client");
              }
            }
          }
        } /* OnConnectionFailedListener */)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();
  }

  private void setListeners() {
    mGoogleLoginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        launchLogin();
        Analytics.track(AUTHENTICATION_BUTTON);
      }
    });
  }

  private void handleSignInOrSignOut(@NonNull FirebaseAuth firebaseAuth) {
    FirebaseUser user = firebaseAuth.getCurrentUser();
    if (user != null) {
      // User is signed in
      Timber.d("onAuthStateChanged:signed_in:" + user.getUid());
      loginSuccess();
    } else {
      // User is signed out
      Timber.d("onAuthStateChanged:signed_out");
    }
  }

  private void launchLogin() {
    Timber.d("auth launchLogin");
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleClient);
    startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
  }

  private void loginSuccess() {
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    String name = firebaseUser.getDisplayName();

    Timber.d("Login has been done successfully! For: " + firebaseUser.getEmail());
    Etil.showToast(AuthenticationActivity.this, "Hi, " + name + ".");

    saveUserToFirestoreThenForwardToMainActivity();
    saveFirstTimeLoginMillis();
  }

  private void saveUserToFirestoreThenForwardToMainActivity() {
    @Nullable FirebaseUser currentUser = mAuth.getCurrentUser();
    if (currentUser == null) {
      Etil.showToast(this, "Couldn't login. Close the app and try again.");
    }
    User user = new User(currentUser);
    user.saveUserToFirestore(new Runnable() {
      @Override
      public void run() {
        boolean isAlreadySignedIn = FirebaseAuth.getInstance().getCurrentUser() != null;
        if (isAlreadySignedIn) {
          Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
          startActivity(intent);
          overridePendingTransition(0, 0);
          finish();
        }
      }
    });
  }

  private void saveFirstTimeLoginMillis() {
    long firstLoginMillis = WorkoutFHApplication.getSharedPreferencesLong(FIRST_LOGIN_MILLIS);
    if (firstLoginMillis == -1L) {
      WorkoutFHApplication.setSharedPreferencesLong(FIRST_LOGIN_MILLIS, System.currentTimeMillis());
    }
  }
}
