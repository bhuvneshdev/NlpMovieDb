package helperClass;

import java.util.ArrayList;

import database.SemanticRoleMatchData;

public class Characters {

	
	public static ArrayList<SemanticRoleMatchData> filterCharacters(ArrayList<SemanticRoleMatchData> allCharacters){
		ArrayList<SemanticRoleMatchData> filteredCharacters = new ArrayList<SemanticRoleMatchData>();
		
		for(SemanticRoleMatchData character : allCharacters){
			int score = character.getRecepients().size() + character.getRelatedNouns().size() + 
					character.getSemanticRoles().size() + character.getTraits().size() + character.getVerbs().size();
			if(score >1){
				filteredCharacters.add(character);
			}
		}
		
		return filteredCharacters;
		
	}
	
}
