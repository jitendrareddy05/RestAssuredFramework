package resources;

import java.util.HashMap;
import java.util.Map;

import pojo.LoginPayLoad;
import stepDefinations.Login_StepDefinationImp;

public class TestDataBuild {

	public LoginPayLoad loginPayload(String userEmail, String userPassword) {

		LoginPayLoad lp = new LoginPayLoad();
		lp.setUserEmail(userEmail); 
		lp.setUserPassword(userPassword);

		return lp;
	}
	
	public Map<String, String> createProductPayLoad() {
		Login_StepDefinationImp login =new Login_StepDefinationImp();
		Map<String, String> map=new HashMap<String, String>();
		map.put("productName", "Jumkha");
		map.put("productAddedBy", login.get_the_uderId_value_from_the_response_body());
		map.put("productCategory", "fashion");
		map.put("productSubCategory", "jewellery");
		map.put("productPrice", "11500");
		map.put("productDescription", "Malabar Jewellery");
		map.put("productFor", "women");
		map.put("productFor", "women");
		
		return map;
	}
}
