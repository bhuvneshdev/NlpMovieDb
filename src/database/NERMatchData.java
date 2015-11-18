package database;

public class NERMatchData {

	public String movieName;
	public int matchCount;
	public int totalCount;
	public NERMatchData(String movieName, int matchCount, int totalCount){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
	}
	
	public void setMatchData(String movieName, int matchCount, int totalCount){
		this.movieName = movieName;
		this.matchCount = matchCount;
		this.totalCount = totalCount;
	};

	@Override
	public String toString(){
		String result = "";
		result = this.movieName + " : " + this.matchCount + " , " + this.totalCount; 
		return result;
	}
	
	
}
