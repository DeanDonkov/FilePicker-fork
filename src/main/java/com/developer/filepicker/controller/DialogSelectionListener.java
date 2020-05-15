package com.developer.filepicker.controller;

/**
 * @author akshay sunil masram
 */
public interface DialogSelectionListener {
    void onSelectedFilePaths(String[] files);
    void onSelectedFilePathsToRemove(String[] files);
    void onSelectedFilePathsToCopy(String[] files);
}