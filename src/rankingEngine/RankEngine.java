package rankingEngine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import database.EventSimilarityMatchData;
import database.NERMatchData;
import SearchingEngine.EventSimilarity;

public class RankEngine {

	
	
	public static ArrayList<String> getRankedList(ArrayList<EventSimilarityMatchData> eventTable, 
			ArrayList<NERMatchData> nerTable){
		
		
		ArrayList<String> movieList = new ArrayList<String>();
		Set<String> movieListFromEventMatch = new HashSet<String>();
		Set<String> movieListFromNERMatch = new HashSet<String>();
		
		System.out.println("===== Event Match =====");
		for(EventSimilarityMatchData eventRow : eventTable ){
			movieListFromEventMatch.add(eventRow.getMovieName());
		}
		
		System.out.println("===== NER Match =====");
		for(NERMatchData nerRow : nerTable ){
			movieListFromNERMatch.add(nerRow.getMovieName());
		}
		
		Set<String> intersection = new HashSet<String>(movieListFromEventMatch); // use the copy constructor
		intersection.retainAll(movieListFromNERMatch);
		
		System.out.println("======Intersection ====");
		for(String str: intersection){
			System.out.println(str);
		}
		
		return movieList;
		
	}
	
	
	
	
}
