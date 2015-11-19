import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import rankingEngine.RankEngine;
import SearchingEngine.EventSimilarity;
import SearchingEngine.NERSimilarity;
import database.EventSimilarityMatchData;
import database.NERMatchData;


public class Input {

	public static void startSearch(String query) throws ClassCastException, ClassNotFoundException, IOException, JSONException{
		
		System.out.println("===========Event Similarity Match =========");
		ArrayList<EventSimilarityMatchData> eventTable = EventSimilarity.getEventSimilarityTable(query,0.82f);
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
		
		if(nerTable != null){
			RankEngine.getRankedList(eventTable, nerTable);
		}
		
	}
}
