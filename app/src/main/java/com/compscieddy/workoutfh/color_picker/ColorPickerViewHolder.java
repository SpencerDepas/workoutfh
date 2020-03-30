package com.compscieddy.workoutfh.color_picker;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compscieddy.workoutfh.R;
import com.compscieddy.workoutfh.model.HabitRecord;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorPickerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.color_item)
    RelativeLayout mColorItem;

    private int color;

    public ColorPickerViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(ColorPickerViewHolder.this, itemView);
        init();
    }

    void setColor(int color) {
        this.color = color;
        initHabitRecord();
    }

    private void init() {

    }

    private void initHabitRecord() {
        Drawable drawable = mColorItem.getContext().getResources().getDrawable(R.drawable.color_picker_circle);
        GradientDrawable gradientDrawable = (GradientDrawable) drawable;
        gradientDrawable.setColor(color);
        mColorItem.setBackground(gradientDrawable);
    }
}
