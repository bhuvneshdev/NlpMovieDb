package database;

import java.util.ArrayList;

public class EntityMatchData {

	public String movieName;
	public int matchCount;
	public int totalCount;
	public ArrayList<String> entityMatches;
	
	public EntityMatchData(String movieName, int matchCount, int totalCount, String[] entityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		for(String str : entityMatches){
			this.entityMatches.add(str);
		}
	
	}
	
	public EntityMatchData(String movieName, int matchCount, int totalCount, ArrayList<String> entityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.entityMatches = entityMatches;
		
	}
	
	public void setMatchData(String movieName, int matchCount, int totalCount , String[] entityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		for(String str : entityMatches){
			this.entityMatches.add(str);
		}
	};
	
	public void setMatchData(String movieName, int matchCount, int totalCount , ArrayList<String> entityMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.entityMatches = entityMatches;
		
	};
	
	public String getMovieName(){
		return this.movieName;
	}

	@Override
	public String toString(){
		String result = "";
		result = this.movieName + " : " + this.matchCount + " , " + this.totalCount + " " +entityMatches.toString(); 
		return result;
	}
}
