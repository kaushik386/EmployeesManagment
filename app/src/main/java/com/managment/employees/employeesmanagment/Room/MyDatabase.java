package com.managment.employees.employeesmanagment.Room;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.managment.employees.employeesmanagment.Model.Employee;

@Database(entities = {Employee.class},version = 1,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static final String DATABASE ="employeelist";

    private static MyDatabase mInstance;

    private static final Object LOCK = new Object();
    public  abstract MyDao myDao();

    public static MyDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCK) {
                mInstance = Room.databaseBuilder(context.getApplicationContext(),MyDatabase.class,MyDatabase.DATABASE).build();
            }
        }
        return  mInstance;
    }
}
