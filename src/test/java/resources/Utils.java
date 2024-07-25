package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {

	public static RequestSpecification reqspec;
	public static ResponseSpecification respspec;

	public RequestSpecification requestSpecification() throws IOException {
		if (reqspec == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			reqspec = new RequestSpecBuilder().setBaseUri(globalProperties("baseURL"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();

			return reqspec;
		}
		return reqspec;
	}

	public RequestSpecification requestSpecificationForFormData() throws IOException {
		if (reqspec == null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			reqspec = new RequestSpecBuilder().setBaseUri(globalProperties("baseURL"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();

			return reqspec;
		}
		return reqspec;
	}

	public ResponseSpecification responseSpecification(int statusCode) {
		if (respspec == null) {
			respspec = new ResponseSpecBuilder().expectStatusCode(statusCode).build();
			
			return respspec;
		}
		return respspec;
	}

	public static String globalProperties(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}

}
