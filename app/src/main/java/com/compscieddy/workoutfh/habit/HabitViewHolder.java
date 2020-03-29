package com.compscieddy.workoutfh.habit;

import android.view.View;
import android.widget.TextView;

import com.compscieddy.workoutfh.R;
import com.compscieddy.workoutfh.model.Habit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.FrameLayout;

public class HabitViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.habit_name) TextView mHabitName;
  @BindView(R.id.habit_count) TextView mHabitCount;
  @BindView(R.id.habit_record_button) FrameLayout mHabitRecordButton;

  public HabitViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  void setHabitModel(Habit habit) {
    mHabitName.setText(habit.getHabitName());
  }

}
