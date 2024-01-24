package com.lia2.lia2_backend.service;

import com.lia2.lia2_backend.entity.Item;
import com.lia2.lia2_backend.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item getItemById(int id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pryl med ID " + id + " finns inte."));
    }
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    public Item createItem(Item item) {
        try {
            return itemRepository.save(item);
        } catch (Exception ex) {
            throw new RuntimeException("Ett fel uppstod:  " + ex.getMessage());
        }
    }
    public void deleteItemById(int id) {
        try {
            itemRepository.deleteById(id);
        } catch (Exception ex) {
            throw new RuntimeException("Ett fel uppstod med ID : " + id, ex);
        }
    }
    public void deleteAllItems() {
        try {
            itemRepository.deleteAll();
        } catch (Exception ex) {
            throw new RuntimeException("Ett fell uppstod med att radera alla prylar", ex);
        }
    }


}
