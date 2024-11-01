package com.library.service;

public interface LibraryService
{
    boolean allocateBook(int bookId, int rollNo);
    public boolean submitBookData(int rollNum, int orderId);
    void renewBookData();
    void getStudentDetailsData();
    boolean sendReminder();
}
