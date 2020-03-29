package com.compscieddy.workoutfh.habit;

import android.view.View;
import android.widget.TextView;

import com.compscieddy.workoutfh.R;
import com.compscieddy.workoutfh.RecordHabitFragment;
import com.compscieddy.workoutfh.model.Habit;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.FrameLayout;

public class HabitViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.habit_name) TextView mHabitName;
  @BindView(R.id.habit_count) TextView mHabitCount;
  @BindView(R.id.habit_record_button) FrameLayout mHabitRecordButton;

  private FragmentManager mChildFragmentManager;
  private Habit mHabit;

  public HabitViewHolder(FragmentManager childFragmentManager, @NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    mChildFragmentManager = childFragmentManager;
    init();
  }

  void setHabitModel(Habit habit) {
    mHabit = habit;
    initHabit();
  }

  private void init() {
    mHabitRecordButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        RecordHabitFragment recordHabitFragment = RecordHabitFragment.newInstance();
        recordHabitFragment.setHabit(mHabit);
        recordHabitFragment.show(mChildFragmentManager, RecordHabitFragment.TAG);
      }
    });
  }

  private void initHabit() {
    mHabitName.setText(mHabit.getHabitName());
  }

}
