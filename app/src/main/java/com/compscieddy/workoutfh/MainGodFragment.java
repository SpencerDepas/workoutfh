package com.compscieddy.workoutfh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainGodFragment extends Fragment {

  @BindView(R.id.new_habit_button) View mNewHabitButton;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_main_god, container, false);
    ButterKnife.bind(MainGodFragment.this, rootView);
    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();
    attachListeners();
  }

  @Override
  public void onPause() {
    super.onPause();
    detachListeners();
  }

  private void attachListeners() {
    mNewHabitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        launchNewHabitFragment();
      }
    });
  }

  private void detachListeners() {
    mNewHabitButton.setOnClickListener(null);
  }

  private void launchNewHabitFragment() {
    DialogFragment newHabitFragment = NewHabitFragment.newInstance();
    newHabitFragment.show(getChildFragmentManager(), NewHabitFragment.TAG);
  }
}
