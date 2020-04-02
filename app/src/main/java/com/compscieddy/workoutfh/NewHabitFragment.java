package com.compscieddy.workoutfh;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compscieddy.workoutfh.color_picker.ColorPickerRecyclerAdapter;
import com.compscieddy.workoutfh.model.Habit;
import com.compscieddy.workoutfh.ui.FloatingBaseFragment;

public class NewHabitFragment extends FloatingBaseFragment implements ColorPickerRecyclerAdapter.ColorPickerCallBack {

    public static final String TAG = NewHabitFragment.class.getSimpleName();
    private View mRootView;
    private View mSubmitButton;
    private RecyclerView mRecyclerView;
    private EditText mHabitNameEditText;
    private View mBlackBackground;
    private View mMainDialogContainer;
    private int habitColor = R.color.muted_red;

    public static NewHabitFragment newInstance() {
        return new NewHabitFragment();
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
    public View getBlackBackground() {
        return mBlackBackground;
    }

    @Override
    public View getMainDialogContainer() {
        return mMainDialogContainer;
    }

    @Override
    public View getKeyboardFocusView() {
        return mHabitNameEditText;
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
        mBlackBackground = mRootView.findViewById(R.id.black_background);
        mMainDialogContainer = mRootView.findViewById(R.id.main_dialog_container);
        mRecyclerView = mRootView.findViewById(R.id.mtrl_colors);
        initRvAdapter();
    }

    private void initRvAdapter() {

        LinearLayoutManager horizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ColorPickerRecyclerAdapter adapter = new ColorPickerRecyclerAdapter(getResources().getIntArray(R.array.habit_colors), this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(horizontalLayout);
    }

    private void attachListeners() {
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habitName = mHabitNameEditText.getText().toString();

                if (TextUtils.isEmpty(habitName)) {
                    mHabitNameEditText.animate()
                            .setDuration(300)
                            .scaleX(1.2f)
                            .scaleY(1.2f)
                            .setInterpolator(new BounceInterpolator())
                            .withEndAction(() -> mHabitNameEditText.animate()
                                    .setDuration(300)
                                    .scaleX(1.0f)
                                    .scaleY(1.0f)
                                    .setInterpolator(new BounceInterpolator()));
                    return;
                }


                Habit.createNewHabitOnFirestore(habitName, habitColor);
                dismissWithAnimation();
            }
        });
    }

    private void detachListeners() {
        mSubmitButton.setOnClickListener(null);
        mBlackBackground.setOnClickListener(null);
    }

    @Override
    public void onColorSelected(int color) {
        mSubmitButton.setBackgroundColor(0xff000000 + color);
        habitColor = color;
    }
}
