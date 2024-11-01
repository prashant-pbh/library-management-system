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
    private String bookRenewalDate;
    private String bookReturnDueDate;
    private boolean isBookReturned;
    private String bookReturnDate;

    public OrderDetail() {
    }

    public OrderDetail(int orderId, int bookId, String bookIssueDate, String bookRenewalDate, String bookReturnDueDate) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.bookIssueDate = bookIssueDate;
        this.bookRenewalDate = bookRenewalDate;
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

    public String getBookRenewalDate() {
        return bookRenewalDate;
    }

    public void setBookRenewalDate(String bookRenewalDate) {
        this.bookRenewalDate = bookRenewalDate;
    }

    public String getBookReturnDueDate() {
        return bookReturnDueDate;
    }

    public void setBookReturnDueDate(String bookReturnDueDate) {
        this.bookReturnDueDate = bookReturnDueDate;
    }

    public boolean isBookReturned() {
        return isBookReturned;
    }

    public void setBookReturned(boolean bookReturned) {
        isBookReturned = bookReturned;
    }

    public String getBookReturnDate() {
        return bookReturnDate;
    }

    public void setBookReturnDate(String bookReturnDate) {
        this.bookReturnDate = bookReturnDate;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId=" + orderId +
                ", bookId=" + bookId +
                ", bookIssueDate='" + bookIssueDate + '\'' +
                ", bookRenewalDate='" + bookRenewalDate + '\'' +
                ", bookReturnDueDate='" + bookReturnDueDate + '\'' +
                ", isBookReturned=" + isBookReturned +
                ", bookReturnDate='" + bookReturnDate + '\'' +
                '}';
    }
}
