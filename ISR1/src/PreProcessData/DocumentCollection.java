package PreProcessData;

import java.io.IOException;
import java.util.Map;

/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 * 
 * DocumentCollection is an interface for reading individual document file from TREC collection.
 * No implementation or modification here.
 */
public interface DocumentCollection {
	
	/**
	 * Try to read and return the next document stored in the collection.
	 * If it reaches the end of the collection file, return null.
	 * Each document should be stored as a Map, in which the key is the document number, while the value is document content
	 * so that you can get the document's number(a string) by calling doc.keySet().iterator().next()
	 * and document's content by map.get(document's number)
	 * 
	 * @return The next document stored in the collection; or null if it is the end of the collection file.
	 */
	Map<String,Object> nextDocument() throws IOException;
	
}
