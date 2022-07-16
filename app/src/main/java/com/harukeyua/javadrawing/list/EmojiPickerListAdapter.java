package com.harukeyua.javadrawing.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.harukeyua.javadrawing.databinding.EmojiItemBinding;
import com.harukeyua.javadrawing.model.EmojiPickerItemModel;

public class EmojiPickerListAdapter extends ListAdapter<EmojiPickerItemModel, EmojiPickerListAdapter.EmojiPickerViewHolder> {

    private final EmojiClickedCallback onEmojiClickedCallback;

    public EmojiPickerListAdapter(EmojiClickedCallback onEmojiClickedCallback) {
        super(new EmojiPickerDiffCallback());
        this.onEmojiClickedCallback = onEmojiClickedCallback;

    }

    @NonNull
    @Override
    public EmojiPickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmojiPickerViewHolder(
                EmojiItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),
                onEmojiClickedCallback
        );
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiPickerViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    static class EmojiPickerViewHolder extends RecyclerView.ViewHolder {
        private final EmojiItemBinding binding;
        private EmojiPickerItemModel currentItem = null;

        public EmojiPickerViewHolder(EmojiItemBinding binding, EmojiClickedCallback onEmojiClickedCallback) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                if (currentItem != null) {
                    onEmojiClickedCallback.onEmojiClicked(currentItem);
                }
            });
        }

        public void bind(EmojiPickerItemModel item) {
            currentItem = item;
            binding.emojiText.setText(item.getEmoji());
            binding.selectionImage.setVisibility(item.getSelected() ? View.VISIBLE : View.GONE);
        }
    }

    static class EmojiPickerDiffCallback extends DiffUtil.ItemCallback<EmojiPickerItemModel> {

        @Override
        public boolean areItemsTheSame(@NonNull EmojiPickerItemModel oldItem, @NonNull EmojiPickerItemModel newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull EmojiPickerItemModel oldItem, @NonNull EmojiPickerItemModel newItem) {
            return false;
        }
    }
}
