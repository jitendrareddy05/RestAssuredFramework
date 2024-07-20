Feature: Validating Login API

Scenario: Verify if user is able to login to the application using LoginAPI's
Given Login payload with "<userEmail>" "<userPassword>"
When user calls "Login" with "Post" http request
Then the API call will be success with status code 200
And get the token value from the response body
And get the userId value from the response body
And message in the response body is "Login Successfully"

Examples:
|userEmail								| userPassword   |
|jitendra.ts@gmail.com		| Password@123	 |
|jitendrareddy05@gmail.com| Password@111	 |

