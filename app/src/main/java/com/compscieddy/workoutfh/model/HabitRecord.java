package com.compscieddy.workoutfh.model;

import com.compscieddy.workoutfh.Crashes;
import com.compscieddy.workoutfh.util.DateUtil;
import com.compscieddy.workoutfh.util.FirestoreUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

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

  /**
   * Habit Record has a count that needs to be added to the total habit count on Habit.
   * This is called an aggregation. Doing the client-side implementation here.
   * https://firebase.google.com/docs/firestore/solutions/aggregation
   */
  private void saveHabitRecordToFirestore(@Nullable final Runnable onSuccessRunnable) {
    FirebaseFirestore.getInstance().runTransaction(new Transaction.Function<Void>() {
      @Nullable
      @Override
      public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
        // Update the Habit
        DocumentReference habitReference = Habit.getHabitCollection().document(getHabitId());
        Habit habit = transaction.get(habitReference).toObject(Habit.class);
        habit.updateTotalHabitCount(HabitRecord.this);
        transaction.set(habitReference, habit);

        // Update the HabitRecord
        DocumentReference habitRecordReference = getHabitRecordCollection().document(getId());
        transaction.set(habitRecordReference, HabitRecord.this);
        return null;
      }
    })
    .addOnSuccessListener(aVoid -> {
      if (onSuccessRunnable != null) {
        onSuccessRunnable.run();
      }
    })
    .addOnFailureListener(e -> {
      Crashes.log("Failed to save habit record with id: " + getId());
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
