package SearchingEngine;

import helperClass.HelperClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.helper.StringUtil;

import EventExtraction.KeywordExtraction;
import EventExtraction.NamedEntityRecognition;
import database.EntityMatchData;
import database.NERMatchData;

import org.apache.commons.lang3.StringUtils; 


public class EntitySimilarity {

	private static ArrayList<EntityMatchData> entityTable = new ArrayList<EntityMatchData>();
	
	public static ArrayList<EntityMatchData> getEntitySimilarityTable(String query, Set<String> entityQuerySet) throws Exception{
		
		
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
			String[] movieKeywordArray = jsonObject.get("keywords").toString().split(",");
			Set<String> entityPlotSet = getEntities(movieEntityArray,movieKeywordArray);
			System.out.println(movieTitle);
			System.out.println(getArrayListFromSet(entityQuerySet).toString());
			System.out.println(getArrayListFromSet(entityPlotSet).toString());
			if(entityPlotSet.size() == 0){
				return entityTable;
			}
			else{
				int count =0;
				Set<String> intersection = new HashSet<String>();
				for(String keyFromQuery : entityQuerySet){
					for(String keyFromPlot : entityPlotSet){
						if((compareStrings(keyFromPlot,keyFromQuery) > 0.90)){
							System.out.println(keyFromQuery + "  " + keyFromPlot);
							intersection.add(keyFromQuery + " " + keyFromPlot);
							count++;
							break;
						}
					}
				}
				
				if(count>0){
					EntityMatchData dataRow = new EntityMatchData(movieTitle, count, entityQuerySet.size() , getArrayListFromSet(intersection));
					entityTable.add(dataRow);
				}
				
				Collections.sort(entityTable, new Comparator<EntityMatchData>(){
				     public int compare(EntityMatchData dataRow1, EntityMatchData dataRow2){
				         if(dataRow1.score == dataRow2.score)
				             return 0;
				         return dataRow1.score < dataRow2.score ? 1 : -1;
				     }
				});
				
				/*
				Set<String> intersection = new HashSet<String>(entityPlotSet);
				intersection.retainAll(entityQuerySet);
				intersectionCount = intersection.size();
				if(intersectionCount > 0){
					EntityMatchData dataRow = new EntityMatchData(movieTitle, intersectionCount, entityQuerySet.size() , getArrayListFromSet(intersection));
					entityTable.add(dataRow);
				}
				*/
			}
			System.out.println("=============================");
		}
		//System.out.println(nerTable.size());
		
		entityTable = getTopItemsOfTable(entityTable, 4);
		
		return entityTable;
	}
	
	private static ArrayList<EntityMatchData> getTopItemsOfTable(
		ArrayList<EntityMatchData> entityTable2, int topItemsCount) {
		
		ArrayList<EntityMatchData> tempArray = new ArrayList<EntityMatchData>();
		tempArray.add(entityTable2.get(0));
		int count =0;
		EntityMatchData row;
		for(int i =1; i< entityTable2.size() ; i++){
			row = entityTable2.get(i);
			if(tempArray.get(i-1).score != row.score){
				if(count<3){
					tempArray.add(row);
					count++;
				}
				else{
					break;
				}
			}
			else{
				tempArray.add(row);
			}
		}
		
		return tempArray;
	}

	private static ArrayList<String> getArrayListFromSet(Set<String> intersection) {
		ArrayList<String> arrayList = new ArrayList<String>();
		for(String str: intersection){
			arrayList.add(str);
		}
		return arrayList;
	}

	public static Set<String> getEntities(String[] entityStringArray, String[] keywordStringArray){
		Set<String> movieEntitySet = new HashSet<String>();
		for(String str: entityStringArray){
			str = str.toLowerCase().trim().replace("'", "");
			movieEntitySet.add(str.trim().toLowerCase());
		}
		for(String str: keywordStringArray){
			str = str.replace("'", "");
			movieEntitySet.add(str.trim().toLowerCase());
		}
		
		return movieEntitySet;
	}
	
	
	  
	private static double compareStrings(String stringA, String stringB) {
	    return StringUtils.getJaroWinklerDistance(stringA, stringB);
	}
}
