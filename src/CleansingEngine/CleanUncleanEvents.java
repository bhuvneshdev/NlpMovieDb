package CleansingEngine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CleanUncleanEvents {
	
	public static void cleanUncleanEvents(){
		File folder_input = new File("Data//EventExtractedUncleanPlots");
		for (File fileEntry : folder_input.listFiles()) {
			ArrayList<String> eventsFromLinesInPlot = new ArrayList<String>();
			try {
				
				FileReader fileReader = new FileReader(fileEntry);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					String[] tokens_temp = line.split(",");
					String[] tokens = tokens_temp[1].split("-");
					if(!tokens[0].equalsIgnoreCase("am") && !tokens[0].equalsIgnoreCase("are") && !tokens[0].equalsIgnoreCase("is") && !tokens[0].equalsIgnoreCase("was") && !tokens[0].equalsIgnoreCase("were") && !tokens[0].equalsIgnoreCase("being") && !tokens[0].equalsIgnoreCase("can") && !tokens[0].equalsIgnoreCase("could") && !tokens[0].equalsIgnoreCase("do") && !tokens[0].equalsIgnoreCase("did") && !tokens[0].equalsIgnoreCase("does") && !tokens[0].equalsIgnoreCase("doing") && !tokens[0].equalsIgnoreCase("be") && !tokens[0].equalsIgnoreCase("am") && !tokens[0].equalsIgnoreCase("have") && !tokens[0].equalsIgnoreCase("has") && !tokens[0].equalsIgnoreCase("had") && !tokens[0].equalsIgnoreCase("having") && !tokens[0].equalsIgnoreCase("may") && !tokens[0].equalsIgnoreCase("might") && !tokens[0].equalsIgnoreCase("must") && !tokens[0].equalsIgnoreCase("shall") && !tokens[0].equalsIgnoreCase("should") && !tokens[0].equalsIgnoreCase("will") && !tokens[0].equalsIgnoreCase("would") && !tokens[0].equalsIgnoreCase("'s"))
						eventsFromLinesInPlot.add(line);
				}
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(!new File("Data//EventExtractedCleanPlots", fileEntry.getName()).exists()){
				Path file = Paths.get("Data//EventExtractedCleanPlots//"+fileEntry.getName());
				Charset charset = Charset.forName("US-ASCII");
				try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
					for(String e: eventsFromLinesInPlot){
							String temp = e+'\n';
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
