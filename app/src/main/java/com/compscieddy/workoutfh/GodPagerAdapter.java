package com.compscieddy.workoutfh;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class GodPagerAdapter extends FragmentPagerAdapter {

  @StringRes
  private static final int[] TAB_TITLES = new int[] {R.string.god_tab_1, R.string.god_tab_2, R.string.god_tab_3};

  private final Context mContext;

  public GodPagerAdapter(Context context, FragmentManager fragmentManager) {
    super(fragmentManager);
    mContext = context;
  }

  @NonNull
  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new SettingsGodFragment();
      case 1:
        return new MainGodFragment();
      case 2:
      default:
        return new ThirdGodFragment();
    }
  }

  @Override
  public int getCount() {
    return 3;
  }
}
