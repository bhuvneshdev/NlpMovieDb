package EventExtraction;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;




public class KeywordExtraction {
	
	

	public static Set<String> getKeywords(String query) throws Exception{
		Set<String> keywordSet = new HashSet<String>();
		ArrayList<String> keywords = new ArrayList<String>();
		
		String responseString = sendPost(query);
		JSONObject jsonObject = new JSONObject(responseString);
		
		 
		System.out.println(jsonObject.toString(4));
		
		JSONArray jsonKeywordsArray = jsonObject.getJSONArray("keywords");
		for(int i =0 ; i< jsonKeywordsArray.length() ; i++){
			JSONObject wordObject = jsonKeywordsArray.getJSONObject(i);
			String[] keywordWithMultipleWords = wordObject.getString("text").split(" "); 
			for(String str : keywordWithMultipleWords){
				keywords.add(str);
			}
		}
		
		/*
		for(String str: keywords)
			System.out.println(str);
		*/
		for(String str: keywords){
			keywordSet.add(str);
		}
		
		return keywordSet;
		
		
	}
			
		private static String sendPost(String query) throws Exception {
			
			String USER_AGENT = "Mozilla/5.0";

			String url = "http://gateway-a.watsonplatform.net/calls/text/TextGetRankedKeywords";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "outputMode=json&apikey=f99038c7714b9ac345fe76e24e622ceeb86e110d&text="+query;
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'POST' request to URL : " + url);
			//System.out.println("Post parameters : " + urlParameters);
			//System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//print result
			return response.toString();

		}
}
