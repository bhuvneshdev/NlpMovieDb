package EventExtraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class NamedEntityRecognition {
	
	private static Set<String> namedEntities;
	private static final Pattern TAG_REGEX_LOCATION = Pattern.compile("<LOCATION>(.+?)</LOCATION*>");
	private static final Pattern TAG_REGEX_ORGANIZATION = Pattern.compile("<ORGANIZATION>(.+?)</ORGANIZATION*>");
	private static final Pattern TAG_REGEX_PERSON = Pattern.compile("<PERSON>(.+?)</PERSON*>");
	
	private static ArrayList<Pattern> patternList = new ArrayList<Pattern>();
	
	public static Set<String> getNamedEntities(String plot) throws ClassCastException, ClassNotFoundException, IOException{
		
		namedEntities = new HashSet<String>();
		
		String serializedClassifier = "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz";
	    
		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
	    
		String[] plotLines = plot.split("\\.") ;
	    patternList = createPatternArrayList();
	    
	    for(String line : plotLines){
		    System.out.println(classifier.classifyWithInlineXML(line));
		    String classifiedString = classifier.classifyWithInlineXML(line);
		    ArrayList<String> data = (ArrayList<String>) getTagValues(classifiedString,patternList);
		    for(String str : data){
		    	namedEntities.add(str);
		    }
	    }
	    		
		return namedEntities;
	}
	

	private static ArrayList<Pattern> createPatternArrayList() {
		// TODO Auto-generated method stub
		ArrayList<Pattern> patternList = new ArrayList<Pattern>();
		patternList.add(TAG_REGEX_LOCATION);
		patternList.add(TAG_REGEX_ORGANIZATION);
		patternList.add(TAG_REGEX_PERSON);
		return patternList;
	}


	private static List<String> getTagValues(final String str, ArrayList<Pattern> patterns) {
		final List<String> tagValues = new ArrayList<String>();
		for(Pattern pattern : patterns){
			final Matcher matcher = pattern.matcher(str);
			while (matcher.find()) {
				tagValues.add(matcher.group(1));
			}
		}
	    return tagValues;
	}

}
