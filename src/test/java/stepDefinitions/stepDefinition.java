package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class stepDefinition extends Utils{
	
	RequestSpecification res;
	ResponseSpecification Resspec;
	Response response;
	static String place_id;
	TestDataBuild data = new TestDataBuild();
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String address, String language) throws IOException {
		
		
		
		res = given().spec(requestSpecification())
		.body(data.addPlacePayload(name,address,language));
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getresource());
		
		Resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST"))
			response = res.when().post(resourceAPI.getresource());
		else if(method.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getresource());
	   
	}
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(),200);
		
	    
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedKeyValue) {
		
		
		assertEquals(getJsonPath(response,keyValue),expectedKeyValue);
	   
	}
	
	@Then("Verify Place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    
		place_id = getJsonPath(response,"place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource, "GET");
		String actualName = getJsonPath(response,"name");
		assertEquals(actualName,expectedName);
		
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    
		res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
		
	}

}
