package com.library.service;

import com.library.model.Book;
import com.library.model.Student;
import com.library.model.StudentDetailDTO;

import java.util.List;

public interface LibraryService
{
    boolean allocateBook(int bookId, int rollNo);
    public boolean submitBookData(int rollNum, int orderId);
    void renewBookData(int orderId, int rollNum);
    void getStudentDetailsData();
    boolean sendReminder();
    void addBook(Book books);
    void addStudent(Student students);
    StudentDetailDTO getStudentDetail(int rollNo);
}
