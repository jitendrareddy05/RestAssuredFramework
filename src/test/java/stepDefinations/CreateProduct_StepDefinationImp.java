package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
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
	Login_StepDefinationImp login = new Login_StepDefinationImp();
	RequestSpecification formParamAdded;

	@Given("createProduct Payload with {string}")
	public void create_product_payload_with(String string) throws IOException {
		String token = login.get_the_token_value_from_the_response_body();
		createProductRequest = given().spec(requestSpecificationForFormData())
				.header("Content-Type", "multipart/form-data").header("Authorization", token);

		Map<String, String> product = data.createProductPayLoad();
		for (Map.Entry<String, String> entry : product.entrySet()) {

			formParamAdded = createProductRequest.formParam(entry.getKey(), entry.getValue());
		}
		formParamAdded.multiPart("productImage",
				new File(System.getProperty("user.dir") + "\\images\\screenshot (38).jpg"));
	}

	@When("user calls {string} with {string} http request to create product")
	public void user_calls_with_http_request_to_create_product(String apiEndPoint, String requestType) {

		APIResources resouceAPI = APIResources.valueOf(apiEndPoint);

		if (requestType.equalsIgnoreCase("POST"))
			sendRequest = formParamAdded.when().post(resouceAPI.getEndPoint());
		else
			System.out.println("Invalid request type");

	}

	@Then("the API call will be success with status code {int} to create product")
	public void the_api_call_will_be_success_with_status_code_to_create_product(int statusCode) {
		
		response = sendRequest.then().log().all().spec(responseSpecification(statusCode)).extract().response();
	}

	@And("get the productId value from the response body")
	public String get_the_product_id_value_from_the_response_body() {

		addProductPojoResponse = response.as(AddProductResponse.class);
		return addProductPojoResponse.getProductId();

	}

	@And("Create product message in the response body is {string}")
	public void Create_product_message_in_the_response_body_is(String expectedValue) {
		assertEquals(addProductPojoResponse.getMessage(), expectedValue);
	}
}
