package PreProcessData;
import java.io.IOException;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import PreProcessData.TrectextCollection;
//import PreProcessData.TrectextCollection;
/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 * 
 * TextTokenizer can split a sequence of text into individual word tokens, the delimiters can be any common punctuation marks(space, period etc.).
 */
public class WordTokenizer {
	







	//you can add any essential private method or variable
	
	public static void main(String[] args) throws IOException{
		
		//System.out.println(input.length);
		
		TrectextCollection text1 = new TrectextCollection();
		Object tempt = text1.nextDocument().values();
		char[] input = tempt.toString().toCharArray();
		WordTokenizer t = new WordTokenizer(input);
		System.out.println(tempt.toString());
		for(int k = 1; k <100; k++)
			System.out.println(t.nextWord());

		
	}



	private int pos = 1;
	private TrectextCollection text1 = new TrectextCollection();
	private Object tempt = text1.nextDocument().values();
	private char[] input = tempt.toString().toCharArray();
	// YOU MUST IMPLEMENT THIS METHOD
	public WordTokenizer( char[] texts ) throws IOException {
		
	}
	
	
	// YOU MUST IMPLEMENT THIS METHOD
	public char[] nextWord() throws IOException {
		
		// read and return the next word of the document
		// or return null if it reaches the end of the document
		
		
		String outputString = "";
		
		int count = pos;		//update the start position for the next token in the text. 
		
		if(count >= input.length) //return null if it reaches the end of the document
			return null;
		while(input[count] == '.' || input[count] == ','|| input[count] == '!'|| input[count] == ' '|| input[count] == '}'|| input[count] == '{'|| input[count] == '}'|| input[count] == ')'|| input[count] == '(')
			count++;			// move forward if the input char is a punctuation mark. 
		while(count < input.length){
			
			if(input[count] == '.' || input[count] == ','|| input[count] == '!'|| input[count] == ' '|| input[count] == '}'|| input[count] == '{'|| input[count] == '}'|| input[count] == ')'|| input[count] == '(')
				break;			//end appending when meeting the punctuation mark. 
			String inputString = "" + input[count];	//append the char to the current token.
			outputString = outputString + inputString;
			count++;
			pos = count;		// update the position where the current token ended appending chars. 
		}
		//System.out.println(count);
		
		return outputString.toCharArray();
		
	}




	
	
}
