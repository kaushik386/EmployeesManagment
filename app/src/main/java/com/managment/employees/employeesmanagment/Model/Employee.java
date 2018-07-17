package com.managment.employees.employeesmanagment.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.IntDef;

@Entity(tableName = "employeelist")
public class Employee {

    @PrimaryKey
    int id;

    String name;
    String age;
    String gender;
    String designation;
    String photo;


    double leaves ;

    public double getLeaves() {
        return leaves;
    }

    public void setLeaves(double leaves) {
        this.leaves = leaves;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getDesignation() {
        return designation;
    }

    public String getPhoto() {
        return photo;
    }
}
