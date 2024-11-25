package com.example.receipt_processor.controller;

import com.example.receipt_processor.dao.ReceiptsDao;
import com.example.receipt_processor.service.ReceiptProcessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.receipt_processor.model.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    ReceiptsDao receiptsDao = new ReceiptsDao();
    // POST: Add a new user
    @PostMapping("/process")
    public ResponseEntity<?> processReceipt(@Valid @RequestBody Receipt receipt, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return new ResponseEntity<>("invalid receipt", HttpStatus.BAD_REQUEST);
            }
            String receiptId = UUID.randomUUID().toString();
            receiptsDao.put(receiptId, receipt);
            // Return the receipt ID
            Map<String, String> response = new HashMap<>();
            response.put("id", receiptId);
            return ResponseEntity.ok(response);
    }

    // GET: Fetch a user by ID
    @GetMapping("/{id}/points")
    public ResponseEntity<?> getPointsById(@PathVariable String id) {
            ReceiptProcessorService rps = new ReceiptProcessorService();
            int points = receiptsDao.getPointsForReceipt(id);
            if(points<0)
                return new ResponseEntity<>("No receipt found for that id", HttpStatus.NOT_FOUND);
            Map<String, Integer> response = new HashMap<>();
            response.put("points", points);
            return ResponseEntity.ok(response);
    }
}
