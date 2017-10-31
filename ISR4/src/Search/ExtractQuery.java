package Search;

import Classes.Path;
import Classes.Query;
import Classes.Stemmer;
import IndexingLucene.MyIndexReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;

/**
 * Read and parse TREC queries
 * -- INFSCI 2140: Information Storage and Retrieval Spring 2016
 */
public class ExtractQuery {


	private Map<String, Integer> dictionary = new HashMap<String, Integer>();
	public List<Query> GetQueries() throws Exception {
		//you should extract the 4 queries from the Path.TopicDir
		//NT: the query content of each topic should be 1) tokenized, 2) to lowercase, 3) remove stop words, 4) stemming
		//NT: third topic title is ----Star Trek "The Next Generation"-----, if your code can recognize the phrase marked by "", 
		//    and further process the phrase in search, you will get extra points.
		//NT: you can simply pick up title only for query, or you can also use title + description + narrative for the query content.
		//initialzie fileinputstream and bufferedreader 
		List<Query> listOfQuery = new ArrayList<Query>();
		FileInputStream fis = null;
		BufferedReader reader = null;     

		//Build the dictionary of stopwords
		Path textPath = new Path();
		String inputPath = textPath.StopwordDir;
		FileInputStream fisStopWords = new FileInputStream(inputPath);
		BufferedReader readerStopWords = null;  
		readerStopWords = new BufferedReader(new InputStreamReader(fisStopWords));
		String s = readerStopWords.readLine();
		while(s != null){
			s = readerStopWords.readLine();
			dictionary.put(s, 1);
		}

		//read sample.txt
		fis = new FileInputStream(Path.TopicDir);
		reader = new BufferedReader(new InputStreamReader(fis));

		//Reading File line by line using BufferedReader
		//You can get next line using reader.readLine() method.
		String line;
		String marker = "<top>";
		
		while((line = reader.readLine()) != null){

			if(line.equals(marker)){
				
				Query myQuery = new Query();
				line = reader.readLine();
				String topicId = line.substring(14,17);
				
				line = reader.readLine();
				String topicLine = line.substring(8, line.length());
				String[] topicTokenized = Tokenize(topicLine);
				String content = "";
				//lowercase, romove stopwords and stemming
				for(int i = 0; i < topicTokenized.length; i++){
					char[] topicLowercasedChar = lowercase(topicTokenized[i].toCharArray());
					if( !isStopWords(topicLowercasedChar)){
						//String topicLowercasedString = new String(topicLowercasedChar);
						Stemmer stemTool = new Stemmer();
						stemTool.add(topicLowercasedChar, topicLowercasedChar.length);
						stemTool.stem();
						content = content + stemTool + " ";
					}
				}
				myQuery.SetTopicId(topicId);
				myQuery.SetQueryContent(content);
				listOfQuery.add(myQuery);
				//System.out.println(topicId);
				//System.out.println(content);
			}
		}
		/*for(Query e : listOfQuery){
			System.out.println(e.GetQueryContent());
		}*/
		return listOfQuery;
	}




	public boolean isStopWords( char[] word ) {
		// return true if the input word is a stopword, or false if not
		String wordCheck = word.toString();
		return dictionary.containsKey(wordCheck);
	}




	public char[] lowercase( char[] chars ) {
		//transform the upper case characters in the word to lower case
		for(int i = 0; i < chars.length; i++){
			int num = (int) chars[i];
			if(num > 64 && num < 91){
				num = num + 32;		//  transform to lower case, add 32 to ASCII code
				chars[i] = (char) num;
			}//end if
		}// end for
		return chars;
	}

	private String[] Tokenize(String topicLine) {
		String topicLineRemoved= topicLine.replace("\\W", "");
		String result = topicLineRemoved.replace("\"", "");
		String[] StringArray = result.split(" ");
		for(int magicCount = 0; magicCount < StringArray.length; magicCount++)
			if(StringArray[magicCount].equals("Dysphagia"))
				StringArray[magicCount] = "";
		return StringArray;
	}
}
