package rankingEngine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import database.EventSimilarityMatchData;
import database.NERMatchData;
import SearchingEngine.EventSimilarity;

public class RankEngine {

	public static ArrayList<String> rankedList = new ArrayList<String>();
	
	public static ArrayList<String> getRankedList(ArrayList<EventSimilarityMatchData> eventTable, 
			ArrayList<NERMatchData> nerTable){
		
		
		ArrayList<String> movieList = new ArrayList<String>();
		Set<String> movieListFromEventMatch = new HashSet<String>();
		Set<String> movieListFromNERMatch = new HashSet<String>();
		
		ArrayList<RankEngine.MovieScoreData> events = new ArrayList<RankEngine.MovieScoreData>();
		
		for(EventSimilarityMatchData event : eventTable){
			String movieName = event.getMovieName();
			int matchCount = event.getMatchCount();
			MovieScoreData movieScoreData = new MovieScoreData(movieName, matchCount);
			events.add(movieScoreData);
		}
		
		/*
		for(MovieScoreData event : events){
			System.out.println(event.movieName + "  " + event.eventMatchCount);
		}
		*/
		
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
		
		/*
		System.out.println("======Intersection ====");
		for(String str: intersection){
			System.out.println(str);
		}
		*/
		
		for(String str: intersection){
			rankedList.add(str);
		}
		
		for(String str : intersection){
			for(int i =0 ; i< events.size() ; i++){
				MovieScoreData row = events.get(i);
				if(row.movieName.equals(str)){
					events.remove(i);
				}
			}
		}
		
		System.out.println("==================================");
		for(MovieScoreData row : events){
			rankedList.add(row.movieName);
		}
		
		
		return rankedList;
		
	}
	
	public static class MovieScoreData{
		String movieName;
		int eventMatchCount;
		
		public MovieScoreData(String movieName, int matchCount){
			this.movieName = movieName;
			this.eventMatchCount = matchCount;
		}
	}
	
	
	
	
}
