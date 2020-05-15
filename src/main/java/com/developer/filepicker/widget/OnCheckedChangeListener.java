package com.developer.filepicker.widget;

/**
 * @author akshay sunil masram
 */
public interface OnCheckedChangeListener {
    void onCheckedChanged(MaterialCheckbox checkbox, boolean isChecked);
    void onCheckedChanged(DeleteCheckBox buttonbox, boolean isChecked);
    void onCheckedChanged(CopyCheckBox copyCheckBox, boolean isChecked);
}