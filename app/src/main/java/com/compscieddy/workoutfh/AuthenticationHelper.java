package com.compscieddy.workoutfh;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationHelper {

  public static boolean isLoggedOut() {
    return FirebaseAuth.getInstance().getCurrentUser() == null;
  }

  public static void handleLoggedOutUser(Activity activity) {
    ActivityHelper.launchActivityAndFinish(activity, AuthenticationActivity.class);
  }

}
