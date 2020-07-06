package com.appolinary.mvvmexample1.repos;

import android.arch.lifecycle.MutableLiveData;

import com.appolinary.mvvmexample1.models.NicePlace;

import java.util.ArrayList;
import java.util.List;

public class NicePlaceRepo {

    private static NicePlaceRepo instance;
    private ArrayList<NicePlace> dataSet = new ArrayList<>();

    //Singleton pattern
    public static NicePlaceRepo getInstance(){
        if(instance == null){
            instance = new NicePlaceRepo();
        }
        return instance;
    }

    public MutableLiveData<List<NicePlace>> getNicePlaces() {
        setNicePlaces();//get data from DB or API

        MutableLiveData<List<NicePlace>> data = new MutableLiveData<>();
        data.setValue(dataSet);//и повесили свежие данные в наш основной контейнер(MutableLiveData, который observable) для данных

        return data;
    }

    private void setNicePlaces(){
        //fake method. here we need to get data from DB or from network...
        //but we'll just hardcode it
        dataSet.add(
                new NicePlace("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg",
                        "Havasu Falls")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/tpsnoz5bzo501.jpg",
                        "Trondheim")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/qn7f9oqu7o501.jpg",
                        "Portugal")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/j6myfqglup501.jpg",
                        "Rocky Mountain National Park")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/0h2gm1ix6p501.jpg",
                        "Havasu Falls")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/k98uzl68eh501.jpg",
                        "Mahahual")
        );
        dataSet.add(
                new NicePlace("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg",
                        "Frozen Lake")
        );
        dataSet.add(
                new NicePlace("https://i.redd.it/obx4zydshg601.jpg",
                        "Austrailia")
        );

    }
}
