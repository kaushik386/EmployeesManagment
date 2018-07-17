package com.managment.employees.employeesmanagment.Room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.managment.employees.employeesmanagment.Model.Employee;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MyDao {

    @Insert
    void insert(Employee employees);

    @Insert
    void insertAll(List<Employee> employeeList);

    @Query("select * from employeelist")
  LiveData<List<Employee>> getAll();

    @Query("select * from employeelist where id =:mId")
    LiveData<Employee> getEmployeeWith(int mId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTodo(Employee employee);

}
