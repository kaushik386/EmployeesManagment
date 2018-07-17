package com.managment.employees.employeesmanagment.services;

import com.managment.employees.employeesmanagment.Model.Employee;
import com.managment.employees.employeesmanagment.Model.Response;

import java.util.List;

import io.reactivex.Observable;

import retrofit2.http.GET;

public interface ApiServices {

@GET("kaushik386/demoDb/employees")
Observable<List<Employee>> getEmployeesList();

}
