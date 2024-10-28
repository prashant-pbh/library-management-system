package com.library.service.impl;

import com.library.model.Inventory;
import com.library.model.OrderDetail;
import com.library.model.Student;
import com.library.model.Transaction;
import com.library.repository.InventoryRepository;
import com.library.repository.OrderRepository;
import com.library.repository.StudentRepository;
import com.library.repository.TransactionRepository;
import com.library.service.LibraryService;
import com.library.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
    public void removeBookData() {

    }

    @Override
    public void renewBookData() {

    }

    @Override
    public void getStudentDetailsData() {

    }
}
