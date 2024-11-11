package com.fetch.receiptprocesser;

import com.fetch.receiptprocesser.models.Item;
import com.fetch.receiptprocesser.models.ReceiptProcessRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class ReceiptService {


    public int ProcessReceipt(ReceiptProcessRequest request){
        var totalPoints = 0;
        totalPoints += RetailerNamePoints(request.retailer);
        totalPoints += RoundDollar(request.total);
        totalPoints += QuaterDollar(request.total);
        totalPoints += NumberOfItems(request.items);
        totalPoints += DayPurchase(request.purchaseDate);
        totalPoints += TimePurchase(request.purchaseTime);
        for(var item : request.items){
            totalPoints += ItemPoints(item);
        }
        return totalPoints;
    }

    // One point for every alphanumeric character in the retailer name.
    public int RetailerNamePoints(String retailerName){
        int points = 0;

        for (char c : retailerName.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                points++;
            }
        }

        return points;
    }

    // 50 points if the total is a round dollar amount with no cents.
    public int RoundDollar(String totalAmount){
        if (totalAmount.endsWith(".00")) {
            return 50;
        }
        return 0;
    }

    // 25 points if the total is a multiple of 0.25.
    public int QuaterDollar(String totalPrice){
        double amount = Double.parseDouble(totalPrice);
        if (amount % 0.25 == 0) {
            return 25;
        }
        return 0;
    }

    //5 points for every two items on the receipt.
    public int NumberOfItems(Item[] items){
        int itemCount = items.length;
        int pairs = itemCount / 2;
        return pairs * 5;
    }

    //6 points if the day in the purchase date is odd.
    public int DayPurchase(String purchaseDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(purchaseDate, formatter);
        int dayOfMonth = date.getDayOfMonth();
        if (dayOfMonth % 2 != 0) {
            return 6;
        }
        return 0;
    }

    //    If the trimmed length of the item description is a multiple of 3,
//    multiply the price by 0.2 and round up to the nearest integer.
//    The result is the number of points earned.
    public int ItemPoints(Item item){
        int length = item.shortDescription.trim().length();

        if (length % 3 == 0) {
            BigDecimal priceValue = new BigDecimal(item.price);
            BigDecimal points = priceValue.multiply(new BigDecimal("0.2"));
            return points.setScale(0, RoundingMode.UP).intValue();
        }
        return 0;
    }

    // 10 points if the time of purchase is after 2:00pm and before 4:00pm.
    public int TimePurchase(String purchaseTime){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(purchaseTime, timeFormatter);

        LocalTime start = LocalTime.of(14, 0);
        LocalTime end = LocalTime.of(16, 0);

        if (time.isAfter(start) && time.isBefore(end)) {
            return 10;
        }
        return 0;
    }

















}
