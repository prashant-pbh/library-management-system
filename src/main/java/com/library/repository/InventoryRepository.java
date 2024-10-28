package com.library.repository;

import com.library.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Integer>
{
//    Optional<Inventory> findById(int id);

//    void updateInventry();
//    Inventory bookAvailableInInventory(int bookId);
}
