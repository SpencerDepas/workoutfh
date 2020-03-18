package com.compscieddy.workoutfh;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import timber.log.Timber;

public class Crash {

  public static void log(String errorMessage) {
    Timber.e(errorMessage);
    FirebaseCrashlytics.getInstance().log(errorMessage);
  }

}
