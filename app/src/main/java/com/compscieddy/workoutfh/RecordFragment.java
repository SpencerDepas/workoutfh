package com.compscieddy.workoutfh;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RecordFragment extends FloatingBaseFragment {

  public static final String TAG = RecordFragment.class.getSimpleName();

  private View mRootView;
  private EditText mHabitCountEditText;

  public static RecordFragment newInstance() {
    return new RecordFragment();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mRootView = inflater.inflate(R.layout.fragment_record, container, false);
    initViews();
    init();
    return mRootView;
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

  @Override
  View getBlackBackground() {
    return mRootView.findViewById(R.id.black_background);
  }

  @Override
  View getMainDialogContainer() {
    return mRootView.findViewById(R.id.main_dialog_container);
  }

  private void initViews() {
    mHabitCountEditText = mRootView.findViewById(R.id.habit_count_edit_text);
  }

  private void init() {
    mHabitCountEditText.post(new Runnable() {
      @Override
      public void run() {
        mHabitCountEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mHabitCountEditText, InputMethodManager.SHOW_IMPLICIT);
      }
    });
  }

  private void attachListeners() {

  }

  private void detachListeners() {

  }
}
