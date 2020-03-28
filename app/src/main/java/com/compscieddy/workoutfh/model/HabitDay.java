package com.compscieddy.workoutfh.model;

import com.compscieddy.workoutfh.util.DateUtil;
import com.compscieddy.workoutfh.util.FirestoreUtil;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class HabitDay {

  public static final String HABIT_DAY_COLLECTION = "habitDay";
  private String mId;
  private String mYearMonthDay;
  private String mHabitId;
  private int mHabitCount;
  private long mCreatedAtMillis;

  public HabitDay() {}

  public HabitDay(Calendar calendar, String habitId, int habitCount) {
    mId = FirestoreUtil.generateId(getHabitDayCollection());
    mHabitId = habitId;
    mYearMonthDay = DateUtil.getYearMonthDayString(calendar);
    mHabitCount = habitCount;
    mCreatedAtMillis = System.currentTimeMillis();
  }

  public CollectionReference getHabitDayCollection() {
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
