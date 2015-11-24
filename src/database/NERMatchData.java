package database;

import java.util.ArrayList;

public class NERMatchData {

	public String movieName;
	public int matchCount;
	public int totalCount;
	public ArrayList<String> nerMatches;
	public int score;
	
	public NERMatchData(String movieName, int matchCount, int totalCount, String[] nerMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		for(String str : nerMatches){
			this.nerMatches.add(str);
		}
		this.score = (matchCount/totalCount) *100;
	
	}
	
	public NERMatchData(String movieName, int matchCount, int totalCount, ArrayList<String> nerMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.nerMatches = nerMatches;
		this.score = (matchCount*100)/totalCount;
	}
	
	public void setMatchData(String movieName, int matchCount, int totalCount , String[] nerMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		for(String str : nerMatches){
			this.nerMatches.add(str);
		}
		this.score = (matchCount*100)/totalCount;
	}
	
	public void setMatchData(String movieName, int matchCount, int totalCount , ArrayList<String> nerMatches){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.nerMatches = nerMatches;
		this.score = (matchCount*100)/totalCount;
		
	};
	
	public String getMovieName(){
		return this.movieName;
	}

	public int getScore(){
		return this.score;
	}
	@Override
	public String toString(){
		String result = "";
		result = this.movieName + " : " + this.matchCount + " , " + this.totalCount + " " + this.score + " " +nerMatches.toString(); 
		return result;
	}
	
	
}
