package com.managment.employees.employeesmanagment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.managment.employees.employeesmanagment.Model.Employee;
import com.squareup.picasso.Picasso;

import java.util.List;


public class EmployeesListAdapter extends RecyclerView.Adapter<EmployeesListAdapter.EmployeesHolder> {

    private Context mContext;
    private List<Employee> mEmployees;
    private CustomItemClickListener mListener;

    public EmployeesListAdapter(Context context, List<Employee> employees,CustomItemClickListener listener) {
        this.mContext = context;
        mEmployees = employees;
        mListener= listener;
    }

    @NonNull
    @Override
    public EmployeesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.employees_list, parent, false);
    final EmployeesHolder employeesHolder = new EmployeesHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v,employeesHolder.getAdapterPosition());
            }
        });
        return employeesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeesHolder holder, int position) {
        Employee employee = mEmployees.get(position);
        holder.mEmployeeName.setText(employee.getName());
        Picasso.with(mContext).load(employee.getPhoto()).into(holder.mEmployeeImage);

    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
    }

    public class EmployeesHolder extends RecyclerView.ViewHolder {

        TextView mEmployeeName;
        ImageView mEmployeeImage;

        public EmployeesHolder(View itemView) {
            super(itemView);
            mEmployeeName = (TextView) itemView.findViewById(R.id.employee_name);
            mEmployeeImage = (ImageView) itemView.findViewById(R.id.employee_image);
        }
    }
}
