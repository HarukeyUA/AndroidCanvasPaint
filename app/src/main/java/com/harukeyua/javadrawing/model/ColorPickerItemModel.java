package com.harukeyua.javadrawing.model;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

public class ColorPickerItemModel {
    @ColorRes
    private final Integer color;
    private Boolean isSelected = false;

    public ColorPickerItemModel(int color) {
        this.color = color;
    }

    public ColorPickerItemModel(int color, boolean isSelected) {
        this.color = color;
        this.isSelected = isSelected;
    }

    @ColorRes
    @NonNull
    public Integer getColor() {
        return color;
    }

    public Boolean getSelected() {
        return isSelected;
    }
}
