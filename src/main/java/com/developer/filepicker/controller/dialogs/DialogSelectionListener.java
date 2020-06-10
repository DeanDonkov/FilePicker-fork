package com.developer.filepicker.controller.dialogs;

/**
 * @author akshay sunil masram
 */
public interface DialogSelectionListener {
    void onSelectedFilePathsToEncrypt(String[] files);
    void onSelectedFilePathsToCopy(String[] files);
    void onSelectedFilePaths(String[] files);
    void onSelectedFilePathsToRemove(String[] files);

    void onSelectedFilePathsToDecrypt(String[] files);

    void onSelectedPathsToExtract(String[] files);
}