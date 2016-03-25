package Indexing; 
import Classes.*;
import PreProcessData.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * This is a simple corpus reader
 * Class for Assignment 2 of INFSCI2140, 2016 Spring.
 */
public class PreProcessedCorpusReader {
	
	public static void main(String [] args) throws IOException{
       	 	PreProcessedCorpusReader text1 = new PreProcessedCorpusReader("trecweb");
		text1.nextDocument();
		System.out.println("----------------------------------------------------------------------------");	
       	 	text1.nextDocument();
		System.out.println("----------------------------------------------------------------------------");	
       	 	text1.nextDocument();
 	}
	private InputStreamReader iSReader;
	private BufferedReader bReader;

	
    /*
     This constructor should open the pre-processed corpus file, Path.ResultHM1 + type
     which was generated in assignment 1
     remember to close the file that you opened, when you do not use it any more
     */
	public PreProcessedCorpusReader(String type) throws IOException {
		String docType = type;
		String file = Path.ResultHM1 + docType;
		
        	// new input stream reader is created
        	FileInputStream fis = new FileInputStream(file);
       		iSReader = new InputStreamReader(fis);
		bReader = new BufferedReader(iSReader);	
	
	}
	

	/*
     read a line for docNo, then read another line for the word list
     put them in a map, and return it
    */
	public Map<String, String> nextDocument() throws IOException {
		Map<String, String> doc = new HashMap<String, String>();
		String lineKey = bReader.readLine();
		String lineValue = bReader.readLine();	
		doc.put(lineKey,lineValue);	
		//System.out.println(doc.keySet());
		//System.out.println(doc.values());
		return doc;
	}

}
