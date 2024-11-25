package com.example.receipt_processor.dao;

import com.example.receipt_processor.model.Receipt;
import com.example.receipt_processor.service.ReceiptProcessorService;

import java.util.HashMap;
import java.util.Map;

public class ReceiptsDao {
    private Map<String, Receipt> receiptMap = new HashMap<>();

    public void put(String id, Receipt receipt){
        receiptMap.put(id, receipt);
    }

    public int getPointsForReceipt(String id){
        ReceiptProcessorService service = new ReceiptProcessorService();
        if(receiptMap.containsKey(id))
            return  service.calculatePoints(receiptMap.get(id));
        else
            return -1;
    }
}
