package com.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Transaction
{
    @Id
    int orderId;
    int studentRollNo;
    String createdTime;


    public Transaction() {
    }

    public Transaction(int studentRollNo, int orderId) {
        this.studentRollNo = studentRollNo;
        this.orderId = orderId;
    }

    public int getStudentRollNo() {
        return studentRollNo;
    }

    public void setStudentRollNo(int studentRollNo) {
        this.studentRollNo = studentRollNo;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "studentRollNo=" + studentRollNo +
                ", orderId=" + orderId +
                '}';
    }
}
