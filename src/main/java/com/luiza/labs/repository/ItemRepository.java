package com.luiza.labs.repository;

import com.luiza.labs.domain.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    List<Item> findByOrderId(Long orderId);

    @Query("{'orderDate': {$gte: ?0, $lte: ?1}}")
    List<Item> findByOrderDateBetween(LocalDate orderDateStart, LocalDate orderDateEnd);
}