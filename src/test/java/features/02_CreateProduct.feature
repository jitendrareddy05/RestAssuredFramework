
Feature: Validating createProduct API
@CreateProduct
Scenario: Verify if the user is able to cerate a product using createProductAPI
Given createProduct Payload with "<token>"
When user calls "CreateProduct" with "Post" http request to create product
Then the API call will be success with status code 201 to create product
And get the productId value from the response body
And Create product message in the response body is "<Product Added Successfully>"
