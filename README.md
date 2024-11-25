Steps to run the project:

clone the project to your local git clone https://github.com/pedarlalp/fetch-api.git
build docker image docker build -t receipt-processor .
create a container from docker image docker run -p 8080:8080 --name receipt-processor-api receipt-processor
you can test the following endpoints on localhost:8080 post endpoint: http://localhost:8080/receipts/process get endpoint: http://localhost:8080/receipts/{id}/points

Examples for test data (post request body) can be found (here)[https://github.com/fetch-rewards/receipt-processor-challenge/tree/main/examples] 