package com.compscieddy.workoutfh.color_picker;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.compscieddy.workoutfh.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorPickerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.color_item) RelativeLayout mColorItem;
    @BindView(R.id.clickable_color_item) Button mClickableColorItem;

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
        GradientDrawable gradientDrawable = (GradientDrawable) mColorItem.getContext().getResources().getDrawable(R.drawable.color_picker_circle);
        gradientDrawable.setColor(color);
        mColorItem.setBackground(gradientDrawable);
    }
}
