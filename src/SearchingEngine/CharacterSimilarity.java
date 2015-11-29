package SearchingEngine;

import helperClass.Characters;
import helperClass.HelperClass;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import database.CharacterMatchData;
import database.SemanticRoleMatchData;

public class CharacterSimilarity {

	static ArrayList<CharacterMatchData> characterMatchDataTable = new ArrayList<CharacterMatchData>();
	public static ArrayList<CharacterMatchData> getCharacterSimilarityTable(String query) throws IOException, JSONException{
		

		String[] linesInQuery = query.split("(?<=[.?!])\\s+(?=[a-zA-Z])");

		ArrayList<String> allFilesName = HelperClass.getAllFileNamesInFolder("Data//SemanticRolesJSONFiles//");
		for(String fileName : allFilesName){
			System.out.println(fileName);
		}
		
		for(String fileName: allFilesName){
			String movieJSONString = HelperClass.getDataInFile("Data//SemanticRolesJSONFiles//" + fileName);
			JSONObject movieJSON = new JSONObject(movieJSONString);
			System.out.println(movieJSON.get("movieName"));
		}
		
		/*
		ArrayList<SemanticRoleMatchData> queryCharactersFiltered = Characters.filterCharacters(queryCharacters);
		
		System.out.println("=========================================");
		for(SemanticRoleMatchData character: queryCharactersFiltered ){
			System.out.println(character.getNoun());
			System.out.println(character.getTraits().toString());
			System.out.println(character.getRelatedNouns().toString());
			System.out.println(character.getSemanticRoles().toString());
			System.out.println(character.getVerbs().toString());
			System.out.println(character.getRecepients().toString());
		}
		
		*/
		
		
		
		
		return characterMatchDataTable;
	}
	
	
}
