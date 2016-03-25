package Indexing;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
/**
 * Please construct your code efficiently, otherwise, your memory cannot hold the whole corpus...
 *
 * Class for Assignment 2 of INFSCI2140, 2016 Spring.
 */
public class MyIndexWriter {
	private HashMap<String,ArrayList<String>> dictionaryP;
	private HashMap<String,ArrayList<Integer>> dictionaryO ;
	private FileWriter writer;
	private FileWriter writerOccur;

	public static void main(String[] args)throws IOException{
		PreProcessedCorpusReader corpus=new PreProcessedCorpusReader("trecweb");
		MyIndexWriter output = new MyIndexWriter("trecweb");	
		// initiate the output object
		// initiate a doc object, which can hold document number and document content of a document
		Map<String, String> doc = null;
		for(int k = 0; k < 10; k++){
			doc = corpus.nextDocument();
			
			// build index of corpus document by document
			// load document number and content of the document
			String docno = doc.keySet().iterator().next();
			String content = doc.get(docno);
			// index this document
			output.index(docno, content); 
			
			
		}
		output.close();

		
	}
	
	
	
    /**
     This constructor should initiate the FileWriter to output your index files
     remember to close files if you finish writing the index
    */
	public MyIndexWriter(String type) throws IOException {
		String typeToWrite = type;
		File file = new File("/home/hadoop/isr/isrAssignment1/ISR1/data//Postings."+ typeToWrite);
		File fileOccur = new File("/home/hadoop/isr/isrAssignment1/ISR1/data//Occurences."+ typeToWrite);
		file.createNewFile();
		fileOccur.createNewFile();
		writer = new FileWriter(file);
		writerOccur = new FileWriter(fileOccur);
		dictionaryP = new HashMap<String,ArrayList<String>>();
		dictionaryO = new HashMap<String,ArrayList<Integer>>();
		
	}
	
    /**
     you are strongly suggested to build the index by installments
     in your implementation of the index, you should transform your string docnos into non-negative integer docids !!!
     In MyIndexReader, you will need to request the integer docid for docnos.
    */
	public void index(String docno, String content) throws  NullPointerException,IOException {
		
		String[] tokens = content.split("\\s+");
		

		//System.out.println(tokens.length);
		//System.out.println(tokens[0]);
		//System.out.println(content);
		for(int i = 0; i < tokens.length; i++){
			
			String tempKey = tokens[i];
			//if the dictionary has the term, then add the docno to the List aready existing. 
			if(dictionaryP.containsKey(tempKey))
			{
				ArrayList<String> tempListPost = dictionaryP.get(tempKey);
				ArrayList<Integer> tempListOccur = dictionaryO.get(tempKey);
				String currentDocno = tempListPost.get(tempListPost.size() - 1);
				int currentOccur = tempListOccur.get(tempListOccur.size() - 1);
				if(currentDocno.equals(docno)){//if this term occur for the first time in this doc but exsits in other posts,  
					currentOccur++;
					tempListOccur.remove(tempListOccur.size() - 1);
					tempListOccur.add(currentOccur);
					dictionaryO.put(tempKey, tempListOccur);
					
				}
				else{
					tempListOccur.add(1);
					dictionaryO.put(tempKey, tempListOccur);
					tempListPost.add(docno);//add docno to posts 
					dictionaryP.put(tempKey,tempListPost);
				}
				
			}//end if
			else{// if the dictionary does not have the term, create a new List.
				
				ArrayList<String> tempListPost = new ArrayList<String>();
				ArrayList<Integer> tempListOccur = new ArrayList<Integer>();
				int tempOccur = 1;
				tempListOccur.add(tempOccur);
				tempListPost.add(docno);
				dictionaryO.put(tempKey, tempListOccur);
				dictionaryP.put(tempKey,tempListPost);
			}//end else
		}//end for
		//System.out.println(dictionaryP.entrySet());
		//System.out.println("-------------------------------------------------");
		//System.out.println(dictionaryO.entrySet());
	}

	
	/**
	 *   close the index writer, and you should output all the buffered content (if any).
     *   and if you write your index into several files, you need to fuse them here.
	 */
	public void close() throws IOException {
		System.out.println("I am running");


		for(Map.Entry<String, ArrayList<String>> entry : dictionaryP.entrySet()){
			writer.append(entry.getKey());
			writer.append("\n");//mark token
			writer.append(entry.getValue().toString());
			writer.append("\n");//mark posts
		}
		for(Map.Entry<String, ArrayList<Integer>> entry : dictionaryO.entrySet()){
			writerOccur.append(entry.getKey());
			writerOccur.append("\n");//mark token
			writerOccur.append(entry.getValue().toString());
			writerOccur.append("\n");//mark posts
		}
		writer.close();
		writerOccur.close();
	}
	
}
