package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.IOException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.LoginResponse;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class Login_StepDefinationImp extends Utils {
	RequestSpecification loginRequest;
	Response sendRequest;
	TestDataBuild data = new TestDataBuild();
	static Response response;
	LoginResponse loginPojoResponse;
	static String token;

	@Given("Login payload with {string} {string}")
	public void login_payload_with(String userEmail, String userPassword) throws IOException {
		loginRequest = given().spec(requestSpecification()).body(data.loginPayload(userEmail, userPassword));
	}

	@When("user calls {string} with {string} http request to login")
	public void user_calls_with_http_request_to_login(String apiEndPoint, String requestType) {
		APIResources resouceAPI = APIResources.valueOf(apiEndPoint);
		if (requestType.equalsIgnoreCase("POST"))
			sendRequest = loginRequest.when().post(resouceAPI.getEndPoint());
		else
			System.out.println("Invalid request type");
	}

	@Then("the API call will be success with status code {int}")
	public void the_api_call_will_be_success_with_status_code(int statusCode) {
		response = sendRequest.then().log().all().spec(responseSpecification(statusCode)).extract().response();
		assertEquals(sendRequest.getStatusCode(), statusCode);
	}

	@And("get the token value from the response body")
	public String get_the_token_value_from_the_response_body() {

		loginPojoResponse = response.as(LoginResponse.class);
		token = loginPojoResponse.getToken();
		return token;
	}

	@And("get the userId value from the response body")
	public String get_the_uderId_value_from_the_response_body() {
		loginPojoResponse = response.as(LoginResponse.class);
		return loginPojoResponse.getUserId();
	}

	@And("login message in the response body is {string}")
	public void login_message_in_the_response_body_is(String expectedValue) {
		assertEquals(loginPojoResponse.getMessage(), expectedValue);
	}

}
