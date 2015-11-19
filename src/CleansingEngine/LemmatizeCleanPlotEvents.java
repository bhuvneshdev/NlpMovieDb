package CleansingEngine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import SearchingEngine.EventSimilarity;

public class LemmatizeCleanPlotEvents {
	
	public static void lemmatizeCleanPlotEvents(ArrayList<String> eventsFromLinesInPlot, String movieName){
		File folder = new File("Data//EventExtractedCleanPlots");
		int ctr=0;
		for (File fileEntry : folder.listFiles()) {
			//reading events from file and storing in ArrayList
			
			try {
				FileReader fileReader = new FileReader(fileEntry);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					eventsFromLinesInPlot.add(line);
				}
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(!new File("Data//EventExtractedCleanPlotsLemma", fileEntry.getName()).exists()){
				Set<String> setLemmas = new HashSet<String>();
				Path file = Paths.get("Data//EventExtractedCleanPlotsLemma//"+fileEntry.getName());
				Charset charset = Charset.forName("US-ASCII");
				try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
					for(String p: eventsFromLinesInPlot){
							String[] p_tokens_temp = p.split(",");
							String[] p_tokens = p_tokens_temp[1].split("-");
							List<String> l1 = EventSimilarity.StanfordLemmatizer(p_tokens[0]);
							setLemmas.add(l1.get(0));
					}
					for(String p: setLemmas){
						String temp = p+'\n';
					    writer.write(temp, 0, temp.length());
					}
				} catch (IOException x) {
				    System.err.format("IOException: %s%n", x);
				}
			}
			eventsFromLinesInPlot.clear();
		}
	}
}
