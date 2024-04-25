package com.luiza.labs.service;

import com.luiza.labs.domain.Item;
import com.luiza.labs.repository.ItemRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveAll(Iterable<Item> items) {
        itemRepository.saveAll(items);
    }

    @Cacheable("items")
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Cacheable("items")
    public List<Item> findByOrderId(Long orderId) {
        return itemRepository.findByOrderId(orderId);
    }

    @Cacheable("items")
    public List<Item> findByOrderDateBetween(LocalDate start, LocalDate end) {
        return itemRepository.findByOrderDateBetween(start, end);
    }
}
