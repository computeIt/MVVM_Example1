package com.appolinary.mvvmexample1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.appolinary.mvvmexample1.adapters.RecyclerAdapter;
import com.appolinary.mvvmexample1.models.NicePlace;
import com.appolinary.mvvmexample1.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressbBar;
    private RecyclerAdapter mAdapter;
    private MainActivityViewModel mMainActivityViewModel;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressbBar = findViewById(R.id.progress_bar);


        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        //Для получения ссылки на наш экземпляр ViewModel мы должны воспользоваться ViewModelProviders

        mMainActivityViewModel.init();
        //чтобы подписаться на изменения наших данных, используем следующую конструкцию
        //this ниже - это активити, к жизненному циклу которой мы привязываемся
        mMainActivityViewModel.getNicePlaces().observe(this, new Observer<List<NicePlace>>() {
            @Override
            public void onChanged(@Nullable List<NicePlace> nicePlaces) {
                mAdapter.notifyDataSetChanged();//если данные изменились, то мы уведомляем об этом адаптер
            }
        });

        mMainActivityViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            //тут мы подписываемся на изменения в случае, если чтото добавляется в список
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    showProgressBar();
                } else {
                    hideProgressBar();
                    mRecyclerView.smoothScrollToPosition(mMainActivityViewModel.getNicePlaces().getValue().size() - 1);
                    //тк мы добавляем в конец списка, то и прокручиваем потом список до конца
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainActivityViewModel.addNewValue(new NicePlace(//добавим по кнопке еще одну позицию в список(хардкод :-()
                        "https://i.imgur.com/ZcLLrkY.jpg",
                        "Washington"
                ));
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new RecyclerAdapter(this, mMainActivityViewModel.getNicePlaces().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showProgressBar() {
        mProgressbBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressbBar.setVisibility(View.GONE);
    }

}
