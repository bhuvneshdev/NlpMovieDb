package database;

import java.util.ArrayList;

public class EntityMatchData {

	public String movieName;
	public int matchCount;
	public int totalCount;
	public ArrayList<String> entityMatches;
	public int score;
	
	public EntityMatchData(String movieName, int matchCount, int totalCount, String[] entityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		for(String str : entityMatches){
			this.entityMatches.add(str);
		}
		this.score=(matchCount*100)/totalCount;
	}
	
	public EntityMatchData(String movieName, int matchCount, int totalCount, ArrayList<String> entityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.entityMatches = entityMatches;
		this.score =(matchCount*100)/totalCount;
	}
	
	public void setMatchData(String movieName, int matchCount, int totalCount , String[] entityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		for(String str : entityMatches){
			this.entityMatches.add(str);
		}
		this.score =(matchCount*100)/totalCount;
	};
	
	public void setMatchData(String movieName, int matchCount, int totalCount , ArrayList<String> entityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.entityMatches = entityMatches;
		this.score = (matchCount*100)/totalCount;
	};
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return this.score;
	}
	
	
	public String getMovieName(){
		return this.movieName;
	}
	

	@Override
	public String toString(){
		String result = "";
		result = this.movieName + " : " + this.matchCount + " , " + this.totalCount + " , " + this.score + " , "+entityMatches.toString(); 
		return result;
	}
	
	
}
