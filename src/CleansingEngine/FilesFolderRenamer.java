package CleansingEngine;

import java.io.File;

public class FilesFolderRenamer {

	public static void removeWhiteSpace () {
	    // For clarification:
	    File IBFolder = new File("Data//RawPlots");
	    String oldName;
	    String newName;
	    for (File old : IBFolder.listFiles()) {
	        oldName = old.getName();
	        //if (!oldName.contains(" ")) continue;
	        //newName = oldName + ".txt";
	        newName = oldName.replaceAll(".txt", "");
	        // or the following code should work, not sure which is more efficient
	        // newName = oldName.replaceAll(" ", "");

	        old.renameTo(new File(IBFolder + "/" + newName+".txt"));
	    }
	}
}
