package com.vincent.hoangnguyen.project1.ui.NhapHang;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NhapHangViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public NhapHangViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
