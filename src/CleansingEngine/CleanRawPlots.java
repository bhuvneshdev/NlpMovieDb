
package CleansingEngine;
import java.util.*;
import java.io.*;

public class CleanRawPlots {
	
	


public static void cleanPlots() throws IOException {

	File IBFolder = new File("Data//RawPlots");
    String fileName;
    for (File file : IBFolder.listFiles()) {
        fileName = file.getName();
        System.out.println(fileName);
        cleanSinglePlot(fileName);

    }
}

public static void cleanSinglePlot(String fileName) throws IOException{
	

    BufferedReader br=new BufferedReader(new FileReader("Data//RawPlots//" + fileName));
    FileWriter fw=new FileWriter("Data//RawPlotsClean//"+ fileName,true);
      //File inputFolder = new File("Data//RawPlots");
      //traverse(inputfolder, )
    String line="";

    boolean in = false;
    String a="";
    String inp="";
    while((line=br.readLine())!=null)
        { 
            inp+=line;
        }


    String outp = "";



    for (int i=0; i < inp.length(); ++i)

    {

        if (!in && inp.charAt(i) == '<' || inp.charAt(i) == '(')

            {

                in = true;

                continue;

            }

            if (in && inp.charAt(i) == '>' || inp.charAt(i) == ')')

            {

                in = false;

                continue;

            }
            

            if (!in)

            {

                outp = outp + inp.charAt(i);

            }
            

    }  
     for (int i=0; i < outp.length(); i++)
     {
        if(outp.charAt(i)=='.' && i!=0 && outp.charAt(i-1) == '.')
            {
                
                    outp=outp.substring(0,i-1)+outp.substring(i+1);
                    i--;
                
            }
     } 

    String text = outp.replaceAll("[^a-zA-Z0-9 .,']", "");
    //text=text.replace("..*"," ");
    //String text1 = text.replaceAll("\\([^\\(]*\\)", "");

    System.out.println(text);
    fw.write(text);
    fw.close();

 

    }
	
}




