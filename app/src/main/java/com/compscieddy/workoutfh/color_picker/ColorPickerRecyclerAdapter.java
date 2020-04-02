package com.compscieddy.workoutfh.color_picker;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.compscieddy.workoutfh.R;

/** This is the adapter for showing the list of colors for users to select in the new habit dialog. */

public class ColorPickerRecyclerAdapter extends RecyclerView.Adapter<ColorPickerViewHolder> {

    private int[] mColors;

    private ColorPickerCallBack mColorPickerCallback;

    public ColorPickerRecyclerAdapter(int[] colors, ColorPickerCallBack callback) {
        mColors = colors;
        mColorPickerCallback = callback;
    }

    @NonNull
    @Override
    public ColorPickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ColorPickerViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color_picker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ColorPickerViewHolder holder, int position) {
        holder.setColor(mColors[position]);
        holder.mClickableColorItem.setOnClickListener(v -> {
            mColorPickerCallback.onColorSelected(mColors[position]);
        });
    }

    @Override
    public int getItemCount() {
        return mColors.length;
    }

    public interface ColorPickerCallBack {
        void onColorSelected(int color);
    }

}
