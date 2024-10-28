package com.library.service;

public interface LibraryService
{
    boolean allocateBook(int booId, int rollNo);
    void removeBookData();
    void renewBookData();
    void getStudentDetailsData();
}
