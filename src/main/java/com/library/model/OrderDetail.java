package com.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OrderDetail
{
    @Id
    private int orderId;
    private int bookId;
    private String bookIssueDate;
    private String bookRenewalDay;
    private String bookReturnDueDate;

    public OrderDetail() {
    }

    public OrderDetail(int orderId, int bookId, String bookIssueDate, String bookRenewalDay, String bookReturnDueDate) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.bookIssueDate = bookIssueDate;
        this.bookRenewalDay = bookRenewalDay;
        this.bookReturnDueDate = bookReturnDueDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookIssueDate() {
        return bookIssueDate;
    }

    public void setBookIssueDate(String bookIssueDate) {
        this.bookIssueDate = bookIssueDate;
    }

    public String getBookRenewalDay() {
        return bookRenewalDay;
    }

    public void setBookRenewalDay(String bookRenewalDay) {
        this.bookRenewalDay = bookRenewalDay;
    }

    public String getBookReturnDueDate() {
        return bookReturnDueDate;
    }

    public void setBookReturnDueDate(String bookReturnDueDate) {
        this.bookReturnDueDate = bookReturnDueDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", bookId=" + bookId +
                ", bookIssueDate='" + bookIssueDate + '\'' +
                ", bookRenewalDay='" + bookRenewalDay + '\'' +
                ", bookReturnDueDate='" + bookReturnDueDate + '\'' +
                '}';
    }
}
