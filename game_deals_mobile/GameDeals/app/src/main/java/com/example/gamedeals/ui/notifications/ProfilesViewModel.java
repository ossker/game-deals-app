package com.example.gamedeals.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfilesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProfilesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is profiles fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}