package com.managment.employees.employeesmanagment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.managment.employees.employeesmanagment.Room.MyDatabase;


public class DetailModelFactory extends ViewModelProvider.NewInstanceFactory {


    private final MyDatabase myDatabase;
    private  final int position;

    public DetailModelFactory(MyDatabase myDatabase, int position) {
        this.myDatabase = myDatabase;
        this.position = position;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailModel(myDatabase,position);
    }
}
