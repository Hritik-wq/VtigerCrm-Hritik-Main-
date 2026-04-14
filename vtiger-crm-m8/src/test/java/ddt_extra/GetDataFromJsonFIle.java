package ddt_extra;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GetDataFromJsonFIle {
	public static void main(String[] args) throws IOException, ParseException {
		FileReader fr = new FileReader("./src/test/resources/cd2.json");
		
		JSONParser jp = new JSONParser();
		Object obj = jp.parse(fr);
		
		JSONObject pObj = (JSONObject) obj;
		String browser = pObj.get("bro").toString();
		System.out.println(browser);
	}
}