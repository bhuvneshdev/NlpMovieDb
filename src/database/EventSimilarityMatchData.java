package database;

import java.util.ArrayList;

public class EventSimilarityMatchData {
	public String movieName;
	public int matchCount;
	public int totalCount;
	public double score;
	public double threshold;
	public ArrayList<String> EventSimilarityMatches;
	public ArrayList<String> plotEventSimilarityMatches;
	
	public EventSimilarityMatchData(String movieName, int matchCount, int totalCount, double threshold,double score, String[] EventSimilarityMatches, String[] plotEventSimilarityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.threshold = threshold;
		this.score = score;
		for(String str : EventSimilarityMatches){
			this.EventSimilarityMatches.add(str);
		}
		for(String str : plotEventSimilarityMatches){
			this.plotEventSimilarityMatches.add(str);
		}
	}
	
	public EventSimilarityMatchData(String movieName, int matchCount, int totalCount, double threshold,double score, ArrayList<String> EventSimilarityMatches, ArrayList<String> plotEventSimilarityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.threshold = threshold;
		this.score = score;
		this.EventSimilarityMatches = EventSimilarityMatches;
		this.plotEventSimilarityMatches = plotEventSimilarityMatches;
	}
	
	public void setMatchData(String movieName, int matchCount, int totalCount,double score, ArrayList<String> EventSimilarityMatches, ArrayList<String> plotEventSimilarityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.threshold = 0.8;
		this.score = score;
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
	
	public int getMatchCount(){
		return this.matchCount;
	}
	
	public double getScore(){
		return this.score;
	}
	
	@Override
	public String toString(){
		String result = "";
		result = this.movieName + " : " + this.matchCount + " , " + this.totalCount + " , " + this.threshold + " , " + this.score + " , " + this.EventSimilarityMatches.toString() + " , " + this.plotEventSimilarityMatches.toString(); 
		return result;
	}
	
	
}
