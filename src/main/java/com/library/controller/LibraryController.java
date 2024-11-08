package com.library.controller;

import com.library.model.Book;
import com.library.model.Student;
import com.library.model.StudentDetailDTO;
import com.library.model.request.InputRequest;
import com.library.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class LibraryController
{
    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    LibraryService libraryService;

    @PostMapping("/allocateBook/{bookid}/{rollno}")
    public String allocateBook(@PathVariable("bookid") int bookId, @PathVariable("rollno") int rollNo)
    {
        logger.info("Received request to allocate book with bookId={} to student rollNo={}", bookId, rollNo);
        boolean res = libraryService.allocateBook(bookId,rollNo);
        return res ? "Book allocated successfully."  : "Book not allocated.";
    }

    @PostMapping("/submitBook/{orderId}/{rollNo}")
    public String submitBook(@PathVariable("orderId") int orderId, @PathVariable("rollNo") int rollNo)
    {
        logger.info("Request received to submit book with orderId={} for student rollNo={}", orderId, rollNo);
        boolean res = libraryService.submitBookData(rollNo, orderId);
        if(res)
        {
            logger.info("Book returned successfully for orderId={} and rollNo={}", orderId, rollNo);
            return "Book returned successfully";
        }
        else {
            logger.error("Book return failed for orderId={} and rollNo={}", orderId, rollNo);
            return "Book not returned successfully";
        }
    }

    @PostMapping("/renewBook/{orderId}/{rollNo}")
    public String renewBook(@PathVariable("orderId") int orderId,@PathVariable("rollNo") int rollNo)
    {
        logger.info("Request received to renew book with orderId={} for student rollNo={}", orderId, rollNo);
        libraryService.renewBookData(orderId,rollNo);
        logger.info("Book return date renewed successfully for orderId={} and rollNo={}", orderId, rollNo);
        return "Book return date renew successfully.";
    }

    @GetMapping("/getStudentDetails/{rollNo}")
    public Student fetchStudentData(@PathVariable("rollNo") int rollNo)
    {
        logger.info("Fetching student details for rollNo={}", rollNo);
        return new Student();
    }

    @GetMapping("/sendReminder")
    public String sendReminder()
    {
        logger.info("Request received to send reminder");
        boolean res = libraryService.sendReminder();
        if(res)
        {
            logger.info("Reminder sent successfully");
            return "Reminder sent";
        }
        else {
            logger.warn("Failed to send reminder");
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
        logger.info("Retrieving student details for rollNo={}", rollNo);
        return libraryService.getStudentDetail(rollNo);
    }

}
