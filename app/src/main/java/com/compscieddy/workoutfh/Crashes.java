package com.compscieddy.workoutfh;

import com.compscieddy.eddie_utils.etil.Etil;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import timber.log.Timber;

public class Crashes {

  public static void log(String errorMessage) {
    Timber.e(errorMessage);
    FirebaseCrashlytics.getInstance().log(errorMessage);
  }

  public static void logAndShowToast(String errorMessage) {
    log(errorMessage);
    Etil.showToast(WorkoutFHApplication.sApplicationContext, errorMessage);
  }
}
