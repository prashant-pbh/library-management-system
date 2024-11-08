package com.library.service.impl;

import com.library.model.*;
import com.library.repository.*;
import com.library.service.LibraryService;
import com.library.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.util.ByteArrayDataSource;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService
{
    @Autowired
    Utility utility;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    BookRepository bookRepository;

    @Override
    public boolean allocateBook(int bookId, int rollNo)
    {
        if(isBookAvailable(bookId) && isStudentExist(rollNo))
        {
            try {
//          saving order in DB
                Instant now = Instant.now();
                String bookReturnDueDate = now.plus(15, ChronoUnit.DAYS).toString();
                OrderDetail orderDetail = new OrderDetail(utility.generateOrderId(),bookId,Instant.now().toString(),null,bookReturnDueDate);
                orderRepository.save(orderDetail);

//          saving transaction detail in db
                Transaction transaction = new Transaction(rollNo, orderDetail.getOrderId(),Instant.now().toString());
                transactionRepository.save(transaction);
//
//          updating book quantity in inventory db
                Optional<Inventory> optionalInventory = inventoryRepository.findById(bookId);
                Inventory inventory = optionalInventory.isPresent() ? optionalInventory.get() : null;
                int bookQuantity = inventory.getQuantity();
                inventory.setQuantity(bookQuantity-1);
                inventoryRepository.save(inventory);


                Optional<Student> optionalStudent = studentRepository.findById(rollNo);
                Student student = optionalStudent.isEmpty() ? null : optionalStudent.get();
                String email = student.getEmail();
                sendAllocationBookEmail(student.getName(),email,bookId,bookReturnDueDate,orderDetail.getBookIssueDate());
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private boolean isBookAvailable(int bookId){
        boolean isBookAvailable = false;
        Optional<Inventory> optionalInventory = inventoryRepository.findById(bookId);
        if(optionalInventory.isPresent()){
            isBookAvailable = optionalInventory.get().getQuantity() > 0;
        }
        return isBookAvailable;
    }

    private boolean isStudentExist(int rollNo){
        boolean isStudentExists = false;
        //TODO: write logic here
        Optional<Student> optionalStudent = studentRepository.findById(rollNo);
        if(optionalStudent.isPresent()){
            isStudentExists = true;
        }
        return isStudentExists;
    }

    @Override
    public boolean submitBookData(int rollNo, int orderId)
    {
        if(isBookOrder(orderId) && isStudentExist(rollNo))
        {
            try {
                Optional<OrderDetail> optionalOrderDetail = orderRepository.findById(orderId);
                OrderDetail orderDetail = optionalOrderDetail.orElse(null);
                if(orderDetail == null)
                {
                    return false;
                }
                orderDetail.setBookReturned(true);
                orderDetail.setBookReturnDate(Instant.now().toString());

                Optional<Inventory> optionalInventory = inventoryRepository.findById(orderDetail.getBookId());
                Inventory inventory = optionalInventory.isPresent() ? optionalInventory.get() : null;
                int bookQuantity = inventory.getQuantity();
                inventory.setQuantity(bookQuantity+1);
                inventoryRepository.save(inventory);
            }catch (Exception e)
            {
                System.out.println(e);
                return false;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    private  boolean isBookOrder(int orderId)
    {
        boolean isBookOrdered = true;
        Optional<OrderDetail> optionalOrderDetail = orderRepository.findById(orderId);
        if (optionalOrderDetail.isEmpty())
        {
            return false;
        }
        return isBookOrdered;
    }

    private boolean isBookIdExist(int orderId)
    {
        boolean isBookIdExist = true;
        Optional<OrderDetail> optionalBook = orderRepository.findById(orderId);
        if (optionalBook.isEmpty())
        {
            return false;
        }
        return isBookIdExist;
    }



    @Override
    public void renewBookData(int orderId, int rollNum)
    {
        if(isBookIdExist(orderId) && isStudentExist(rollNum))
        {
            Optional<OrderDetail> optionalOrderDetail = orderRepository.findById(orderId);
            OrderDetail orderDetail = optionalOrderDetail.isEmpty() ? null : optionalOrderDetail.get();
            int bookId = orderDetail.getBookId();
            String returnDueDate = orderDetail.getBookReturnDueDate();
            String bookIssueDate = orderDetail.getBookIssueDate();
            Instant dueDateInstant = Instant.parse(returnDueDate);
            String renewDueDate = dueDateInstant.plus(15, ChronoUnit.DAYS).toString();
            OrderDetail newOrderDetail = new OrderDetail(orderId,bookId,bookIssueDate,null,renewDueDate);
            orderRepository.save(newOrderDetail);
        }
    }

    @Override
    public void getStudentDetailsData() {

    }

    @Override
    public boolean sendReminder()
    {
        List<OrderDetail> orderDetails = orderRepository.findAll();
        for (OrderDetail orderDetail: orderDetails)
        {
            if (isOrderRenewalDateWithinThreeDays(orderDetail.getBookReturnDueDate()))
            {
                Optional<Transaction> optionalTransaction = transactionRepository.findById(orderDetail.getOrderId());
                Transaction transaction = optionalTransaction.orElse(null);
                int rollNo = transaction.getStudentRollNo();
                Optional<Student> optionalStudent = studentRepository.findById(rollNo);
                Student student = optionalStudent.isEmpty() ? null : optionalStudent.get();
                String email = student.getEmail();
                sendReminderEmail(student.getName(), email,orderDetail.getBookId(),orderDetail.getBookReturnDueDate());
            }
        }
        return true;
    }

    @Override
    public void addBook(Book books)
    {
        bookRepository.save(books);
    }

    @Override
    public void addStudent(Student students)
    {
        studentRepository.save(students);
    }

    @Override
    public StudentDetailDTO getStudentDetail(int rollNo)
    {
        StudentDetailDTO studentDetailDTO = new StudentDetailDTO();
        studentDetailDTO.setRollNo(rollNo);

        Optional<Student> optionalStudent = studentRepository.findById(rollNo);
        Student student = optionalStudent.isPresent() ? optionalStudent.get() : null;
        String name = student.getName();
        String email = student.getEmail();
        studentDetailDTO.setStudentName(name);
        studentDetailDTO.setEmail(email);

        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findByRollNo(rollNo);
        for (Transaction transaction: transactions)
        {
            OrderDetailDTO orderDetailDTO = getOrderDetailDTO(transaction.getOrderId());
            orderDetailDTOList.add(orderDetailDTO);
        }

        studentDetailDTO.setOrderDetailDTOList(orderDetailDTOList);

        return studentDetailDTO;
    }

    private OrderDetailDTO getOrderDetailDTO(int orderId)
    {
//        TODO: write the logic here.
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrderId(orderId);
        Optional<OrderDetail> optionalOrderDetail = orderRepository.findById(orderId);
        OrderDetail orderDetail = optionalOrderDetail.isPresent() ? optionalOrderDetail.get() : null;
        int bookId = orderDetail.getBookId();
        String bookIssueDate = orderDetail.getBookIssueDate();
        String bookRenewalDate = orderDetail.getBookIssueDate();
        String bookReturnDueDate = orderDetail.getBookReturnDueDate();
        boolean isBookReturned = orderDetail.isBookReturned();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.isPresent() ? optionalBook.get() : null;
        String booName = book.getName();
        String author  = book.getAuthor();
        String price = book.getPrice();

        orderDetailDTO.setBookId(bookId);
        orderDetailDTO.setBookName(booName);
        orderDetailDTO.setAuthor(author);
        orderDetailDTO.setPrice(price);
        orderDetailDTO.setBookIssueDate(bookIssueDate);
        orderDetailDTO.setBookRenewalDate(bookRenewalDate);
        orderDetailDTO.setBookReturnDueDate(bookReturnDueDate);
        orderDetailDTO.setBookReturned(isBookReturned);

        return orderDetailDTO;
    }

    private boolean isOrderRenewalDateWithinThreeDays(String dueDate)
    {
        Instant dueDateTimeStamp = Instant.parse(dueDate);
        Duration duration = Duration.between(dueDateTimeStamp,Instant.now());
        long daysDifference = duration.toDays();
        if(daysDifference < 3)
        {
            return true;
        }
        else {
            return  false;
        }
    }

    private void sendReminderEmail(String name, String email, int bookId, String dueDate)
    {
        final String subject = "Reminder: Please return the book before due date.";
        final String body = "<html>" +
                "<body style='font-family: Arial, sans-serif; color: #333;'>" +
                "<div style='max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd;'>" +
                "<h2 style='color: #d9534f;'>Book Return Reminder</h2>" +
                "<p>Dear " + name + ",</p>" +
                "<p>This is a friendly reminder to return the allocated book by the due date to avoid any penalties.</p>" +
                "<table style='width: 100%; border-collapse: collapse; margin-top: 10px;'>" +
                "<tr style='background-color: #f9f9f9;'>" +
                "<td style='padding: 10px; border: 1px solid #ddd;'>Book ID</td>" +
                "<td style='padding: 10px; border: 1px solid #ddd;'>" + bookId + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 10px; border: 1px solid #ddd;'>Due Date</td>" +
                "<td style='padding: 10px; border: 1px solid #ddd;'>" + utility.getISTFormattedDateAndTime(dueDate) + "</td>" +
                "</tr>" +
                "</table>" +
                "<p style='margin-top: 20px;'>Please ensure you return it on or before the due date to avoid any penalties.</p>" +
                "<p>Thank you,</p>" +
                "<p><strong>Library Management</strong></p>" +
                "</div>" +
                "</body>" +
                "</html>";
            emailService.sendEmail(email, subject, body);

    }

    private void sendAllocationBookEmail(String name, String email, int bookId, String dueDate, String bookIssueDate)
    {
        final String subject = "Book Allocated: your book has been allocated";
        final  String body = "<html><body style='font-family: Arial, sans-serif; color: #333;'><h3>Dear "+name+",\n\tThis is informed to you that following book (ID: "+bookId+") allocated to you on "+utility.getISTFormattedDateAndTime(bookIssueDate)+".</h3><p>Please ensure to return it on or before "+utility.getISTFormattedDateAndTime(dueDate)+" to avoid any penalties.</p><p>Thank you,\nLibrary Management</p></body></html>";
        final String pdfHtmlContent = "<html><h2>Receipt</h2><p>Dear "+name+", you have ordered following book (ID: "+bookId+").</p></html>";
        ByteArrayDataSource byteArrayDataSource = utility.createPDF(pdfHtmlContent);
        emailService.sendEmail(email,subject,body,byteArrayDataSource);
    }


}
