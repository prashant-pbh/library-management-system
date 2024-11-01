package com.library.service.impl;

import com.library.model.*;
import com.library.repository.InventoryRepository;
import com.library.repository.OrderRepository;
import com.library.repository.StudentRepository;
import com.library.repository.TransactionRepository;
import com.library.service.LibraryService;
import com.library.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
                Transaction transaction = new Transaction(rollNo, orderDetail.getOrderId());
                transactionRepository.save(transaction);
//
//          updating book quantity in inventory db
                Optional<Inventory> optionalInventory = inventoryRepository.findById(bookId);
                Inventory inventory = optionalInventory.isPresent() ? optionalInventory.get() : null;
                int bookQuantity = inventory.getQuantity();
                inventory.setQuantity(bookQuantity-1);
                inventoryRepository.save(inventory);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        else
        {
            return false;
        }
//        if(isBookAvailable(bookId)){
//            //TODO: write logic here
//
//            Instant now = Instant.now();
//            String bookReturnDueDate = now.plus(15, ChronoUnit.DAYS).toString();
//            Order order = new Order(1223,bookId, Instant.now().toString(),null,bookReturnDueDate);
//            // saving order in db
//            libraryRepository.addOrder(order);
//
//            // saving transaction in db
//            Transaction transaction = new Transaction(rollNo, order.getOrderId());
//            libraryRepository.addTransaction(transaction);
//
//
//
//            return true;
//        }else {
//            return false;
//        }
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


    @Override
    public void renewBookData() {

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
        final String body = "Dear "+name+",\n\tThis is a reminder to return the allocated book (ID: "+bookId+") by "+dueDate+".\nPlease ensure you return it on or before the due date to avoid any penalties.\nThank you,\nLibrary Management";
        emailService.sendEmail(email,subject,body);
    }
}
