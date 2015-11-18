package database;

import java.util.ArrayList;

public class NERMatchData {

	public String movieName;
	public int matchCount;
	public int totalCount;
	public ArrayList<String> nerMatches;
	
	public NERMatchData(String movieName, int matchCount, int totalCount, String[] nerMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		for(String str : nerMatches){
			this.nerMatches.add(str);
		}
	
	}
	
	public NERMatchData(String movieName, int matchCount, int totalCount, ArrayList<String> nerMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.nerMatches = nerMatches;
		
	}
	
	public void setMatchData(String movieName, int matchCount, int totalCount , String[] nerMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		for(String str : nerMatches){
			this.nerMatches.add(str);
		}
	};
	
	public void setMatchData(String movieName, int matchCount, int totalCount , ArrayList<String> nerMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.nerMatches = nerMatches;
		
	};

	@Override
	public String toString(){
		String result = "";
		result = this.movieName + " : " + this.matchCount + " , " + this.totalCount + " " +nerMatches.toString(); 
		return result;
	}
	
	
}
