import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.me.JSONException;
import org.json.me.JSONObject;


/**
 * HTTP Communication Class that handles the request to find the driving distance and time of two
 * addresses. The http GET request is done to Google's Map API
 * @author dany
 *
 */
public class HttpRequest {
	
	//Google's Map API Url, in JSON format and Imperial units
	private static final String URL_STRING = "http://maps.googleapis.com/maps/api/distancematrix/json?sensor=false&units=imperial";

	public static JSONObject getJSON(String origin, String destinantion) throws IOException{
		
		//Start Http Client
		HttpClient client = new DefaultHttpClient();
		
		//Create Full Url from addresses
		String url = URL_STRING + "&origins=" + origin.replaceAll("\\s", "%20") + "&destinations=" + destinantion.replaceAll("\\s", "%20");
		
		try {
			//Create GET Request and execute it
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			int statusCode = response.getStatusLine().getStatusCode();
			
			//Validate Http Response
			if(statusCode == HttpStatus.SC_OK){
				//Retrive response, in JSON format and return it
				String responseString = EntityUtils.toString(response.getEntity());
				String cleanString = responseString.replace("\n", "").replace("\t", "");
				return new JSONObject(cleanString);
			} else {
				System.out.println("HTTP Fail, Response Code: " + statusCode);
				System.exit(1);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return null;
	}
}
