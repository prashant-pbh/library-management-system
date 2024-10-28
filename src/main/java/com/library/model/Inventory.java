package com.library.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Inventory
{
    @Id
    private int bookId;
    private int quantity;

    public Inventory() {
    }

    public Inventory(int bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "bookId=" + bookId +
                ", quantity=" + quantity +
                '}';
    }
}
