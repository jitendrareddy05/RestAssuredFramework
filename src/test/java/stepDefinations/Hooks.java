package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	@Before("@CreateProduct")
	public void beforeScenario() throws IOException {
		Login_StepDefinationImp login = new Login_StepDefinationImp();
		
		if (Login_StepDefinationImp.token == null) {
			login.login_payload_with("jitendra.ts@gmail.com", "Password@123");
			login.user_calls_with_http_request_to_login("Login", "Post");
			login.the_api_call_will_be_success_with_status_code(200);
			login.get_the_token_value_from_the_response_body();
			login.get_the_uderId_value_from_the_response_body();
		}
	}

}
