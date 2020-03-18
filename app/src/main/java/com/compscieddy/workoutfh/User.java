package com.compscieddy.workoutfh;

import android.content.Context;
import android.content.Intent;

import com.compscieddy.workoutfh.activity.AuthenticationActivity;
import com.google.firebase.auth.FirebaseAuth;

public class User {

  public static final String USER_COLLECTION = "user";
  private String mEmail;
  private String mDisplayName;
  private String mPhotoUrl;

  public User() {}

  public User(String email, String displayName, String photoUrl) {
    mEmail = email;
    mDisplayName = displayName;
    mPhotoUrl = photoUrl;
  }

  public String getEmail() {
    return mEmail;
  }
  public void setEmail(String email) {
    mEmail = email;
  }

  public String getDisplayName() {
    return mDisplayName;
  }
  public void setDisplayName(String displayName) {
    mDisplayName = displayName;
  }

  public String getPhotoUrl() {
    return mPhotoUrl;
  }
  public void setPhotoUrl(String photoUrl) {
    mPhotoUrl = photoUrl;
  }

  public static String getEmail(Context context) {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String email = firebaseAuth.getCurrentUser().getEmail();
    if (email == null) {
      Intent intent = new Intent(context, AuthenticationActivity.class);
      context.startActivity(intent);
    }
    return email;
  }
}
