package com.managment.employees.employeesmanagment;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.managment.employees.employeesmanagment.Model.Employee;
import com.managment.employees.employeesmanagment.Room.MyDatabase;
import com.managment.employees.employeesmanagment.services.ApiClient;
import com.managment.employees.employeesmanagment.services.ApiServices;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabase database;
    ApiServices apiServices;
    List<Employee> employeeList;
    EmployeesListAdapter employeesListAdapter;
    private int clickedPosition;
    private MyDatabase myDatabase;
    private CompositeDisposable disposable = new CompositeDisposable();
    private Button button;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String DataBacked = "DataBacked";
    ProgressBar progressBar;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiServices = ApiClient.getClient(getApplicationContext()).create(ApiServices.class);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDatabase = MyDatabase.getInstance(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        getEmployeesDetails().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribeWith(new DisposableObserver<List<Employee>>() {
            @Override
            public void onNext(List<Employee> employees) {
                employeeList = employees;
                insertEmployees(employeeList);
            }

            @Override
            public void onError(Throwable e) {
                e.getCause();
            }

            @Override
            public void onComplete() {
                SetupAdapterRecycleView();
                progressBar.setVisibility(View.GONE);

            }
        });


    }

    void getDataFromBackup() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                myDatabase.myDao().getAll().observe(MainActivity.this, new Observer<List<Employee>>() {
                    @Override
                    public void onChanged(@Nullable List<Employee> employees) {
                        employeeList = employees;
                        SetupAdapterRecycleView();
                    }
                });
            }
        });
    }

    void SetupAdapterRecycleView() {
        if (employeeList != null) {
            employeesListAdapter = new EmployeesListAdapter(MainActivity.this, employeeList, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {

                    callDetailActivity(position);
                }
            });
            recyclerView.setAdapter(employeesListAdapter);
        }
    }

    void callDetailActivity(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Constants.POSITION, position);
        startActivity(intent);
    }


    private Observable<List<Employee>> getEmployeesDetails() {
        return apiServices.getEmployeesList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }


    void insertEmployees(final List<Employee> employees) {
        if (!sharedpreferences.contains(DataBacked)) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (employees != null)
                        myDatabase.myDao().insertAll(employees);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(DataBacked, true);
                    editor.commit();

                }
            });

        }

    }

    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }
}
