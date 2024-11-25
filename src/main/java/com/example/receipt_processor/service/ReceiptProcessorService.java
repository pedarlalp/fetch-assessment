package com.example.receipt_processor.service;

import com.example.receipt_processor.dao.ReceiptsDao;
import com.example.receipt_processor.model.Item;
import com.example.receipt_processor.model.Receipt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReceiptProcessorService {

    public int calculatePoints(Receipt receipt) {
        int points = 0;

        // Rule 1: Points for every alphanumeric character in retailer name
        points += retailerNamePoints(receipt.getRetailer());

        // Rule 2: 50 points if total is a round dollar amount
        points += roundDollarPoints(receipt.getTotal());

        // Rule 3: 25 points if total is a multiple of 0.25
        points += multipleOfQuarterPoints(receipt.getTotal());

        // Rule 4: 5 points for every two items
        points += itemsPoints(receipt.getItems().size());

        // Rule 5: Points based on item description length
        points += itemDescriptionPoints(receipt.getItems());

        // Rule 6: 6 points if day of purchase date is odd
        points += oddDayPoints(receipt.getPurchaseDate());

        // Rule 7: 10 points if purchase time is between 2:00pm and 4:00pm
        points += purchaseTimePoints(receipt.getPurchaseTime());

        return points;
    }

    // Rule 1: Points for every alphanumeric character in retailer name
    private int retailerNamePoints(String retailerName) {
        if (retailerName == null) return 0;
        return (int) retailerName.chars().filter(Character::isLetterOrDigit).count();
    }

    // Rule 2: 50 points if total is a round dollar amount (no cents)
    private int roundDollarPoints(String total) {
        try {
            BigDecimal totalAmount = new BigDecimal(total);
            if (totalAmount.stripTrailingZeros().scale() <= 0) {
                return 50;
            }
        } catch (NumberFormatException e) {

        }
        return 0;
    }

    // Rule 3: 25 points if total is a multiple of 0.25
    private int multipleOfQuarterPoints(String total) {
        try {
            BigDecimal totalAmount = new BigDecimal(total);
            if (totalAmount.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0) {
                return 25;
            }
        } catch (NumberFormatException e) {
        }
        return 0;
    }

    // Rule 4: 5 points for every two items on the receipt
    private int itemsPoints(int itemCount) {
        return (itemCount / 2) * 5;
    }

    // Rule 5: Points based on item description length
    private int itemDescriptionPoints(List<Item> items) {
        int points = 0;
        for (Item item : items) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                BigDecimal price = new BigDecimal(item.getPrice());
                points += price.multiply(new BigDecimal("0.2")).setScale(0, RoundingMode.UP).intValue();
            }
        }
        return points;
    }

    // Rule 6: 6 points if the day in the purchase date is odd
    private int oddDayPoints(String purchaseDate) {
        try {
            LocalDate date = LocalDate.parse(purchaseDate, DateTimeFormatter.ISO_DATE);
            if (date.getDayOfMonth() % 2 != 0) {
                return 6;
            }
        } catch (Exception e) {
        }
        return 0;
    }

    // Rule 7: 10 points if the time of purchase is between 2:00pm and 4:00pm
    private int purchaseTimePoints(String purchaseTime) {
        try {
            LocalTime time = LocalTime.parse(purchaseTime);
            if (time.isAfter(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(16, 0))) {
                System.out.println("purchaseTimePoints ");
                return 10;
            }
        } catch (Exception e) {
        }
        return 0;
    }
}
