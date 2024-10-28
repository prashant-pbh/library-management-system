package com.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
public class Student
{
    @Id
    private int rollNo;
    private String name;
    private String mobileNo;
    private String address;
    private String standard;

    @Override
    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", address='" + address + '\'' +
                ", standard='" + standard + '\'' +
                '}';
    }

    public Student()
    {}

    public Student(int rollNo, String name, String mobileNo, String address, String standard) {
        this.rollNo = rollNo;
        this.name = name;
        this.mobileNo = mobileNo;
        this.address = address;
        this.standard = standard;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
