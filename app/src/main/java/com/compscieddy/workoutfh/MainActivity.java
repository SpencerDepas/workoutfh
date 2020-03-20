package com.compscieddy.workoutfh;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

  @BindView(R.id.logout_button) View mLogoutButton;
  @BindView(R.id.god_tab_layout) TabLayout mGodTabLayout;
  @BindView(R.id.god_view_pager) ViewPager mGodViewPager;
  @BindView(R.id.first_god_fragment_icon) View mFirstGodFragmentIcon;
  @BindView(R.id.second_god_fragment_icon) View mSecondGodFragmentIcon;
  @BindView(R.id.third_god_fragment_icon) View mThirdGodFragmentIcon;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(MainActivity.this);
    init();
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

  private void init() {
    mGodViewPager.setAdapter(new GodPagerAdapter(MainActivity.this, getSupportFragmentManager()));
    mGodTabLayout.setupWithViewPager(mGodViewPager);
    mGodViewPager.setCurrentItem(1);
  }

  private void attachListeners() {
    mLogoutButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
        ActivityHelper.launchActivityAndFinish(MainActivity.this, AuthenticationActivity.class);
      }
    });
    mFirstGodFragmentIcon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mGodViewPager.setCurrentItem(0);
      }
    });
    mSecondGodFragmentIcon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mGodViewPager.setCurrentItem(1);
      }
    });
    mThirdGodFragmentIcon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mGodViewPager.setCurrentItem(2);
      }
    });
  }

  private void detachListeners() {
    mLogoutButton.setOnClickListener(null);
  }
}
