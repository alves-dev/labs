package com.luiza.labs.service;

import com.luiza.labs.domain.Item;
import com.luiza.labs.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveAll(Iterable<Item> items){
        itemRepository.saveAll(items);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public List<Item> findByOrderId(Long orderId){
        return itemRepository.findByOrderId(orderId);
    }
}
