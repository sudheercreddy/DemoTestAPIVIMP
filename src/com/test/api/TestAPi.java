package com.test.api;

import io.restassured.RestAssured;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReusaubalMethods;

public class TestAPi {
	
	
	public static void main (String[]args) {
		
		
	//	Given--all inputs
	//	when-submit the API
	//	Thne-Validate the response
		
		//Set Base URI
		//Add the place_ID
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").
		header("Content-Type","application/json").
		body(Payload.Addplace()).
		when().post("maps/api/place/add/json").
		then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server","Apache/2.4.18 (Ubuntu)").
		extract().response().asString();
		
		//Add place --> update place with new address--> get place to validate if new address is returned.
		System.out.println(response);
		
		JsonPath js= new JsonPath(response);
		String placeID=js.getString("place_id");
		System.out.println("place_ID is "+placeID);
		
		//update the place_ID
		System.out.println("+++++UPDATEd ADDRESS+++++");
		String newAddress= "13430 woodson street , kansas";
		
	given().log().all().queryParam("Key", "qaclick123").header("Content-Type", "application/json").
	body("{\r\n" + 
			"\"place_id\":\""+placeID+"\",\r\n" + 
			"\"address\":\""+newAddress+"\",\r\n" + 
			"\"key\":\"qaclick123\"\r\n" + 
			"}").
	when().put("maps/api/place/update/json").
	then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
	//get or retrieve the place ID
	
	
	
	String getNewAddress=given().queryParam("key", "qaclick123").queryParam("place_id", placeID).
	when().get("maps/api/place/get/json").
	then().assertThat().statusCode(200).header("Connection", "Keep-Alive").extract().asString();
	
	System.out.println("response of GET is "+ getNewAddress);
	
	//JsonPath js1= new JsonPath(getNewAddress); 
	
	JsonPath js1=ReusaubalMethods.rawToJson(getNewAddress);
	String extractAddress=js1.getString("address");
	System.out.println(" extracted address is :----"+extractAddress);
	
Assert.assertEquals(newAddress, newAddress, "not equal");
	
	if(newAddress.equalsIgnoreCase(extractAddress)) {
		System.out.println("Matching Happies!!!");
	}
	
	}	
}
