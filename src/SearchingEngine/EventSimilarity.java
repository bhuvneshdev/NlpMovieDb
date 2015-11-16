package SearchingEngine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

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
		/*
		//available options of metrics
		private static RelatednessCalculator[] rcs = { new HirstStOnge(db),
				new LeacockChodorow(db), new Lesk(db), new WuPalmer(db),
				new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db) };
		*/
		public static void eventSimilarity(ArrayList<String> eventsFromLinesInPlot,ArrayList<String> eventsFromLinesInQuery){
				
			eventsFromLinesInQuery.add("<NULL>,cutting-1,<NULL>");
			eventsFromLinesInQuery.add("<NULL>,killing-1,<NULL>");
			for(String q: eventsFromLinesInQuery){
					String[] q_tokens_temp = q.split(",");
					String[] q_tokens = q_tokens_temp[1].split("-");
//					String[] q_tokens_temp= eventsFromLinesInQuery.get(2).split(",");
//					String[] q_tokens = q_tokens_temp[1].split("-");
					for(String p: eventsFromLinesInPlot){
						String[] p_tokens_temp = p.split(",");
						String[] p_tokens = p_tokens_temp[1].split("-");
						List<String> l1 = StanfordLemmatizer(q_tokens[0]);
						List<String> l2 = StanfordLemmatizer(p_tokens[0]);
						WS4JConfiguration.getInstance().setMFS(true);
						double s_path = new Path(db).calcRelatednessOfWords(l1.get(0), l2.get(0));
						double s_lin = new Lin(db).calcRelatednessOfWords(l1.get(0), l2.get(0));
						if(s_path==1.7976931348623157E308) s_path = 1.5;
						if(s_lin==1.7976931348623157E308) s_lin = 1.5;
						if(s_path>.4 || s_lin>.4) System.out.println("Implement the ranking of plots!!");
//							System.out.println("Similarity Score PATH:"+"("+q_tokens[0]+","+l1.get(0)+")" + " " +"("+p_tokens[0]+","+l2.get(0)+")"+"-"+s_path);
//						System.out.println("Similarity Score LIN:"+"("+q_tokens[0]+","+l1.get(0)+")" + " " +"("+p_tokens[0]+","+l2.get(0)+")"+"-"+s_lin);
					}
				}
//				double temp = new Lin(db).calcRelatednessOfWords("cutting", "killing");
		}
		
		private static List<String> StanfordLemmatizer(String string) {
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
