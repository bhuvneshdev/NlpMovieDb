package SearchingEngine;

import helperClass.HelperClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import EventExtraction.NamedEntityRecognition;

import com.sun.xml.internal.ws.api.ha.HaInfo;

import database.NERMatchData;

public class NERSimilarity {

	/*
	 * Returns a hashmap of <MovieName, MatchCount , Total> 
	 * for all movies corresponding to a query
	 */
	ArrayList<NERMatchData> nerTable = new ArrayList<NERMatchData>();
	
	public static void getNERSimilarityTable(String query) throws ClassCastException, ClassNotFoundException, IOException, JSONException{
		
		Set<String> nerQuerySet = NamedEntityRecognition.getNamedEntities(query);
		System.out.println(nerQuerySet.toString());
		
		ArrayList<String> allFilesName = HelperClass.getAllFileNamesInFolder("Data//CoreferencedPlots//");
		for(String fileName : allFilesName){
			//System.out.println(fileName);
		}
		
		String jsonStringFromFile = HelperClass.getDataInFile("Data//NERPlots//allMoviesNER.txt");
		JSONArray jsonArray = new JSONArray(jsonStringFromFile);
		//System.out.println(jsonArray.toString(4));
		
		for(int i =0 ; i< jsonArray.length() ; i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String movieTitle = (String) jsonObject.get("movieTitle");
			String[] movieNERArray = jsonObject.get("ner").toString().split(","); 
			//System.out.println(movieTitle);
			System.out.println(getNER(movieNERArray).toString());
			
		}
	}
	
	public static Set<String> getNER(String[] nerStringArray){
		Set<String> movieNERSet = new HashSet<String>();
		for(String str: nerStringArray){
			movieNERSet.add(str.trim().toLowerCase());
		}
		return movieNERSet;
	}
	
}
