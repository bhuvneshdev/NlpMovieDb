package helperClass;

import java.io.File;
import java.util.ArrayList;

public class HelperClass {
	
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
	
	public static String getDataInFile(String filePath){
		String data ="";
		
		return data;
	}
	
	public static String getDataInFile(String folderPath,String filePath){
		String data = "";
		
		return data;
	}

}
