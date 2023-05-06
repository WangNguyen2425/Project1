package com.vincent.hoangnguyen.project1.ui.TongQuan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TongQuanViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TongQuanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}