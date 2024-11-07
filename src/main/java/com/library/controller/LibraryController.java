package com.library.controller;

import com.library.model.Book;
import com.library.model.Student;
import com.library.model.StudentDetailDTO;
import com.library.model.request.InputRequest;
import com.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class LibraryController
{
    @Autowired
    LibraryService libraryService;

    @PostMapping("/allocateBook/{bookid}/{rollno}")
    public String allocateBook(@PathVariable("bookid") int bookId, @PathVariable("rollno") int rollNo)
    {
        boolean res = libraryService.allocateBook(bookId,rollNo);
        return res ? "Book allocated successfully."  : "Book not allocated.";
    }

    @PostMapping("/submitBook/{orderId}/{rollNo}")
    public String submitBook(@PathVariable("orderId") int orderId, @PathVariable("rollNo") int rollNo)
    {
//        logger.info("Request received to submit book with rollNo {} and orderId {}", rollNo,orderId);
        boolean res = libraryService.submitBookData(rollNo, orderId);
        if(res)
        {
            return "Book returned successfully";
        }
        else {
            return "Book not returned successfully";
        }
    }

    @PostMapping("/renewBook/{bookId}/{rollNo}")
    public void renewBook(@PathVariable("bookId") int bookId,@PathVariable("rollNo") int rollNo)
    {

    }

    @GetMapping("/getStudentDetails/{rollNo}")
    public Student fetchStudentData(@PathVariable("bookId") int bookId)
    {
        return new Student();
    }

    @GetMapping("/sendReminder")
    public String sendReminder()
    {
        boolean res = libraryService.sendReminder();
        if(res)
        {
            return "Reminder sent";
        }
        else {
            return "Reminder not sent";
        }
    }

    @PostMapping("/addStudent")
    public String addStudent(@RequestBody Student student)
    {
        libraryService.addStudent(student);
        return "Student added successfully";
    }

    @PostMapping("/addBook")
    public String addBook(@RequestBody Book book)
    {
        libraryService.addBook(book);
        return "Book added successfully";
    }

    @PostMapping("/getStudentDetail/{rollNo}")
    public StudentDetailDTO getStudentDetail(@PathVariable("rollNo") int rollNo)
    {
        return libraryService.getStudentDetail(rollNo);
    }

}
