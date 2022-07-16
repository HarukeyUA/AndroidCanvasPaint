package com.harukeyua.javadrawing;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.harukeyua.javadrawing.model.ColorPickerItemModel;
import com.harukeyua.javadrawing.model.EmojiPickerItemModel;
import com.harukeyua.javadrawing.utils.CombinedLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class DrawViewModel extends ViewModel {

    private final MutableLiveData<List<ColorPickerItemModel>> availableColorsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectedColorLiveData = new MutableLiveData<>(R.color.black);

    private final CombinedLiveData<List<ColorPickerItemModel>, Integer, List<ColorPickerItemModel>> colorPickerList =
            new CombinedLiveData<>(
                    availableColorsLiveData,
                    selectedColorLiveData,
                    (colorPickerItemModels, selectedColor) -> {
                        ArrayList<ColorPickerItemModel> appliedSelectionList = new ArrayList<>();
                        for (ColorPickerItemModel colorPickerItemModel : colorPickerItemModels) {
                            if (colorPickerItemModel.getColor().equals(selectedColor)) {
                                appliedSelectionList.add(new ColorPickerItemModel(selectedColor, true));
                            } else {
                                appliedSelectionList.add(colorPickerItemModel);
                            }
                        }
                        return appliedSelectionList;
                    });

    private final MutableLiveData<List<EmojiPickerItemModel>> availableEmojiLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> selectedEmojiLiveData = new MutableLiveData<>(null);

    private final CombinedLiveData<List<EmojiPickerItemModel>, String, List<EmojiPickerItemModel>> emojiPickerList =
            new CombinedLiveData<>(
                    availableEmojiLiveData,
                    selectedEmojiLiveData,
                    (emojiPickerItemModels, selectedEmoji) -> {
                        ArrayList<EmojiPickerItemModel> appliedSelectionList = new ArrayList<>();
                        for (EmojiPickerItemModel emojiPickerItemModel : emojiPickerItemModels) {
                            if (emojiPickerItemModel.getEmoji().equals(selectedEmoji)) {
                                appliedSelectionList.add(new EmojiPickerItemModel(selectedEmoji, true));
                            } else {
                                appliedSelectionList.add(emojiPickerItemModel);
                            }
                        }
                        return appliedSelectionList;
                    }
            );

    public DrawViewModel() {
        initAvailableColorsList();
        initAvailableEmojiList();
    }

    private void initAvailableColorsList() {
        ArrayList<ColorPickerItemModel> colors = new ArrayList<>();
        colors.add(new ColorPickerItemModel(R.color.black));
        colors.add(new ColorPickerItemModel(R.color.material_red));
        colors.add(new ColorPickerItemModel(R.color.material_pink));
        colors.add(new ColorPickerItemModel(R.color.material_purple));
        colors.add(new ColorPickerItemModel(R.color.material_deep_purple));
        colors.add(new ColorPickerItemModel(R.color.material_indigo));
        colors.add(new ColorPickerItemModel(R.color.material_blue));
        colors.add(new ColorPickerItemModel(R.color.material_light_blue));
        colors.add(new ColorPickerItemModel(R.color.material_cyan));
        availableColorsLiveData.setValue(colors);
    }

    private void initAvailableEmojiList() {
        ArrayList<EmojiPickerItemModel> emojiList = new ArrayList<>();
        emojiList.add(new EmojiPickerItemModel("\uD83D\uDE0D"));
        emojiList.add(new EmojiPickerItemModel("\uD83D\uDE02"));
        emojiList.add(new EmojiPickerItemModel("\uD83D\uDE00"));
        emojiList.add(new EmojiPickerItemModel("\uD83D\uDE09"));
        emojiList.add(new EmojiPickerItemModel("\uD83D\uDE0B"));
        emojiList.add(new EmojiPickerItemModel("\uD83D\uDE0E"));
        emojiList.add(new EmojiPickerItemModel("\uD83D\uDE18"));
        emojiList.add(new EmojiPickerItemModel("\uD83D\uDE42"));
        emojiList.add(new EmojiPickerItemModel("\uD83D\uDE44"));
        emojiList.add(new EmojiPickerItemModel("\uD83D\uDE0F"));
        emojiList.add(new EmojiPickerItemModel("\uD83D\uDE23"));
        availableEmojiLiveData.setValue(emojiList);
    }

    public void setColorSelection(@NonNull ColorPickerItemModel colorSelection) {
        clearEmojiSelection();
        selectedColorLiveData.setValue(colorSelection.getColor());
    }

    public void clearColorSelection() {
        selectedColorLiveData.setValue(null);
    }

    public LiveData<List<ColorPickerItemModel>> getColorPickerListLiveData() {
        return colorPickerList;
    }

    public LiveData<Integer> getSelectedColorLiveData() {
        return selectedColorLiveData;
    }

    public LiveData<List<EmojiPickerItemModel>> getEmojiPickerListLiveData() {
        return emojiPickerList;
    }

    public LiveData<String> getSelectedEmojiLiveData() {
        return selectedEmojiLiveData;
    }

    public void setEmojiSelection(@NonNull EmojiPickerItemModel emojiSelection) {
        clearColorSelection();
        selectedEmojiLiveData.setValue(emojiSelection.getEmoji());
    }

    public void clearEmojiSelection() {
        selectedEmojiLiveData.setValue(null);
    }
}
