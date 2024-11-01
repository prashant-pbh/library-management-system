package com.library.controller;

import com.library.model.Student;
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

}
