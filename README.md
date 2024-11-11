1. Java 8 may be a require install on box
2. docker build -t springboot-api .
3. docker run -p 8080:8080 springboot-api

Endpoints

Endpoint: Process Receipts
* Path: /receipts/process
* Method: POST
* Payload: Receipt JSON
* Response: JSON containing an id for the receipt.

Endpoint: Find Receipt
* Path: /receipts/{id}/points
* Method: GET
* Response: A JSON object containing the number of points awarded.