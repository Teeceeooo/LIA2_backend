package com.lia2.lia2_backend.controller;

import com.lia2.lia2_backend.entity.Item;
import com.lia2.lia2_backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
@CrossOrigin("*")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable int id) {
        Item item = itemService.getItemById(id);

        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }
    @PostMapping("/create")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        try {
            Item newItem = itemService.createItem(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItemById(@PathVariable int id) {
        try {
            itemService.deleteItemById(id);
            return ResponseEntity.ok("Pryl med " + id + " raderad.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ett fel uppstod.");
        }
    }
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllItems() {
        try {
            itemService.deleteAllItems();
            return ResponseEntity.ok("Alla prylar har raderas.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ett fel uppstod att radera alla prylar.");
        }
    }
}
