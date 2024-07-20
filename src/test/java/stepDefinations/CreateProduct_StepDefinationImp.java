package stepDefinations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.AddProductResponse;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class CreateProduct_StepDefinationImp extends Utils {
	TestDataBuild data = new TestDataBuild();
	RequestSpecification createProductRequest;
	Response sendRequest;
	Response response;
	AddProductResponse addProductPojoResponse;

	@Given("createProduct Payload with {string}")
	public void create_product_payload_with(String string) throws IOException {
		String token = Login_StepDefinationImp.get_the_token_value_from_the_response_body();

		Map<String, String> product = data.createProductPayLoad();
		for (Map.Entry<String, String> entry : product.entrySet()) {
			createProductRequest = given().spec(requestSpecificationWithHeaderToken(token))
					.param(entry.getKey(), entry.getValue()).multiPart("productImage",
							new File(System.getProperty("user.dir") + "\\images\\screenshot (38).jpg"));
		}
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String apiEndPoint, String requestType) {
		APIResources resouceAPI = APIResources.valueOf(apiEndPoint);

		if (requestType.equalsIgnoreCase("POST"))
			sendRequest = createProductRequest.when().post(resouceAPI.getEndPoint());
		else
			System.out.println("Invalid request type");
	}

	@Then("the API call will be success with status code {int}")
	public void the_api_call_will_be_success_with_status_code(int statusCode) {
		response = sendRequest.then().spec(responseSpecification(statusCode)).extract().response();
		assertEquals(sendRequest.getStatusCode(), statusCode);
	}

	@And("get the productId value from the response body")
	public String get_the_product_id_value_from_the_response_body() {
		addProductPojoResponse = response.as(AddProductResponse.class);
		return addProductPojoResponse.getProductId();
	}
	
	@And("message in the response body is {string}")
	public void in_the_response_body_is(String expectedValue) {
		String actualValue = addProductPojoResponse.getMessage();
		assertEquals(actualValue, expectedValue);
	}
}
