Short demo application for tool rental service. In lieu of a database backend, the tools were stored in an enum. In a real application, these would be pulled from a database into hibernate entities. 

I chose to have the response body return a nicely formatted String of the rental agreement, simply for demo purposes. In a real application, we would return a JSON object, and have the front end format it properly.

There are three json files: `testRequest.json`, `testRequest_badDiscountPercent.json`, and `testRequest_badRentalDays.json` that can be used with a curl request to test the controller.
The latter two files have input errors that should return an error.

The curl request should look something like this:
```
curl -H "Content-Type: application/json" -d @testRequest.json http://localhost:8080/rental
```