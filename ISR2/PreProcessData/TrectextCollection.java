package PreProcessData;
import Classes.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 *
 * Implementation of DocumentCollection
 *
 */
public class TrectextCollection implements DocumentCollection {
	
 //you can add essential private methods or variables
 public static void main(String [] args) throws IOException{
        TrectextCollection text1 = new TrectextCollection();
        text1.nextDocument();
        text1.nextDocument();
        
 }

 
 private String inputPath = Path.DataTextDir;
 private FileInputStream fis = new FileInputStream(inputPath);
 // YOU SHOULD IMPLEMENT THIS METHOD
 public TrectextCollection() throws IOException {
	  
     
 }

 // YOU SHOULD IMPLEMENT THIS METHOD
 public Map<String, Object> nextDocument() throws IOException {
  // this method should load one document from the corpus, and return this document's number and content.
  // the returned document should never be returned again.
  // when no document left, return null
  // NTT: remember to close the file that you opened, when you do not use it any more
  //open the file and read
	// read the file
	 Map<String, Object> docInfo = new HashMap<String, Object>();
	 String ID = "";
	 BufferedReader reader = null;     
     String s = "";
     reader = new BufferedReader(new InputStreamReader(fis));
     
     //Reading File line by line using BufferedReader
     //You can get next line using reader.readLine() method.
     String line = reader.readLine();
     
     if(line == null) return null;
    
     while(!line.equals("<DOC>")){
    	 line = reader.readLine();
    	 if(line == null) return null;
   	 }//end while
    	
	 // acquire ID
     if(line.equals("<DOC>")){
      	line = reader.readLine();
      	if(line == null) return null;
       	ID = line.substring(8,24); 
     }//end if
         
     //System.out.println(ID);
         
     //acquire content
         
     while(!line.equals("<TEXT>")){
    	 line = reader.readLine();
    	 if(line == null) return null;
     }//end while
         
     if(line.equals("<TEXT>")){                	 
       	line = reader.readLine();  
       	if(line == null) return null;
        	 
		while( !line.equals("</TEXT>")){                  	
         	 s = s + line;
          	 line = reader.readLine(); 
          	 if(line == null) return null;
        }//end while
        //save ID and content into docInfo	
		//System.out.println(s);
        docInfo.put(ID, s.toCharArray());
     }//end if
     
        line = reader.readLine();  
        if(line == null) return null;
        //System.out.println(docInfo.values());
        return docInfo;
 }
 
}
