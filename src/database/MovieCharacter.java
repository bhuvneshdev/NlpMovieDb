package database;

import java.util.ArrayList;
import java.util.Set;

public class MovieCharacter {

	String movieTitle;
	ArrayList<String> charactersArray = new ArrayList<String>();
	
	public MovieCharacter(String movieTitle , Set<String> characterSet) {
		// TODO Auto-generated constructor stub
		this.movieTitle = movieTitle;
		this.charactersArray = getArrayListFromSet(characterSet);
	}
	
	public MovieCharacter(String movieTitle , ArrayList<String> characterArrayList) {
		// TODO Auto-generated constructor stub
		this.movieTitle = movieTitle;
		this.charactersArray = characterArrayList;
	}
	
	public String getMovieTitle(){
		return this.movieTitle;
	}

	public ArrayList<String> getMovieCharacters(){
		return this.charactersArray;
	}
	
	
	private ArrayList<String> getArrayListFromSet(Set<String> characterSet) {
		// TODO Auto-generated method stub
		ArrayList<String> characters = new ArrayList<String>();
		for(String character: characterSet){
			characters.add(character);
		}
		return characters;
	}
	
	@Override
	public String toString(){
		String result=this.movieTitle + " ";
		for(String str: charactersArray){
			result = result + str +" ";
		}
		return result;
		
	}
	
}
