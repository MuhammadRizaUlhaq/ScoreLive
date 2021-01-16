package com.example.scorelive.ui.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoritesViewModel extends ViewModel {

    private MutableLiveData<Integer> mPosition;

    public FavoritesViewModel() {}

    public void setPosition(int position) {
        mPosition.setValue(position);
    }

    public LiveData<Integer> getPosition() {
        return mPosition;
    }
}