package com.fetch.receiptprocesser;

import com.fetch.receiptprocesser.models.ReceiptProcessRequest;
import com.fetch.receiptprocesser.models.ReceiptFindResponse;
import com.fetch.receiptprocesser.models.ReceiptProcessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private DatabaseService databaseService;


    @PostMapping("/process")
    public ReceiptProcessResponse processRequest(@RequestBody ReceiptProcessRequest request) {
        var points = receiptService.ProcessReceipt(request);
        var uuid = databaseService.saveInDatabase(points);
        return new ReceiptProcessResponse(uuid);
    }

    @GetMapping("/{id}/points")
    public ReceiptFindResponse findRequest(@PathVariable String id) {
        try {
            return new ReceiptFindResponse(databaseService.findInDatabase(id));
        } catch (NoSuchElementException e) {
            // Handle not found error, and return 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receipt with ID " + id + " not found", e);
        }
    }
}
