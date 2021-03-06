package SearchingEngine;

import helperClass.HelperClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;

import database.EventSimilarityMatchData;

import EventExtraction.EventExtraction;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

	public class EventSimilarity {
		private static ILexicalDatabase db = new NictWordNet();
		public static ArrayList<EventSimilarityMatchData> ESTable = new ArrayList<EventSimilarityMatchData>();
		/*
		//available options of metrics
		private static RelatednessCalculator[] rcs = { new HirstStOnge(db),
				new LeacockChodorow(db), new Lesk(db), new WuPalmer(db),
				new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db) };
		*/
		
		public static ArrayList<EventSimilarityMatchData> getEventSimilarityTable(String str_query, float threshold){
			
			
			str_query = Jsoup.parse(str_query).text();
			String[] linesInQuery = str_query.split("\\.");
			ArrayList<String> eventsFromLinesInQuery = EventExtraction.eventExtractionEngine(linesInQuery);
			ArrayList<String> eventsFromLinesInQueryLemma = new ArrayList<String>();
			List<String> l1 = null;
			Set<String> qLemma= new HashSet<String>();
			for(String q: eventsFromLinesInQuery){
				String[] q_tokens_temp = q.split(",");
				String[] q_tokens = q_tokens_temp[1].split("-");
				l1 = StanfordLemmatizer(q_tokens[0]);
				qLemma.add(l1.get(0));
			}
			for(String q : qLemma){
				eventsFromLinesInQueryLemma.add(q);
			}
			
			ArrayList<String> eventsFromLinesInPlotLemma = new ArrayList<String>();
			File folder = new File("Data//EventExtractedCleanPlotsLemma");
			for (File fileEntry : folder.listFiles()) {
				//reading events from file and storing in ArrayList
				
				try {
					FileReader fileReader = new FileReader(fileEntry);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						eventsFromLinesInPlotLemma.add(line);
					}
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				eventSimilarity(eventsFromLinesInPlotLemma, eventsFromLinesInQueryLemma,fileEntry.getName(),threshold);
				eventsFromLinesInPlotLemma.clear();
			}
			return ESTable;
		}
		
		public static void eventSimilarity(ArrayList<String> eventsFromLinesInPlotLemma,ArrayList<String> eventsFromLinesInQueryLemma, String movieName, float threshold){
			System.out.println(movieName);
			ArrayList<String> q_EventSimilarityMatches = new ArrayList<String>();
			ArrayList<String> p_EventSimilarityMatches = new ArrayList<String>();
			EventSimilarityMatchData object;
			Set<String> eventsFromLinesInPlotLemmaSet = new HashSet<String>();
			for(String temp: eventsFromLinesInPlotLemma) eventsFromLinesInPlotLemmaSet.add(temp);
			
			//variable declarations
			double max,eventMax;;
			String plot = new String();
			String query = new String();
			boolean flag;
			int events_match_count=0;
			double avg_event_match = 0;
			double score=0;
			int eventsDeletedFromQuery = 0;
			
			//loop over each query event
			for(String q: eventsFromLinesInQueryLemma){
					max = 0;     // reset for each new query
					flag = false;   // so far the query has'nt hit any plot event
					
					if(!q.equalsIgnoreCase("am") && !q.equalsIgnoreCase("are") && !q.equalsIgnoreCase("is") && !q.equalsIgnoreCase("was") && !q.equalsIgnoreCase("were") && !q.equalsIgnoreCase("being") && !q.equalsIgnoreCase("can") && !q.equalsIgnoreCase("could") && !q.equalsIgnoreCase("do") && !q.equalsIgnoreCase("did") && !q.equalsIgnoreCase("does") && !q.equalsIgnoreCase("doing") && !q.equalsIgnoreCase("be") && !q.equalsIgnoreCase("am") && !q.equalsIgnoreCase("have") && !q.equalsIgnoreCase("has") && !q.equalsIgnoreCase("had") && !q.equalsIgnoreCase("having") && !q.equalsIgnoreCase("may") && !q.equalsIgnoreCase("might") && !q.equalsIgnoreCase("must") && !q.equalsIgnoreCase("shall") && !q.equalsIgnoreCase("should") && !q.equalsIgnoreCase("will") && !q.equalsIgnoreCase("would") && !q.equalsIgnoreCase("'s")){
						if(!eventsFromLinesInPlotLemmaSet.contains(q)){
							//loop over all plot events
							for(String p: eventsFromLinesInPlotLemma){
								
								//similarity score calculation: Lin and path
								WS4JConfiguration.getInstance().setMFS(true);
								double s_path = new Path(db).calcRelatednessOfWords(q, p);
								double s_lin = new Lin(db).calcRelatednessOfWords(q, p);
								if(s_path==1.7976931348623157E308) s_path = 1.5;
								if(s_lin==1.7976931348623157E308) s_lin = 1.5;
								
								//thresholding and maximum hit so far
								if((s_path + s_lin)/2 > 0.4 && (s_path + s_lin)/2>max){
									flag = true; 
									max = Math.max(s_path,s_lin);
									double idf = 0;
									File folder = new File("Data//EventExtractedCleanPlotsLemmaTfidf");
									for (File fileEntry : folder.listFiles()) {
										if(fileEntry.getName().equalsIgnoreCase(movieName)){
											JSONParser parser = new JSONParser();
											try {
												
										            Object obj = parser.parse(new FileReader(
										                    "Data//EventExtractedCleanPlotsLemmaTfidf//"+movieName));
										 
										            JSONObject jsonObject = (JSONObject) obj;
										            JSONObject childJsonObject = (JSONObject) jsonObject.get(p);
										            double tf,tfidf;
	//									            tf = (double) childJsonObject.get("tf");
										            idf = (double) childJsonObject.get("idf");
	//									            tfidf = (double) childJsonObject.get("tfidf");
	//									            double w = (double) jsonObject.get(p);
										            
										            System.out.println("Weight of "+p+" IDF: "+idf);
										            break;
												
										        } catch (Exception e) {
										            e.printStackTrace();
										        }
										} // if end
									} // for ifidf end
									
									query = q;
									plot = p;	
									
								}
							} // inner for end
							eventMax = max;
							if(flag){
								System.out.println("Match : "+query+","+plot +","+eventMax);
								events_match_count++;
								avg_event_match= avg_event_match + eventMax;
								q_EventSimilarityMatches.add(query);
								p_EventSimilarityMatches.add(plot);
							}
						}
						else{
							eventMax = 1.5;
							flag = true;
							System.out.println("Match : "+q+","+q +","+eventMax);
							events_match_count++;
							avg_event_match= avg_event_match + eventMax;
							q_EventSimilarityMatches.add(q);
							p_EventSimilarityMatches.add(q);
						}
					}
					else eventsDeletedFromQuery++;
				} // outer for end
			
			avg_event_match = avg_event_match/(eventsFromLinesInQueryLemma.size()-eventsDeletedFromQuery);
			score = ((events_match_count*100/(eventsFromLinesInQueryLemma.size()-eventsDeletedFromQuery)) + (avg_event_match*100/1.5))/2;
			System.out.println("Score: "+score);
			System.out.println("-----------------------");
			if(events_match_count>=(eventsFromLinesInQueryLemma.size()-eventsDeletedFromQuery)/2 && avg_event_match>=threshold){
				object = new EventSimilarityMatchData(movieName, events_match_count, eventsFromLinesInQueryLemma.size()-eventsDeletedFromQuery, threshold,score, q_EventSimilarityMatches, p_EventSimilarityMatches);
				ESTable.add(object);
			}
		}
		
		public static List<String> StanfordLemmatizer(String string) {
			// TODO Auto-generated method stub
			Properties props;
	        props = new Properties();
	        props.put("annotators", "tokenize, ssplit, pos, lemma");
	        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	        
	        
	        List<String> lemmas = new LinkedList<String>();
	        // create an empty Annotation just with the given text
	        Annotation document = new Annotation(string);

	        // run all Annotators on this text
	        pipeline.annotate(document);

	        // Iterate over all of the sentences found
	        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	        for(CoreMap sentence: sentences) {
	            // Iterate over all tokens in a sentence
//	        	System.out.println(sentence.toString());
	            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
	                // Retrieve and add the lemma for each word into the list of lemmas
	                lemmas.add(token.get(LemmaAnnotation.class));
//	                System.out.println(token.get(LemmaAnnotation.class));
	            }
	        }

	        return lemmas;
		}
		
		
	}
