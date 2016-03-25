package PreProcessData;

import Classes.*;

/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 *
 * This class is used for extract the stem of certain word by calling stemmer
 */
public class WordNormalizer {
	//you can add essential private methods or variables
	/*public static void main(String[] args){
		char[] m = {'a','b','C','D'};
		WordNormalizer wordNorm = new WordNormalizer();
		char[] word="computer".toCharArray();
		char [] resultLowercase = wordNorm.lowercase(m);
		String result = wordNorm.stem(word);
		System.out.println(result);
		System.out.println(resultLowercase);
	}*/
	// YOU MUST IMPLEMENT THIS METHOD
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
	
	public String stem(char[] chars)
	{
		//use the stemmer in Classes package to do the stemming on input word, and return the stemmed word
		String str="";
		Stemmer st = new Stemmer();
		
		st.add(chars,chars.length);
		st.stem();
		str = st.toString();
		return str;
	}
	
}
