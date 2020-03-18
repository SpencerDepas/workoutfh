package com.compscieddy.workoutfh;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import androidx.annotation.NonNull;

public class User {

  public static final String USER_COLLECTION = "user";
  private String mEmail;
  private String mDisplayName;
  private String mPhotoUrl;

  public User() {}

  public User(FirebaseUser firebaseUser) {
    this(
        firebaseUser.getEmail(),
        firebaseUser.getDisplayName(),
        firebaseUser.getPhotoUrl() == null ? "" : firebaseUser.getPhotoUrl().toString());
  }

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

  void saveUserToFirestore(final Runnable onSuccessRunnable) {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection(USER_COLLECTION).document(getEmail()).set(User.this, SetOptions.merge())
        .addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {
            onSuccessRunnable.run();
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            Crashes.log("Failed to set the user in AuthenticationActivity e: " + e.toString());
          }
        });
  }
}
