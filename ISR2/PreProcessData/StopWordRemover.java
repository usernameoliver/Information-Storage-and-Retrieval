package PreProcessData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import Classes.Path;

/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 *
 * StopWordRemover is a class takes charge of judging whether a given word
 * is a stopword by calling the method <i>isStopword(word)</i>.
 */
public class StopWordRemover {
	//you can add essential private methods or variables
	public static void main(String[] args) throws IOException{
		StopWordRemover m = new StopWordRemover();
		char [] a = {'a','b'};
		System.out.println(m.isStopword(a));
	}
	private Map<String, Integer> dictionary = new HashMap<String, Integer>();
	private FileInputStream fis = null;
	public StopWordRemover( ) throws IOException {
		// load and store the stop words from the fileinputstream with appropriate data structure
		// that you believe is suitable for matching stop words.
		// address of stopword.txt should be Path.StopwordDir
		Path textPath = new Path();
	    String inputPath = textPath.StopwordDir;
	    fis = new FileInputStream(inputPath);
	    BufferedReader reader = null;  
	    reader = new BufferedReader(new InputStreamReader(fis));
	    String s = reader.readLine();
	    while(s != null){
	    	s = reader.readLine();
	    	dictionary.put(s, 1);
	    }
	    //System.out.println(dictionary.entrySet());
	    

	}
	
	// YOU MUST IMPLEMENT THIS METHOD
	public boolean isStopword( char[] word ) {
		// return true if the input word is a stopword, or false if not
		String wordCheck = word.toString();
		return dictionary.containsKey(wordCheck);
	}
}
