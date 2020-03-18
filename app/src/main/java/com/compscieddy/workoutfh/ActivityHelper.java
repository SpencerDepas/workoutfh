package com.compscieddy.workoutfh;

import android.app.Activity;
import android.content.Intent;

public class ActivityHelper {

  public static void launchActivity(Activity fromActivity, Class toActivity) {
    Intent intent = new Intent(fromActivity, toActivity);
    fromActivity.startActivity(intent);
  }

}
