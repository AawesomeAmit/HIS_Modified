package com.trueform.era.his.Fragment.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModelIntake extends ViewModel {

    private MutableLiveData<Integer> mIndex1 = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex1, input -> "Hello world from section: " + input);

    public void setIndex(int index) {
        mIndex1.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}