package com.compscieddy.workoutfh;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

  @BindView(R.id.logout_button) View mLogoutButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(MainActivity.this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    attachListeners();
  }

  @Override
  protected void onPause() {
    super.onPause();
    detachListeners();
  }

  private void attachListeners() {
    mLogoutButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
        ActivityHelper.launchActivityAndFinish(MainActivity.this, AuthenticationActivity.class);
      }
    });
  }

  private void detachListeners() {
    mLogoutButton.setOnClickListener(null);
  }
}
