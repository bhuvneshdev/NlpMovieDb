package database;

import java.util.ArrayList;

public class EventSimilarityMatchData {
	public String movieName;
	public int matchCount;
	public int totalCount;
	public double threshold;
	public ArrayList<String> EventSimilarityMatches;
	public ArrayList<String> plotEventSimilarityMatches;
	
	public EventSimilarityMatchData(String movieName, int matchCount, int totalCount, double threshold, String[] EventSimilarityMatches, String[] plotEventSimilarityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.threshold = threshold;
		for(String str : EventSimilarityMatches){
			this.EventSimilarityMatches.add(str);
		}
		for(String str : plotEventSimilarityMatches){
			this.plotEventSimilarityMatches.add(str);
		}
	}
	
	public EventSimilarityMatchData(String movieName, int matchCount, int totalCount, double threshold, ArrayList<String> EventSimilarityMatches, ArrayList<String> plotEventSimilarityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.threshold = threshold;
		this.EventSimilarityMatches = EventSimilarityMatches;
		this.plotEventSimilarityMatches = plotEventSimilarityMatches;
	}
	
	public void setMatchData(String movieName, int matchCount, int totalCount, ArrayList<String> EventSimilarityMatches, ArrayList<String> plotEventSimilarityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.threshold = 0.8;
		for(String str : EventSimilarityMatches){
			this.EventSimilarityMatches.add(str);
		}
		for(String str : plotEventSimilarityMatches){
			this.plotEventSimilarityMatches.add(str);
		}
	};
	
	public String getMovieName(){
		return this.movieName;
	}

	@Override
	public String toString(){
		String result = "";
		result = this.movieName + " : " + this.matchCount + " , " + this.totalCount + " , " + this.threshold + " , " + this.EventSimilarityMatches.toString() + " , " + this.plotEventSimilarityMatches.toString(); 
		return result;
	}
	
	
}
