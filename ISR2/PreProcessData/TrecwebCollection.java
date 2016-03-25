package PreProcessData;
import Classes.Path;

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
public class TrecwebCollection implements DocumentCollection {
 //you can add essential private methods or variables
 public static void main(String [] args) throws IOException{
        
        TrecwebCollection web1 = new TrecwebCollection();
        web1.nextDocument();
        web1.nextDocument();
        
 }
 private String inputPath = Path.DataWebDir;
 private FileInputStream fis = new FileInputStream(inputPath);
 // YOU SHOULD IMPLEMENT THIS METHOD
 public TrecwebCollection() throws IOException {
  // This constructor should open the file in Path.DataWebDir
  // and also should make preparation for function nextDocument()
  // Do not load the whole corpus into memory!!!
     // open  the file
     
     
     
     
 }
 
 // YOU SHOULD IMPLEMENT THIS METHOD
 public Map<String, Object> nextDocument() throws IOException {
  // this method should load one document from the corpus, and return this document's number and content.
  // the returned document should never be returned again.
  // when no document left, return null
  // NTT: remember to close the file that you opened, when you do not use it any more
  //open the file and read
	// read the file
	 Map<String, Object> webInfo = new HashMap<String, Object>();
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
    	ID = line.substring(7,24); 
     }
     
   //acquire content
     
     while(!line.equals("</DOCHDR>")){
    	 line = reader.readLine();
    	 if(line == null) return null;
     }//end while
         
     if(line.equals("</DOCHDR>")){                	 
       	line = reader.readLine();  
       	if(line == null) return null;
        	 
		while( !line.equals("</DOC>")){                  	
         	 s = s + line;
          	 line = reader.readLine(); 
          	 if(line == null) return null;
        }//end while
        //save ID and content into docInfo	
		
        webInfo.put(ID, s.toCharArray());
        /*System.out.println(ID);
        System.out.println(s);*/
     }//end if
     
        line = reader.readLine();  
        if(line == null) return null;
        //System.out.println(webInfo.values());
        return webInfo;
 }
 
}
