package SearchingEngine;

import helperClass.Characters;
import helperClass.HelperClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import module.graph.helper.JAWSutility;

import org.apache.commons.collections15.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.CharacterMatchData;
import database.MovieCharacter;
import database.SemanticRoleMatchData;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.impl.Lin;

public class CharacterSimilarity {
	
	private static final String characterName = "characterName";
	private static final String recipients ="recipients";
	private static final String verbs = "verbs";
	private static final String traits = "traits";
	private static final String semanticRoles = "semanticRoles";
	
	static ArrayList<CharacterMatchData> characterMatchDataTable = new ArrayList<CharacterMatchData>();
	static Map<String, ArrayList<SemanticRoleMatchData>> allMovieCharactersMap = new HashedMap<String, ArrayList<SemanticRoleMatchData>>();  
	
	private static ILexicalDatabase db = new NictWordNet();
	static JAWSutility jaws = new JAWSutility();
	
	static ArrayList<String> auxillaryVerbs = new ArrayList<String>();
	
	public static ArrayList<CharacterMatchData> getCharacterSimilarityTable(String query) throws IOException, JSONException{
		
		//Retrieving Characters for the query and filtering the result 
		String[] linesInQuery = query.split("(?<=[.?!])\\s+(?=[a-zA-Z])");
		ArrayList<SemanticRoleMatchData> queryCharacters = SemanticRoleSimilarity.semanticRoleSimilarity(linesInQuery);
		ArrayList<SemanticRoleMatchData> queryCharactersFiltered = Characters.filterCharacters(queryCharacters);
		
		for(SemanticRoleMatchData character : queryCharactersFiltered){
			System.out.println(character.toString());
		}
		
		//Retrieving Map of all characters of the movies 
		allMovieCharactersMap = getAllMoviesCharactersMap();
		
		//Filtering to top ten characters of the movies 
		//filterMovieCharactersMap(allMovieCharactersMap, 10);
		
		auxillaryVerbs = getAuxillaryVerbs();
		
		characterMatchDataTable = getCharacterMatchTable(queryCharactersFiltered, allMovieCharactersMap);
		


		/*
		for (Entry<String, ArrayList<SemanticRoleMatchData>> entry : allMovieCharactersMap.entrySet())
		{
			String movieName = entry.getKey();
			ArrayList<SemanticRoleMatchData> characters = entry.getValue();
			System.out.println("<<<< " + movieName + " >>>>");
			for(SemanticRoleMatchData character: characters){
				System.out.println(">>>>" + character.getNoun());
				System.out.println("traits: " +character.getTraits().toString());
				System.out.println("semanticRole: "+ character.getSemanticRoles().toString());
				System.out.println("verbs: " + character.getVerbs().toString());
				System.out.println("recepients: " +character.getRecepients().toString());
			}
		}
		*/
		
		


				
		
		return characterMatchDataTable;
	}


	private static ArrayList<String> getAuxillaryVerbs() {
		// TODO Auto-generated method stub
		String[] array = {"am", "are", "is", "was", "were", "being", "be", "can", "could", "do", "did", "does",
				"doing", "have", "has", "having", "may", "might", "must", "shall", "should", "will", "would"};
		ArrayList<String> list = new ArrayList<String>();
		for(String str : array){
			list.add(str);
		}
		
		return list;
	}


	private static ArrayList<CharacterMatchData> getCharacterMatchTable(ArrayList<SemanticRoleMatchData> queryCharactersFiltered,
			Map<String, ArrayList<SemanticRoleMatchData>> allMovieCharactersMap2) {
		
		ArrayList<CharacterMatchData> charactersMatchDataTable = new ArrayList<CharacterMatchData>();
		int charactersWeight = getCharactersWeight(queryCharactersFiltered);
		//Iterating over all movies
		for (Entry<String, ArrayList<SemanticRoleMatchData>> entry : allMovieCharactersMap2.entrySet())
		{
			String movieName = entry.getKey();
			ArrayList<SemanticRoleMatchData> movieCharacters = entry.getValue();
			int totalScore=0;
			int maxCount = getCharactersWeight(queryCharactersFiltered);
			//Iterating over all characters of query
			for(SemanticRoleMatchData queryCharacter : queryCharactersFiltered){
				int max =0;
				int score =0;
				//Iterating over all characters of the movie to find the max 
				for(SemanticRoleMatchData movieCharacter:  movieCharacters){
					score = getCharacterSimilarity(queryCharacter, movieCharacter);
					if(score > max){
						max = score; 
					}
					
				}
				totalScore = totalScore +max;
			}
			CharacterMatchData characterMatchDataRow = new CharacterMatchData(movieName, totalScore, maxCount);
			charactersMatchDataTable.add(characterMatchDataRow);			
		}
		
		
		
		return charactersMatchDataTable;
	}


	private static int getCharactersWeight(ArrayList<SemanticRoleMatchData> queryCharactersFiltered) {
		int totalSum = 0;
		for(SemanticRoleMatchData character : queryCharactersFiltered){
			character.setWeight();
			totalSum = totalSum + character.getWeight();
		}
		
		return totalSum;
	}



	private static int getCharacterSimilarity( SemanticRoleMatchData queryCharacter, SemanticRoleMatchData movieCharacter) {
		// TODO Auto-generated method stub
		int matchCount;
		String queryCharacterName = queryCharacter.getNoun();
		String movieCharacterName = movieCharacter.getNoun();
		if(queryCharacterName.equalsIgnoreCase(movieCharacterName)){
			matchCount = queryCharacter.weight;
		}
		else{
			queryCharacter.getRecepients();
			//int traitScoreByStringMatch = calScore(queryCharacter.getTraits(), movieCharacter.getTraits());
			int traitScoreBySemanticMatch = calScoreBySemanticMeaning(queryCharacter.getTraits(), movieCharacter.getTraits(),"n");
			
			//int srlScoreScoreByStringMatch = calScore(queryCharacter.getSemanticRoles(), movieCharacter.getSemanticRoles());
			int srlScoreScoreBySemanticMatch = calScoreBySemanticMeaning(queryCharacter.getSemanticRoles(), movieCharacter.getSemanticRoles(),"a");
			
			//int recipientScoreByStringMatch = calScore(queryCharacter.getRecepients(), movieCharacter.getRecepients());
			int recipientScoreBySemanticMatch = calScoreBySemanticMeaning(queryCharacter.getRecepients(), movieCharacter.getRecepients(),"n");
			queryCharacter.getVerbs().removeAll(auxillaryVerbs);
			movieCharacter.getVerbs().removeAll(auxillaryVerbs);
			//int verbScoreByStringMatch = calScore(queryCharacter.getVerbs(), movieCharacter.getVerbs());
			int verbScoreBySemanticMatch = calScoreBySemanticMeaning(queryCharacter.getVerbs(), movieCharacter.getVerbs(),"v");
			
			//int traitScore = Math.max(traitScoreBySemanticMatch,traitScoreByStringMatch);
			//int srlScore = Math.max(srlScoreScoreBySemanticMatch, srlScoreScoreByStringMatch);
			//int recipientScore = Math.max(recipientScoreBySemanticMatch, recipientScoreByStringMatch);
			//int verbScore = Math.max(verbScoreBySemanticMatch, verbScoreByStringMatch);
			
			//matchCount = traitScore + srlScore + recipientScore + verbScore;
			matchCount = traitScoreBySemanticMatch + srlScoreScoreBySemanticMatch + recipientScoreBySemanticMatch + verbScoreBySemanticMatch;
		}
		return matchCount;
	}


	private static Map<String, ArrayList<SemanticRoleMatchData>> getAllMoviesCharactersMap() throws IOException, JSONException {
		// TODO Auto-generated method stub
		

		Map<String, ArrayList<SemanticRoleMatchData>> allMovieCharacters = new HashedMap<String, ArrayList<SemanticRoleMatchData>>();
		//Get All filenames
		ArrayList<String> allFilesName = HelperClass.getAllFileNamesInFolder("Data//SemanticRolesJSONFiles//");
		for(String fileName : allFilesName){
			System.out.println(fileName);
		}
		
		//Iterate over all movies 
		for(String fileName: allFilesName){
			ArrayList<SemanticRoleMatchData> characters = new ArrayList<SemanticRoleMatchData>();
			String movieJSONString = HelperClass.getDataInFile("Data//SemanticRolesJSONFiles//" + fileName);
			JSONObject movieJSON = new JSONObject(movieJSONString);
			String movieName = movieJSON.getString("movieName");
			JSONArray movieCharactersArray = movieJSON.getJSONArray("characters");
			//Iterate over all character of the movie
			for(int i =0 ; i<movieCharactersArray.length() ; i++){
				JSONObject characterJSON = movieCharactersArray.getJSONObject(i);
				SemanticRoleMatchData character = new SemanticRoleMatchData();
				character.setNoun(characterJSON.getString(characterName));
				character.setRecepients(getArrayFromJSON(characterJSON.getJSONArray(recipients)));
				character.setVerbs(getArrayFromJSON(characterJSON.getJSONArray(verbs)));
				character.setTraits(getArrayFromJSON(characterJSON.getJSONArray(traits)));
				character.setSemanticRoles(getArrayFromJSON(characterJSON.getJSONArray(semanticRoles)));
				characters.add(character);

			}
			allMovieCharacters.put(movieName,characters);
		}
		

		return allMovieCharacters;
	}

	private static ArrayList<String> getArrayFromJSON(JSONArray jsonArray) throws JSONException {
		ArrayList<String> list = new ArrayList<String>();
		for(int i =0 ; i< jsonArray.length() ; i++ ){
			list.add(jsonArray.getString(i));
		}
		return list;
	}
	
	

	public static int calScore(ArrayList<String> s1, ArrayList<String> s2){
		int count = 0;
		for(int i=0;i<s1.size();i++){
			for(int j=0;j<s2.size();j++){
				if(StringUtils.getJaroWinklerDistance(s1.get(i), s2.get(j)) >= 0.90){
					System.out.println(s1.get(i)+ "  " + s2.get(j));
					count++;
					break;
				}
			}
		}
		return count;
	}
	
	public static int calScoreBySemanticMeaning(ArrayList<String> s1, ArrayList<String> s2,String pos){
		int count = 0;
		for(int i=0;i<s1.size();i++){
			for(int j=0;j<s2.size();j++){
				String first = s1.get(i);
				String second = s2.get(j);
				//List<String> firstLemma = EventSimilarity.StanfordLemmatizer(first);
				//List<String> secondLemma = EventSimilarity.StanfordLemmatizer(second);
				String firstLemma = jaws.getBaseForm(first, pos);
				String secondLemma = jaws.getBaseForm(second,pos);
				double simScore = new Lin(db).calcRelatednessOfWords(firstLemma, secondLemma);
				if(simScore >= 0.90){
					System.out.println(s1.get(i)+ "  " + s2.get(j));
					count++;
					break;
				}
			}
		}
		return count;
	}
	
}
