package com.compscieddy.workoutfh.model;

import android.text.TextUtils;

import com.compscieddy.workoutfh.Crashes;
import com.compscieddy.workoutfh.util.FirestoreUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Habit {

  public static final String HABIT_COLLECTION = "habit";

  private String mHabitName;
  private String mEmojiCode;
  private long mCreatedAtMillis;
  private String mId;

  public Habit() {}

  public Habit(String habitName, @Nullable String emojiCode) {
    mId = FirestoreUtil.generateId(getHabitCollection());
    mHabitName = habitName;

    if (TextUtils.isEmpty(emojiCode)) {
      emojiCode = ""; // todo: generate random emoji
    }
    mEmojiCode = emojiCode;

    mCreatedAtMillis = System.currentTimeMillis();
  }

  public static void createNewHabitOnFirestore(String habitName) {
    Habit newHabit = new Habit(habitName, "");
    newHabit.saveHabitToFirestore(null);
  }

  public CollectionReference getHabitCollection() {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    return db.collection(HABIT_COLLECTION);
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
