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
	private static ArrayList<NERMatchData> nerTable = new ArrayList<NERMatchData>();
	
	public static ArrayList<NERMatchData> getNERSimilarityTable(String query) throws ClassCastException, ClassNotFoundException, IOException, JSONException{
		
		Set<String> nerQuerySet = NamedEntityRecognition.getNamedEntities(query);
		int totalNERQueryCount = nerQuerySet.size();
		if(totalNERQueryCount == 0){
			return null;
		}
		//System.out.println(nerQuerySet.toString());
		
		ArrayList<String> allFilesName = HelperClass.getAllFileNamesInFolder("Data//CoreferencedPlots//");
		for(String fileName : allFilesName){
			//System.out.println(fileName);
		}
		
		String jsonStringFromFile = HelperClass.getDataInFile("Data//NERPlots//allMoviesNER.txt");
		JSONArray jsonArray = new JSONArray(jsonStringFromFile);
		//System.out.println(jsonArray.toString(4));
		
		for(int i =0 ; i< jsonArray.length() ; i++){
			int intersectionCount = 0;
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String movieTitle = (String) jsonObject.get("movieTitle");
			String[] movieNERArray = jsonObject.get("ner").toString().split(","); 
			Set<String> nerPlotSet = getNER(movieNERArray);
			//System.out.println(movieTitle);
			//System.out.println(getArrayListFromSet(nerQuerySet).toString());
			//System.out.println(getArrayListFromSet(nerPlotSet).toString());
			if(nerPlotSet.size() == 0){
				return null;
			}
			else{
				Set<String> intersection = new HashSet<String>(nerPlotSet);
				intersection.retainAll(nerQuerySet);
				intersectionCount = intersection.size();
				if(intersectionCount > 0){
					NERMatchData dataRow = new NERMatchData(movieTitle, intersectionCount, nerQuerySet.size() , getArrayListFromSet(intersection));
					nerTable.add(dataRow);
				}
			}
			//System.out.println("=============================");
		}
		//System.out.println(nerTable.size());
		
		return nerTable;
	}
	
	private static ArrayList<String> getArrayListFromSet(Set<String> intersection) {
		ArrayList<String> arrayList = new ArrayList<String>();
		for(String str: intersection){
			arrayList.add(str);
		}
		return arrayList;
	}

	public static Set<String> getNER(String[] nerStringArray){
		Set<String> movieNERSet = new HashSet<String>();
		for(String str: nerStringArray){
			movieNERSet.add(str.trim().toLowerCase());
		}
		return movieNERSet;
	}
	
}
