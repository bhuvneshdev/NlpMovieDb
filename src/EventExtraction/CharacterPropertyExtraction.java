package EventExtraction;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helperClass.HelperClass;

public class CharacterPropertyExtraction implements Comparator<String>{

	public static void setCharacterPropertyExtraction()  throws IOException, ClassCastException, ClassNotFoundException {
		
		//String plotString = HelperClass.getDataInFile("Data//CoreferencePlots//", "Forrest.txt");
		//System.out.println(plotString);
		ArrayList<String> allMovieNames = HelperClass.getAllFileNamesInFolder("Data//RawPlotsClean");
		
		String q = "In contrast, ViruS model student is Chaturramalingam or Silencer";
		Set<String> characterNames = NamedEntityRecognition.getAllCharacterNames(q);
		System.out.println(characterNames.toString());
		
		
		for(String movieName: allMovieNames){
			System.out.println(movieName);
			String plotString = HelperClass.getDataInFile("Data//RawPlotsClean//" + movieName );
			String oldPlotString = plotString;
			Set<String> characterNamesSe = NamedEntityRecognition.getAllCharacterNames(plotString);
			System.out.println(plotString);
			System.out.println(characterNamesSe.toString());
			for(String characterName : characterNamesSe){
				if(characterName.split(" ").length>1){
					String replacingString = characterName.replaceAll(" ", "");
					replacingString = Character.toString(replacingString.charAt(0)).toUpperCase()+replacingString.substring(1);
					plotString = plotString.replaceAll("(?i) "+characterName," " +replacingString);
				}
			}
			System.out.println(plotString);
			
			
			
			Set<String> characterNamesSet = NamedEntityRecognition.getAllCharacterNames(plotString);
			System.out.println(characterNamesSet.toString());
			ArrayList<String> characterNamesArray = HelperClass.getArrayListFromString(characterNamesSet);
			String[] data = new String[characterNamesArray.size()];
			data = characterNamesArray.toArray(data);
			Arrays.sort(data, new CharacterPropertyExtraction());
	        System.out.println(Arrays.toString(data));
			for(int i =0 ; i<data.length; i++){
				String firstCharacter = data[i];
				for(int j =0 ; j<data.length ; j++ ){
					String secondCharacter = data[j];
					if(i!=j){
						if(firstCharacter.contains(secondCharacter)){
							String toReplace = secondCharacter;
							String replacingName = firstCharacter.replace(" ","");
							replacingName = Character.toString(replacingName.charAt(0)).toUpperCase()+replacingName.substring(1);
							toReplace = Character.toString(toReplace.charAt(0)).toUpperCase()+toReplace.substring(1);	 
							System.out.println(toReplace + " : " + replacingName  );
							if(plotString.contains(toReplace+" "))
								plotString = plotString.replaceAll(toReplace+" ", replacingName+" ");
							//if(plotString.contains(toReplace+"\\."))
							//	plotString = plotString.replaceAll(toReplace+"\\.", replacingName+"\\.");
							if(plotString.contains(toReplace+"."))
								plotString = plotString.replaceAll(toReplace+".", replacingName+".");
							if(plotString.contains(toReplace+ "\'"))
								plotString = plotString.replaceAll(toReplace+"\'", replacingName+"\'");
							if(plotString.contains(toReplace+ ","))
								plotString = plotString.replaceAll(toReplace+",", replacingName+",");
							System.out.println(plotString.trim());
						}
					}
				}
			}
			
			
			if(plotString.length() ==0 ){
				System.out.println("0");
				System.out.println(oldPlotString);
				FileWriter file = new FileWriter("Data//NameUnifiedPlots//"+ movieName);
				file.write(plotString.trim());
				file.flush();
				file.close();
			}
			else{
				
				FileWriter file = new FileWriter("Data//NameUnifiedPlots//"+ movieName);
				file.write(plotString.trim());
				file.flush();
				file.close();
			}
			Set<String> characterName = NamedEntityRecognition.getAllCharacterNames(plotString);
			System.out.println(characterName.toString());
			System.out.println(characterNamesSet.size() - characterName.size());
			
			System.out.println("===========================");
		}
		
		/*
		for(String movieName : allMovieNames){
			String plotString = HelperClass.getDataInFile("Data//RawPlotsClean//" + movieName );
			//System.out.println(plotString);
			//String[] allCharacters = plotString.split("\n");
			
			Set<String> characterNamesSet = NamedEntityRecognition.getAllCharacterNames(plotString);
			System.out.println(characterNamesSet.toString());
			ArrayList<String> characterNamesArray = HelperClass.getArrayListFromString(characterNamesSet);
			String[] data = new String[characterNamesArray.size()];
			data = characterNamesArray.toArray(data);
			Arrays.sort(data, new CharacterPropertyExtraction());
	        System.out.println(Arrays.toString(data));
			for(int i =0 ; i<data.length; i++){
				String firstCharacter = data[i];
				for(int j =0 ; j<data.length ; j++ ){
					String secondCharacter = data[j];
					if(i!=j){
						if(firstCharacter.contains(secondCharacter)){
							String toReplace = secondCharacter;
							String replacingName = firstCharacter.replace(" ","");
							replacingName = Character.toString(replacingName.charAt(0)).toUpperCase()+replacingName.substring(1);
							System.out.println(toReplace + " : " + replacingName  );
							plotString = plotString.replaceAll("(?i)"+toReplace, replacingName);
						}
					}
				}
			}
			System.out.println(plotString);
			System.out.println("===========================");
		}
		*/
	}
	
	public int compare(String x, String y) {
        if (x == null)
            return y==null ? 0 : -1;
        else if (y == null)
            return +1;
        else {
            int lenx = x.length();
            int leny = y.length();
            if (lenx == leny)
                return x.compareTo(y); //break ties?
            else
                return lenx < leny ? -1 : +1;
        }
    }
}
