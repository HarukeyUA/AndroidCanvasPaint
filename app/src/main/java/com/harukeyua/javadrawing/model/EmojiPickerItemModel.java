package com.harukeyua.javadrawing.model;

public class EmojiPickerItemModel {
    private final String emoji;
    private Boolean isSelected = false;

    public EmojiPickerItemModel(String emoji) {
        this.emoji = emoji;
    }

    public EmojiPickerItemModel(String emoji, Boolean isSelected) {
        this.emoji = emoji;
        this.isSelected = isSelected;
    }

    public String getEmoji() {
        return emoji;
    }

    public Boolean getSelected() {
        return isSelected;
    }
}
