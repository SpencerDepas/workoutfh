package com.compscieddy.workoutfh.color_picker;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compscieddy.workoutfh.R;

public class ColorPickerRecyclerAdapter extends RecyclerView.Adapter<ColorPickerViewHolder> {

    private int[] mColors;

    public ColorPickerRecyclerAdapter( int[] colors) {
        mColors = colors;
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
    }

    @Override
    public int getItemCount() {
        return mColors.length;
    }

}
