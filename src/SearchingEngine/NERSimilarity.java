package SearchingEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import EventExtraction.NamedEntityRecognition;

import com.sun.xml.internal.ws.api.ha.HaInfo;

import database.NERMatchData;

public class NERSimilarity {

	/*
	 * Returns a hashmap of <MovieName, MatchCount , Total> 
	 * for all movies corresponding to a query
	 */
	ArrayList<NERMatchData> nerTable = new ArrayList<NERMatchData>();
	
	public static void getNERSimilarityTable(String query) throws ClassCastException, ClassNotFoundException, IOException{
		
		Set<String> nerQuerySet = NamedEntityRecognition.getNamedEntities(query);
		for(String str: nerQuerySet){
			System.out.println(str);
		}
		
	}
	
}
