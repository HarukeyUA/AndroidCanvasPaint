package com.harukeyua.javadrawing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.harukeyua.javadrawing.databinding.ActivityMainBinding;
import com.harukeyua.javadrawing.list.ColorPickerListAdapter;
import com.harukeyua.javadrawing.list.ColorsListDecoration;
import com.harukeyua.javadrawing.list.EmojiPickerListAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ColorPickerListAdapter listAdapter;
    private EmojiPickerListAdapter emojiAdapter;
    private DrawViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(DrawViewModel.class);
        listAdapter = new ColorPickerListAdapter(item -> {
            viewModel.setColorSelection(item);
        });
        emojiAdapter = new EmojiPickerListAdapter(item -> {
            viewModel.setEmojiSelection(item);
        });
        viewModel.getColorPickerListLiveData().observe(this, colorPickerItemModels -> listAdapter.submitList(colorPickerItemModels));
        viewModel.getEmojiPickerListLiveData().observe(this, emojiPickerItemModels -> emojiAdapter.submitList(emojiPickerItemModels));

        binding.colorPickerList.setItemAnimator(null);
        binding.colorPickerList.setAdapter(listAdapter);
        binding.colorPickerList.addItemDecoration(new ColorsListDecoration());
        binding.emojiPickerList.setItemAnimator(null);
        binding.emojiPickerList.setAdapter(emojiAdapter);

        viewModel.getSelectedColorLiveData().observe(this, color -> {
            if (color != null) {
                binding.paintSurface.setPaintColor(color);
            }
        });

        viewModel.getSelectedEmojiLiveData().observe(this, emoji -> {
            if (emoji != null) {
                binding.paintSurface.setEmoji(emoji);
            }
        });
    }
}