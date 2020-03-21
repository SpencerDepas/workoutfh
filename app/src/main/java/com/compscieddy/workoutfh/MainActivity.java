package com.compscieddy.workoutfh;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

  @BindView(R.id.logout_button) View mLogoutButton;
  @BindView(R.id.god_view_pager) ViewPager mGodViewPager;
  @BindView(R.id.settings_god_fragment_button) View mSettingsGodFragmentButton;
  @BindView(R.id.todo_god_fragment_button) View mTodoGodFragmentButton;

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
    mGodViewPager.setCurrentItem(1);

    setGodFragmentButtonsUnselected();
  }

  private void attachListeners() {
    mLogoutButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
        ActivityHelper.launchActivityAndFinish(MainActivity.this, AuthenticationActivity.class);
      }
    });
    mSettingsGodFragmentButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mGodViewPager.setCurrentItem(0);
      }
    });
    mTodoGodFragmentButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mGodViewPager.setCurrentItem(2);
      }
    });
    mGodViewPager.addOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

      @Override
      public void onPageSelected(int position) {
        setGodFragmentButtonsUnselected();

        // Highlight current god fragment, do nothing if on main screen
        if (position == GodPagerAdapter.SETTINGS_POSITION) {
          setGodFragmentButtonSelectedState(true, mSettingsGodFragmentButton);
        } else if (position == GodPagerAdapter.TODO_POSITION) {
          setGodFragmentButtonSelectedState(true, mTodoGodFragmentButton);
        }
      }

      @Override
      public void onPageScrollStateChanged(int state) {}
    });
  }

  private void setGodFragmentButtonsUnselected() {
    setGodFragmentButtonSelectedState(false, mSettingsGodFragmentButton);
    setGodFragmentButtonSelectedState(false, mTodoGodFragmentButton);
  }

  private void setGodFragmentButtonSelectedState(boolean isSelected, View godFragmentButton) {
    final float SELECTED_ALPHA = 1.0f;
    final float UNSELECTED_ALPHA = 0.3f;
    final float SELECTED_SCALE = 1.3f;
    final float UNSELECTED_SCALE = 1.0f;

    godFragmentButton.setAlpha(isSelected ? SELECTED_ALPHA : UNSELECTED_ALPHA);
    godFragmentButton.animate()
        .scaleX(isSelected ? SELECTED_SCALE : UNSELECTED_SCALE)
        .scaleY(isSelected ? SELECTED_SCALE : UNSELECTED_SCALE);
  }

  private void detachListeners() {
    mLogoutButton.setOnClickListener(null);
  }
}
