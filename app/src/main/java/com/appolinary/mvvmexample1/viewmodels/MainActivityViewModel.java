package com.appolinary.mvvmexample1.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.appolinary.mvvmexample1.models.NicePlace;
import com.appolinary.mvvmexample1.repos.NicePlaceRepo;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<NicePlace>> mNicePlaces;//MutableLiveData class can be changed,
    // LiveData is immutable class and can only be observed
    // and MutableLiveData extends LiveData
    private NicePlaceRepo mRepo;

    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();//for progresBar
    // - it will be shown during updating

    public void init(){
        if(mNicePlaces != null){//it means that we already retrieved LiveData
            return;
        }
        mRepo = NicePlaceRepo.getInstance();
        mNicePlaces = mRepo.getNicePlaces();
    }

    public void addNewValue(final NicePlace nicePlace){
        mIsUpdating.setValue(true);

        //just for this example - we need to mimic network request
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<NicePlace> currentPlaces = mNicePlaces.getValue();
                currentPlaces.add(nicePlace);//adding new place to our list
                mNicePlaces.postValue(currentPlaces);//submit new updated version of livedata list
                mIsUpdating.postValue(false);//update is complete
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public LiveData<Boolean> getIsUpdating(){
        return mIsUpdating;
    }

    public LiveData<List<NicePlace>> getNicePlaces(){
        return mNicePlaces;
    }
}
