package com.compscieddy.workoutfh;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

public class Authentication {

  public static boolean isLoggedOut() {
    return FirebaseAuth.getInstance().getCurrentUser() == null;
  }

  public static void handleLoggedOutUser(Activity activity) {
    AuthenticationActivity.launch(activity);
  }

}
