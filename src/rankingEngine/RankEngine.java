package rankingEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import database.CharacterMatchData;
import database.EntityMatchData;
import database.EventSimilarityMatchData;
import database.NERMatchData;
import database.RankingData;
import SearchingEngine.EventSimilarity;

public class RankEngine {

	private static ArrayList<String> rankedList = new ArrayList<String>();
	private static ArrayList<RankingData> rankTable = new ArrayList<RankingData>();
	
	public static ArrayList<RankingData> getRankedList(ArrayList<EventSimilarityMatchData> eventTable, 
			ArrayList<NERMatchData> nerTable , ArrayList<EntityMatchData> entityTable, ArrayList<CharacterMatchData> characterTable){
			
		Set<String> movieList = new HashSet<String>();
		
		//Insert rows or data for events
		for(EventSimilarityMatchData eventRow: eventTable){
			String movieTitle = eventRow.getMovieName();
			//Fresh Created Row
			if(!movieList.contains(movieTitle)){
				RankingData rankRow = new RankingData();
				rankRow.setMovieTitle(movieTitle);
				rankRow.setEventScore(eventRow.getScore());
				rankTable.add(rankRow);
				movieList.add(movieTitle);
			}
			
			else{
				int index = getRankTableRowNumber(movieTitle);
				rankTable.get(index).setEventScore(eventRow.getScore());
			}
		}
		
		//Insert rows or data for ner
		for(NERMatchData nerRow: nerTable){
			String movieTitle = nerRow.getMovieName();
			//Fresh Created Row
			if(!movieList.contains(movieTitle)){
				RankingData rankRow = new RankingData();
				rankRow.setMovieTitle(movieTitle);
				rankRow.setNerScore(nerRow.getScore());
				rankTable.add(rankRow);
				movieList.add(movieTitle);
			}
			else{
				int index = getRankTableRowNumber(movieTitle);
				rankTable.get(index).setNerScore(nerRow.getScore());
			}
		}
		
		
		//Insert rows or data for Entities
		
		for(EntityMatchData entityRow: entityTable){
			String movieTitle = entityRow.getMovieName();
			//Fresh Created Row
			if(!movieList.contains(movieTitle)){
				RankingData rankRow = new RankingData();
				rankRow.setMovieTitle(movieTitle);
				rankRow.setEntityScore(entityRow.getScore());
				rankTable.add(rankRow);
				movieList.add(movieTitle);
			}
			else{
				int index = getRankTableRowNumber(movieTitle);
				rankTable.get(index).setEntityScore(entityRow.getScore());
			}
		}
		
		
		//Insert rows or data for character
		
		for(CharacterMatchData characterRow: characterTable){
			String movieTitle = characterRow.getMovieName();
			//Fresh Created Row
			if(!movieList.contains(movieTitle)){
				RankingData rankRow = new RankingData();
				rankRow.setMovieTitle(movieTitle);
				rankRow.setCharacterScore(characterRow.getScore());
				rankTable.add(rankRow);
				movieList.add(movieTitle);
			}
			else{
				int index = getRankTableRowNumber(movieTitle);
				rankTable.get(index).setCharacterScore(characterRow.getScore());
			}
		}
		
				
		for(RankingData row : rankTable){
			int score = row.getEventScore() + row.getNerScore() + row.getEntityScore() + row.getCharacterScore();
			row.setTotalScore(score);
		}
		
		//Sorting the Ranking Table
		Collections.sort(rankTable, new Comparator<RankingData>(){
		     public int compare(RankingData dataRow1, RankingData dataRow2){
		         if(dataRow1.getTotalScore() == dataRow2.getTotalScore())
		             return 0;
		         return dataRow1.getTotalScore() < dataRow2.getTotalScore() ? 1 : -1;
		     }
		});
		
		
		
		return rankTable;
		
		
		
		
		
		
		/*
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
		
		*/
		
	}
	
	private static int getRankTableRowNumber(String movieTitle) {
		// TODO Auto-generated method stub
		int index=0;
		for(int i =0 ; i<rankTable.size(); i++){
			String movieNameExisting = rankTable.get(i).getMovieTitle(); 
			if(movieNameExisting.equalsIgnoreCase(movieTitle)){
				index =i;
				break;
			}
		}
		return index;
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
