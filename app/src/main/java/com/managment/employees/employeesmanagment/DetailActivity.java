package com.managment.employees.employeesmanagment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.managment.employees.employeesmanagment.Model.Employee;
import com.managment.employees.employeesmanagment.Room.MyDatabase;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {


    TextView mEmpName;
    TextView mEmpAge;
    TextView mEmpGender;
    TextView mEmpDes;
    ImageView mEmpPic;
    TextView mLeave;
    Button mHalf;
    Button mFull;
    Button mApprove;
    EditText mNoLeaves;
    int mPosition;
    MyDatabase myDatabase;
    Employee tempEmployee;
    LiveData<Employee> mEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Employee's Detail");
        mEmpName = findViewById(R.id.emp_name);
        mEmpAge = findViewById(R.id.emp_age);
        mEmpDes = findViewById(R.id.emp_des);
        mEmpGender = findViewById(R.id.emp_gender);
        mEmpPic = findViewById(R.id.emp_pic);
        mLeave = findViewById(R.id.emp_leave);
        mHalf = findViewById(R.id.halfday_leave);
        mFull =findViewById(R.id.fullday_leave);
        mApprove = findViewById(R.id.approve_leave);
        mNoLeaves =findViewById(R.id.more_leave);

        mHalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempEmployee.setLeaves(tempEmployee.getLeaves()+.5);
                mLeave.setText(""+tempEmployee.getLeaves());
                UpdateDataset();

            }
        });
        mFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempEmployee.setLeaves(tempEmployee.getLeaves()+1);
                mLeave.setText(""+tempEmployee.getLeaves());
                UpdateDataset();
            }
        });
        mApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mNoLeaves.getText())) {
                   int temp= Integer.valueOf(mNoLeaves.getText().toString());
                    tempEmployee.setLeaves(tempEmployee.getLeaves() + temp);
                    mLeave.setText("" + tempEmployee.getLeaves());
                    UpdateDataset();
                }
                else{
                    Toast.makeText(DetailActivity.this,"Enter No. of day",Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(getIntent()!=null)
        {
            mPosition = getIntent().getIntExtra(Constants.POSITION,-1);
        }
        myDatabase = MyDatabase.getInstance(this);

        DetailModelFactory detailModelFactory = new DetailModelFactory(myDatabase,mPosition+1);
        mEmployee = ViewModelProviders.of(this,detailModelFactory).get(DetailModel.class).getEmployee();

        mEmployee.observe(this, new Observer<Employee>() {
            @Override
            public void onChanged(@Nullable Employee employee) {
                mEmployee.removeObserver(this);
                tempEmployee=employee;
                fillView(employee);
            }
        });



    }
    void UpdateDataset()
    {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                myDatabase.myDao().updateTodo(tempEmployee);
            }
        });
    }

    private void fillView(Employee employee) {
        mEmpName.setText(employee.getName());
        mEmpAge.setText(employee.getAge());
        mEmpDes.setText(employee.getDesignation());
        mEmpGender.setText(employee.getGender());
        mLeave.setText(""+employee.getLeaves());
        Picasso.with(this).load(employee.getPhoto()).into(mEmpPic);

    }
}
