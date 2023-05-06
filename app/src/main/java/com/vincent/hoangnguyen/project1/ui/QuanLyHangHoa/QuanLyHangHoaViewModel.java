package com.vincent.hoangnguyen.project1.ui.QuanLyHangHoa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuanLyHangHoaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public QuanLyHangHoaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}