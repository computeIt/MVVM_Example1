package com.appolinary.mvvmexample1.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.appolinary.mvvmexample1.models.NicePlace;
import com.appolinary.mvvmexample1.repos.NicePlaceRepo;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<NicePlace>> mNicePlaces;//MutableLiveData class can be changed,
    // LiveData is immutable class and can only be observed
    // and MutableLiveData extends LiveData
    private NicePlaceRepo mRepo;

    public void init(){
        if(mNicePlaces != null){//it means that we already retrieved LiveData
            return;
        }
        mRepo = NicePlaceRepo.getInstance();
        mNicePlaces = mRepo.getNicePlaces();
    }

    public LiveData<List<NicePlace>> getNicePlaces(){
        return mNicePlaces;
    }
}
