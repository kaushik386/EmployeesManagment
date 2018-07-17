package com.managment.employees.employeesmanagment;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.managment.employees.employeesmanagment.Model.Employee;
import com.managment.employees.employeesmanagment.Room.MyDatabase;

import java.util.List;

public class DetailModel extends ViewModel {


    private LiveData<Employee> employee;


    public DetailModel(MyDatabase myDatabase,int position) {
      employee=myDatabase.myDao().getEmployeeWith(position);
    }


    public LiveData<Employee> getEmployee() {
        return employee;
    }
}
