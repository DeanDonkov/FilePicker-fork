package com.developer.filepicker.widget.listeners;

import com.developer.filepicker.widget.CopyCheckBox;
import com.developer.filepicker.widget.DecryptCheckBox;
import com.developer.filepicker.widget.EncryptCheckBox;
import com.developer.filepicker.widget.DeleteCheckBox;
import com.developer.filepicker.widget.ExtractCheckBox;
import com.developer.filepicker.widget.MaterialCheckbox;
import com.developer.filepicker.widget.ShareCheckBox;

/**
 * @author akshay sunil masram
 */
public interface OnCheckedChangeListener {
    void onCheckedChanged(CopyCheckBox copyCheckBox, boolean isChecked);
    void onCheckedChanged(EncryptCheckBox encryptCheckBox, boolean isChecked);
    void onCheckedChanged(DeleteCheckBox buttonbox, boolean isChecked);
    void onCheckedChanged(MaterialCheckbox checkbox, boolean isChecked);

    void onCheckedChanged(DecryptCheckBox decryptCheckBox, boolean isChecked);
    void onCheckedChanged(ExtractCheckBox extractCheckBox, boolean isChecked);

    void onCheckedChanged(ShareCheckBox shareCheckBox, boolean isChecked);
}