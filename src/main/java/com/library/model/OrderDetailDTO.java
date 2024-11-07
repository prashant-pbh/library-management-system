package com.library.model;


public class OrderDetailDTO
{
    private int orderId;
    private int bookId;
    private String bookName;
    private String author;
    private String price;
    private String bookIssueDate;
    private String bookRenewalDate;
    private String bookReturnDueDate;
    private boolean isBookReturned;
    private String bookReturnDate;

    public OrderDetailDTO()
    {}

    public OrderDetailDTO(int orderId, int bookId, String bookName, String author, String price, String bookIssueDate, String bookRenewalDate, String bookReturnDueDate, boolean isBookReturned, String bookReturnDate) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.price = price;
        this.bookIssueDate = bookIssueDate;
        this.bookRenewalDate = bookRenewalDate;
        this.bookReturnDueDate = bookReturnDueDate;
        this.isBookReturned = isBookReturned;
        this.bookReturnDate = bookReturnDate;
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
        return "OrderDetailDTO{" +
                "orderId=" + orderId +
                ", bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", price='" + price + '\'' +
                ", bookIssueDate='" + bookIssueDate + '\'' +
                ", bookRenewalDate='" + bookRenewalDate + '\'' +
                ", bookReturnDueDate='" + bookReturnDueDate + '\'' +
                ", isBookReturned=" + isBookReturned +
                ", bookReturnDate='" + bookReturnDate + '\'' +
                '}';
    }
}
