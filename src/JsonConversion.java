import javax.json.*;
import java.util.*;
import java.io.*;

public class JsonConversion {
		
	public static String buildJSONStringArray(String users){
				
		JsonObject jsonObject = Json.createObjectBuilder()
				.add("contacts", users)
				.build();
		return jsonObject.toString();
	}
	
	public static String buildJSONRecord(boolean status, String buffer){
		JsonObject jsonObject = Json.createObjectBuilder()
				.add("isFound", status)
				.add("content", buffer)
				.build();
		return jsonObject.toString();
	}
	
	public static String buildJsonData(String username, String message, String time){
		JsonObject jsonObject = Json.createObjectBuilder()
				.add("user", username)
				.add("message", message)
				.add("time", time)
				.build();
		StringWriter stringWriter = new StringWriter();
		try(JsonWriter jsonWriter = Json.createWriter(stringWriter)){
			jsonWriter.write(jsonObject);
		}
		return stringWriter.toString();
	}
		
}
