package database;

import java.util.ArrayList;

public class CharacterMatchData {
	
	public String movieName;
	public int matchCount;
	public int totalCount;
	public ArrayList<String> matches;
	public int score;
	
	public CharacterMatchData(String movieName, int matchCount, int totalCount, String[] characterDataMatches, int score){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
		this.score = (int)score;
		for(String str : characterDataMatches){
			this.matches.add(str);
		}
	}
	
	public CharacterMatchData(String movieName, int matchCount, int totalCount){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
	}
	
	@Override
	public String toString(){
		return this.movieName + "  " + String.valueOf(matchCount)  + ",  " + String.valueOf(totalCount); 
	}
	
	public int getScore(){
		return (this.matchCount*200)/this.totalCount;
	}
	
	public String getMovieName(){
		return this.movieName;
	}
}
