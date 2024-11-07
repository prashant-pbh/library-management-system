package com.library.model;

import java.util.List;

public class StudentDetailDTO
{
    private int rollNo;
    private String studentName;
    private String email;
    private List<OrderDetailDTO> orderDetailDTOList;

    public StudentDetailDTO()
    {}

    public StudentDetailDTO(int rollNo, String studentName, String email, List<OrderDetailDTO> orderDetailDTOList) {
        this.rollNo = rollNo;
        this.studentName = studentName;
        this.email = email;
        this.orderDetailDTOList = orderDetailDTOList;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderDetailDTO> getOrderDetailDTOList() {
        return orderDetailDTOList;
    }

    public void setOrderDetailDTOList(List<OrderDetailDTO> orderDetailDTOList) {
        this.orderDetailDTOList = orderDetailDTOList;
    }

    @Override
    public String toString() {
        return "StudentDetailDTO{" +
                "rollNo=" + rollNo +
                ", studentName='" + studentName + '\'' +
                ", email='" + email + '\'' +
                ", orderDetailDTOList=" + orderDetailDTOList +
                '}';
    }
}
