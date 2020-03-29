package com.compscieddy.workoutfh.model;

import com.compscieddy.workoutfh.Crashes;
import com.compscieddy.workoutfh.util.DateUtil;
import com.compscieddy.workoutfh.util.FirestoreUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HabitRecord {

  public static final String HABIT_DAY_COLLECTION = "habitDay";
  private String mId;
  private String mYearMonthDay;
  private String mHabitId;
  private int mHabitCount;
  private long mCreatedAtMillis;
  private String mUserEmail;

  public HabitRecord() {}

  public HabitRecord(Calendar calendar, String habitId, int habitCount) {
    mId = FirestoreUtil.generateId(getHabitRecordCollection());
    mUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    mHabitId = habitId;
    mYearMonthDay = DateUtil.getYearMonthDayString(calendar);
    mHabitCount = habitCount;
    mCreatedAtMillis = System.currentTimeMillis();
  }

  public static void createNewHabitRecordOnFirestore(Habit habit, int habitCount) {
    HabitRecord habitRecord = new HabitRecord(Calendar.getInstance(), habit.getId(), habitCount);
    habitRecord.saveHabitRecordToFirestore(null);
  }

  private void saveHabitRecordToFirestore(@Nullable final Runnable onSuccessRunnable) {
    getHabitRecordCollection().document(getId()).set(HabitRecord.this, SetOptions.merge())
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
            Crashes.log("Failed to save habit record with id: " + getId());
          }
        });
  }

  @Exclude
  public static CollectionReference getHabitRecordCollection() {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    return db.collection(HABIT_DAY_COLLECTION);
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

  public String getYearMonthDay() {
    return mYearMonthDay;
  }

  public void setYearMonthDay(String yearMonthDay) {
    mYearMonthDay = yearMonthDay;
  }

  public String getHabitId() {
    return mHabitId;
  }

  public void setHabitId(String habitId) {
    mHabitId = habitId;
  }

  public int getHabitCount() {
    return mHabitCount;
  }

  public void setHabitCount(int habitCount) {
    mHabitCount = habitCount;
  }

  public long getCreatedAtMillis() {
    return mCreatedAtMillis;
  }

  public void setCreatedAtMillis(long createdAtMillis) {
    mCreatedAtMillis = createdAtMillis;
  }
}
