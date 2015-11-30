package helperClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import database.SemanticRoleMatchData;

public class HelperClass {
	
	
	/*Usage
	ArrayList<String> allFilesName = HelperClass.getAllFileNamesInFolder("Data//CoreferencedPlots//");
	for(String fileName : allFilesName){
		System.out.println(fileName);
	}
	*/
	public static ArrayList<String> getAllFileNamesInFolder(String folderPath){
		ArrayList<String> fileList = new ArrayList<String>();
		File IBFolder = new File(folderPath);
	    String fileName;
	    for (File file : IBFolder.listFiles()) {
	        fileName = file.getName();
	        fileList.add(fileName);
	    }
	    return fileList;
	}
	
	
	/* Usage
	System.out.println(HelperClass.getDataInFile("Data//CoreferencedPlots//ApocalypseNow(1979)-Synopsis.txt" ));
	*/
	public static String getDataInFile(String filePath) throws IOException{
		String data ="";
		FileInputStream inputStream = new FileInputStream(filePath);			
		try {
		    try {
				data = IOUtils.toString(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
		   inputStream.close();
		}
		return data;
	}
	
	/*Usage
	System.out.println(HelperClass.getDataInFile("Data//CoreferencedPlots//" , "ApocalypseNow(1979)-Synopsis.txt" ));
	*/
	public static String getDataInFile(String folderPath,String filePath) throws FileNotFoundException{
		String data = "";
		FileInputStream inputStream = new FileInputStream(folderPath + filePath);			
		try {
		    try {
				data = IOUtils.toString(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
		   try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return data;
	}
	
	public static ArrayList<String> getArrayListFromString(Set<String> set){
		ArrayList<String> arrayList = new ArrayList<String>();
		for(String str: set){
			arrayList.add(str);
		}
		return arrayList;
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
	
	public static void topTenCharactersMap(Map<String, ArrayList<SemanticRoleMatchData>> arg){
		for(Entry<String, ArrayList<SemanticRoleMatchData>> entry: arg.entrySet()){
			ArrayList<SemanticRoleMatchData> characterArray = entry.getValue();
			
		}
	}

	
}
