package Indexing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *  Class for Assignment 2 of INFSCI2140, 2016 Spring.
 */
public class MyIndexReader {
	//you are suggested to write efficient code here, otherwise, your memory cannot hold the whole corpus...

	private InputStreamReader iSReader;
	private BufferedReader bReader;
	private InputStreamReader iSReaderOccur;
	private BufferedReader bReaderOccur;
	private HashMap<String,ArrayList<String>> dictionaryP;
	private HashMap<String,ArrayList<Integer>> dictionaryO ;


	public static void main(String[] args)throws IOException{
		MyIndexReader ixreader = new MyIndexReader("trecweb");
		String token = "author";
		int df = ixreader.DocFreq(token);
		long ctf = ixreader.CollectionFreq(token);
		System.out.println(" >> the token \""+token+"\" appeared in "+df+" documents and "+ctf+" times in total");
		int[][] posting = ixreader.getPostingList(token);
			for(int ix=0;ix<posting.length;ix++){
				int docid = posting[ix][0];
				int freq = posting[ix][1];
				String docno = ixreader.getDocno(docid);
				System.out.printf("    %20s    %6d    %6d\n", docno, docid, freq);
			}
		/*//test getDocid
		String testString ="lists-000-0048154";
		int result = ixreader.getDocid(testString);
		System.out.println(result + "");*/
		//test getDocno
		int input = 48154;
		String result = ixreader.getDocno(input);
		System.out.println(result);

	}
	public MyIndexReader( String type ) throws IOException {

		//read the index files you generated in task 1
		//remember to close reader when you finish using them
		//use appropriate structure to maintain the index
		dictionaryP = new HashMap<String,ArrayList<String>>();
		dictionaryO = new HashMap<String,ArrayList<Integer>>();
		String docType = type;
		String file = "/home/hadoop/isr/isrAssignment1/ISR1/data//Postings." + docType;
		String fileOccur = "/home/hadoop/isr/isrAssignment1/ISR1/data//Occurences." + docType;
		
        	// new input stream reader is created
        FileInputStream fis = new FileInputStream(file);
        FileInputStream fisOccur = new FileInputStream(fileOccur);
        iSReader = new InputStreamReader(fis);
        iSReaderOccur = new InputStreamReader(fisOccur);
		bReader = new BufferedReader(iSReader);	
		bReaderOccur = new BufferedReader(iSReaderOccur);	

		//Read Occurences.type
		String lineKeyOccur = bReaderOccur.readLine();
		String lineValueOccur = bReaderOccur.readLine();	
		while(lineKeyOccur != null && lineValueOccur != null){
			String term = lineKeyOccur;
			ArrayList<Integer> termOccur = new ArrayList<Integer>();
			String OccurString = lineValueOccur.substring(1,lineValueOccur.length()-1);
			String[] OccurStringArray = OccurString.split(", ");//parse the String
			int[] OccurencesIntegerArray = new int[OccurStringArray.length];
			for (int i = 0; i < OccurStringArray.length ; i++ ) {
				 OccurencesIntegerArray[i] = Integer.parseInt(OccurStringArray[i]);//Convert from String to Integer
				termOccur.add(OccurencesIntegerArray[i]);
			}
			dictionaryO.put(term,termOccur);//put the term and its occurences into the dictionaryO.

			lineKeyOccur = bReaderOccur.readLine();//Continue reading the next line.
			lineValueOccur = bReaderOccur.readLine();	
		}
		fisOccur.close();

		//Read Postings.type
		String lineKey = bReader.readLine();
		String lineValue = bReader.readLine();	

		//System.out.println(lineKey);
		while(lineKey != null && lineValue != null){
			String term = lineKey;
			ArrayList<String> termPosting = new ArrayList<String>();
			String PostingString = lineValue.substring(1,lineValue.length());
			
			String[] PostingArray = PostingString.split(", ");
			for (int i = 0; i < PostingArray.length ; i++ ) {
				termPosting.add(PostingArray[i]);
			}
			dictionaryP.put(term,termPosting);

			lineKey = bReader.readLine();
			lineValue = bReader.readLine();	
		}

		fis.close();
		//System.out.println(dictionaryO.entrySet());
		
		
	}
	
	//get the non-negative integer dociId for the requested docNo
	//If the requested docno does not exist in the index, return -1
	public int getDocid( String docno ) {
		String numberString = docno.substring(docno.length()-7,docno.length()-1);
		int i = Integer.parseInt(numberString);
		return i;
	}

	// Retrieve the docno given the integer docid
	public String getDocno( int docid ) {
		String result = "lists-000-" + docid;
		return result;
	}
	
	/**
	 * Get the posting list for the requested token.
	 * 
	 * The posting list records 1.the documents' docids which contain given token and 2.corresponding frequencies of the term, such as:
	 *  
	 *  [docid]		[freq]
	 *  1			3
	 *  5			7
	 *  9			11
	 *  13			19
	 * 
	 * ...
	 * 
	 * In the returned 2-dimension array, the first dimension refers to each document, and the second dimension records the docid and frequency.
	 * 
	 * For example:
	 * array[0][0] records the docid of the first document the token appears.
	 * array[0][1] records the frequency of the token in the documents with docid = array[0][0]
	 * ...
	 * 
	 * NOTE that the returned posting list should be ranked by docid from the smallest to the largest.
	 * 
	 * @param token
	 */
	public int[][] getPostingList( String token ) throws IOException {

		ArrayList<String> postingArrayList = dictionaryP.get(token);
		ArrayList<Integer> occurArrayList =dictionaryO.get(token);
		int[][] result = new int[postingArrayList.size()][2];

		for (int row = 0; row < postingArrayList.size() ; row++ ) {
			//convert docno to docid;
			String docno = postingArrayList.get(row);
			String numberString = docno.substring(docno.length()-7,docno.length()-1);
			int docid = Integer.parseInt(numberString);

			result[row][0] = docid;
			result[row][1] = occurArrayList.get(row);
		}
		return result;
	}

	// Return the number of documents that contain the given token.
	public int DocFreq( String token ) throws IOException {
		int result = 0;
		ArrayList<String> postingArrayList = dictionaryP.get(token);
		result = postingArrayList.size() ;
		return result;
	}
	
	// Return the total number of times the token appears in the collection.
	public long CollectionFreq( String token ) throws IOException {
		long result = 0;
		ArrayList<Integer> occurArrayList = dictionaryO.get(token);
		for (int i = 0; i < occurArrayList.size() ; i++ ) {
			result = result + occurArrayList.get(i);
		}
		return result;
	}
	
	public void close() throws IOException {

	}
	
}