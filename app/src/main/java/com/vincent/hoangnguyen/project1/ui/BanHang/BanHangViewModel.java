package com.vincent.hoangnguyen.project1.ui.BanHang;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BanHangViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BanHangViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is BanHang fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}