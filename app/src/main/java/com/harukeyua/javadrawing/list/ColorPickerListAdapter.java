package com.harukeyua.javadrawing.list;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.harukeyua.javadrawing.databinding.ColorItemBinding;
import com.harukeyua.javadrawing.model.ColorPickerItemModel;

public class ColorPickerListAdapter extends ListAdapter<ColorPickerItemModel, ColorPickerListAdapter.ColorPickerViewHolder> {

    private final ColorClickedCallback onColorClickedCallback;

    public ColorPickerListAdapter(ColorClickedCallback clickedCallback) {
        super(new ColorPickerDiffCallback());
        onColorClickedCallback = clickedCallback;
    }

    @NonNull
    @Override
    public ColorPickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ColorPickerViewHolder(
                ColorItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),
                onColorClickedCallback
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ColorPickerViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    static class ColorPickerViewHolder extends RecyclerView.ViewHolder {

        private final ColorItemBinding binding;
        private ColorPickerItemModel currentItem = null;

        public ColorPickerViewHolder(ColorItemBinding binding, ColorClickedCallback onColorClickedCallback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                if (currentItem != null) {
                    onColorClickedCallback.onColorClicked(currentItem);
                }
            });
        }

        public void bind(ColorPickerItemModel item) {
            currentItem = item;
            binding.colorBackground.setBackgroundTintList(ColorStateList.valueOf(getColorFromRes(item.getColor())));
            binding.selectionImage.setVisibility(item.getSelected() ? View.VISIBLE : View.GONE);
        }

        private int getColorFromRes(@ColorRes int color) {
            return ContextCompat.getColor(binding.getRoot().getContext(), color);
        }
    }

    static class ColorPickerDiffCallback extends DiffUtil.ItemCallback<ColorPickerItemModel> {

        @Override
        public boolean areItemsTheSame(@NonNull ColorPickerItemModel oldItem, @NonNull ColorPickerItemModel newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ColorPickerItemModel oldItem, @NonNull ColorPickerItemModel newItem) {
            return false;
        }
    }
}
