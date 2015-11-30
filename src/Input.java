import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.json.JSONException;

import rankingEngine.RankEngine;
import EventExtraction.KeywordExtraction;
import SearchingEngine.CharacterSimilarity;
import SearchingEngine.EntitySimilarity;
import SearchingEngine.EventSimilarity;
import SearchingEngine.NERSimilarity;
import database.CharacterMatchData;
import database.EntityMatchData;
import database.EventSimilarityMatchData;
import database.NERMatchData;
import database.RankingData;


public class Input {

	public static void startSearch(String query) throws Exception{
				
		System.out.println("===========Event Similarity Match =========");
		ArrayList<EventSimilarityMatchData> eventTable = EventSimilarity.getEventSimilarityTable(query,0.80f);
		if(eventTable != null){
			System.out.println("Possible Matches : " + eventTable.size());
			for(EventSimilarityMatchData row : eventTable){
				System.out.println(row.toString());
			}
		}
		else{
			System.out.println("No event match found ");
		}
			
		
		
		System.out.println("===========NER Similarity Match =========");
		ArrayList<NERMatchData> nerTable = NERSimilarity.getNERSimilarityTable(query);
		if(nerTable != null){
			System.out.println("Possible Matches : " + nerTable.size());
			for(NERMatchData row : nerTable){
				System.out.println(row.toString());
			}
		}
		else{
			System.out.println("No NER match found ");
		}
		
		
		System.out.println("===========Entity Similarity Match =========");
		Set<String> entityQuerySet = KeywordExtraction.getKeywords(query);
		ArrayList<EntityMatchData> entityTable = EntitySimilarity.getEntitySimilarityTable(query,entityQuerySet);
		if(entityTable != null){
			System.out.println("Possible Matches : " + entityTable.size());
			for(EntityMatchData row : entityTable){
				System.out.println(row.toString());
			}
		}
		else{
			System.out.println("No Entity match found ");
		}
		
		System.out.println("===========Character Similarity Match =========");
		ArrayList<CharacterMatchData> characterTable = CharacterSimilarity.getCharacterSimilarityTable(query) ;
		if(characterTable !=null){
			for(CharacterMatchData movie : characterTable){
				System.out.println(movie.toString());
			}
		}else{
			System.out.println("No character match found ");
		}
		
		
		
		
		
		System.out.println("===========Results After Ranking =========");
		ArrayList<RankingData> rankTable= RankEngine.getRankedList(eventTable, nerTable ,entityTable,characterTable);
		
		for(RankingData row : rankTable){
			System.out.println(row.toString());
		}
		
	}
}
