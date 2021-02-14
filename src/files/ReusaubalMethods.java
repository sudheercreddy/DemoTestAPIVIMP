package files;

import io.restassured.path.json.JsonPath;

public class ReusaubalMethods {
	
	
	public static JsonPath rawToJson(String getjsonResponse) {
		JsonPath js1= new JsonPath(getjsonResponse);
		return js1;
		
	}

}
