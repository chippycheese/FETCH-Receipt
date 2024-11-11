package com.fetch.receiptprocesser;

import com.fetch.receiptprocesser.models.ReceiptFindResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerErrorException;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class DatabaseService {

    public Dictionary<String, Integer> Database;

    private DatabaseService(){
        Database = new Hashtable<>();
    }

    public int findInDatabase(String uuid){
        Integer result = Database.get(uuid);
        if (result == null) {
            throw new NoSuchElementException("404 Not Found: Item with UUID " + uuid + " not found.");
        }
        return result;
    }

    public String saveInDatabase(int points){
        var uuid = UUID.randomUUID().toString();
        Database.put(uuid, points);
        return uuid;
    }
}
