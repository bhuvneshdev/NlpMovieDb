package database;

import jnr.ffi.Struct.int16_t;

public class RankingData {

	String movieTitle;
	int eventScore;
	int nerScore;
	int entityScore;
	int totalScore;
	
	public RankingData(){
		this.movieTitle = "";
		this.entityScore =0;
		this.nerScore =0;
		this.eventScore =0;
		this.totalScore =0;
	}
	
	public void setMovieTitle(String movieTitle){
		this.movieTitle = movieTitle;
	}
	
	public void setEntityScore(int score){
		this.entityScore  = score;
	}
	
	public void setNerScore(int score){
		this.nerScore = score;
	}
	
	
	public void setEventScore(int score){
		this.eventScore = score;
	}
	
	public void setTotalScore(int score){
		this.totalScore = score;
	}
	
	public int getTotalScore(){
		return this.totalScore;
	}
	
	public String getMovieTitle(){
		return this.movieTitle;
	}
	
	public int getEventScore(){
		return this.eventScore;
	}
	
	public int getNerScore() {
		return this.nerScore;
	}
	
	public int getEntityScore() {
		return this.entityScore;
	}

	@Override
	public String toString(){
		String result = "";
		result = this.movieTitle + " : " + this.eventScore + " , " + this.nerScore + " , " + 
		this.entityScore + " , " + this.totalScore ; 
		return result;
	}
	
}
