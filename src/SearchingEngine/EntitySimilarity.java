package SearchingEngine;

import helperClass.HelperClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import EventExtraction.KeywordExtraction;
import EventExtraction.NamedEntityRecognition;
import database.EntityMatchData;
import database.NERMatchData;


public class EntitySimilarity {

	private static ArrayList<EntityMatchData> entityTable = new ArrayList<EntityMatchData>();
	
	public static ArrayList<EntityMatchData> getEntitySimilarityTable(String query) throws Exception{
		
		Set<String> entityQuerySet = KeywordExtraction.getKeywords(query);
		int totalEntityQueryCount = entityQuerySet.size();
		if(totalEntityQueryCount == 0){
			return entityTable;
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
			String[] movieEntityArray = jsonObject.get("entities").toString().split(","); 
			Set<String> entityPlotSet = getEntities(movieEntityArray);
			System.out.println(movieTitle);
			System.out.println(getArrayListFromSet(entityQuerySet).toString());
			System.out.println(getArrayListFromSet(entityPlotSet).toString());
			if(entityPlotSet.size() == 0){
				return null;
			}
			else{
				Set<String> intersection = new HashSet<String>(entityPlotSet);
				intersection.retainAll(entityQuerySet);
				intersectionCount = intersection.size();
				if(intersectionCount > 0){
					EntityMatchData dataRow = new EntityMatchData(movieTitle, intersectionCount, entityQuerySet.size() , getArrayListFromSet(intersection));
					entityTable.add(dataRow);
				}
			}
			System.out.println("=============================");
		}
		//System.out.println(nerTable.size());
		
		return entityTable;
	}
	
	private static ArrayList<String> getArrayListFromSet(Set<String> intersection) {
		ArrayList<String> arrayList = new ArrayList<String>();
		for(String str: intersection){
			arrayList.add(str);
		}
		return arrayList;
	}

	public static Set<String> getEntities(String[] entityStringArray){
		Set<String> movieEntitySet = new HashSet<String>();
		for(String str: entityStringArray){
			str = str.replace("'", "");
			movieEntitySet.add(str.trim().toLowerCase());
		}
		return movieEntitySet;
	}
	
}
