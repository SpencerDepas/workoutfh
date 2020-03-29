package com.compscieddy.workoutfh.model;

import android.text.TextUtils;

import com.compscieddy.workoutfh.Crashes;
import com.compscieddy.workoutfh.util.FirestoreUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Habit {

  public static final String HABIT_COLLECTION = "habit";

  public static final String FIELD_ID = "id";
  public static final String FIELD_USER_EMAIL = "userEmail";
  public static final String FIELD_HABIT_NAME = "habitName";
  public static final String FIELD_EMOJI_CODE = "emojiCode";
  public static final String FIELD_CREATED_AT_MILLIS = "createdAtMillis";

  private String mId;
  private String mUserEmail;
  private String mHabitName;
  private String mEmojiCode;
  private long mCreatedAtMillis;

  public Habit() {}

  public Habit(String userEmail, String habitName, @Nullable String emojiCode) {
    mId = FirestoreUtil.generateId(getHabitCollection());
    mUserEmail = userEmail;
    mHabitName = habitName;

    if (TextUtils.isEmpty(emojiCode)) {
      emojiCode = ""; // todo: generate random emoji
    }
    mEmojiCode = emojiCode;

    mCreatedAtMillis = System.currentTimeMillis();
  }

  public static void createNewHabitOnFirestore(String habitName) {
    String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    Habit newHabit = new Habit(userEmail, habitName, "");
    newHabit.saveHabitToFirestore(null);
  }

  @Exclude
  public static CollectionReference getHabitCollection() {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    return db.collection(HABIT_COLLECTION);
  }

  @Exclude
  public static Query getHabitQuery() {
    return getHabitCollection()
        .orderBy(FIELD_HABIT_NAME, Query.Direction.ASCENDING);
  }

  public void saveHabitToFirestore(@Nullable final Runnable onSuccessRunnable) {
    getHabitCollection().document(getId()).set(Habit.this, SetOptions.merge())
        .addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {
            if (onSuccessRunnable != null) {
              onSuccessRunnable.run();
            }
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            Crashes.log("Failed to save habit with id: " + getId());
          }
        });
  }

  /** Getters and Setters */

  public String getId() {
    return mId;
  }

  public void setId(String id) {
    mId = id;
  }

  public String getUserEmail() {
    return mUserEmail;
  }

  public void setUserEmail(String userEmail) {
    mUserEmail = userEmail;
  }

  public String getHabitName() {
    return mHabitName;
  }

  public void setHabitName(String habitName) {
    mHabitName = habitName;
  }

  public String getEmojiCode() {
    return mEmojiCode;
  }

  public void setEmojiCode(String emojiCode) {
    mEmojiCode = emojiCode;
  }

  public long getCreatedAtMillis() {
    return mCreatedAtMillis;
  }

  public void setCreatedAtMillis(long createdAtMillis) {
    mCreatedAtMillis = createdAtMillis;
  }
}
