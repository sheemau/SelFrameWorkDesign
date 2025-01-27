package MausumiAcademy.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReaderUtil {
	
	//#174
	//public static List<HashMap<String, String>> getJsonDataToMap() throws IOException {
		
	public  List<HashMap<String, String>> getJsonDataToMap() throws IOException {
		//Java file - it will read a file, scan through an entire Json and convert to String.
		String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"//src//test//java//MausumiAcademy//data//PurchaseOrder.json"), 
				StandardCharsets.UTF_8);
	
		//C:\Users\shant\eclipse-workspace\SelFrameworkDesignSelFrameworkDesign\src\test\java\MausumiAcademy\data\PurchaseOrder.json
		//C:\Users\shant\eclipse-workspace\SelFrameworkDesign\src\test\java\MausumiAcademy\data\PurchaseOrder.json
		//convert String to HashMap - get new dependency called jackson databind
		ObjectMapper mapper = new ObjectMapper();
		
		//this says take the json string, return me a list of hashmaps
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
		
		
		return data;
	}
	
/*	public static void main(String[] args) throws IOException {
		
		//print out the LIst of Hashmaps
		List<HashMap<String, String>> data = getJsonDataToMap();
				//Iterator iterator = data.iterator();
				for (HashMap<String, String> hm: data) {
					for(Map.Entry<String, String> set: hm.entrySet()) {
						String key = set.getKey();
						String val = set.getValue();
						System.out.println("key=" + key + ".val=" + val);
					}
					
				}
		
	}*/

}
