package com.tech4lyf.absolutesolutionscrm.ui.customers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CustomersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}