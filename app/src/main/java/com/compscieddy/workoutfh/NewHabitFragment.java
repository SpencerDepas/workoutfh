package com.compscieddy.workoutfh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.compscieddy.workoutfh.model.Habit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NewHabitFragment extends DialogFragment {

  public static final String TAG = NewHabitFragment.class.getSimpleName();
  private View mRootView;
  private View mSubmitButton;
  private EditText mHabitNameEditText;

  public static NewHabitFragment newInstance() {
    return new NewHabitFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.FloatingFullscreenDialogTheme);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mRootView = inflater.inflate(R.layout.fragment_new_habit, container, false);
    /** ButterKnife does not appear to work, debugging was proving to be a waste of time. */
    initViews();
    return mRootView;
  }

  @Override
  public void onStart() {
    super.onStart();
    getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
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

  private void initViews() {
    mSubmitButton = mRootView.findViewById(R.id.new_habit_submit_button);
    mHabitNameEditText = mRootView.findViewById(R.id.new_habit_name_input);
  }

  private void attachListeners() {
    mSubmitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String habitName = mHabitNameEditText.getText().toString();
        Habit.createNewHabitOnFirestore(habitName);
      }
    });
  }

  private void detachListeners() {

  }
}
