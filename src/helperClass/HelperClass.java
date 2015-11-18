package helperClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

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

}
