import Classes.Document;
import Classes.Query;
import IndexingLucene.MyIndexReader;
import Search.ExtractQuery;
import Search.QueryRetrievalModel;

import java.util.List;

/**
 * !!! DO NOT CHANGE ANYTHING IN THIS CLASS !!!
 * 
 * Main class of Assignment 3.
 * -- INFSCI 2140: Information Storage and Retrieval Spring 2016
 *
 */
public class HW3Main {

	public static void main(String[] args) throws Exception {
		//Open index
		System.out.println("I am running");
		MyIndexReader ixreader = new MyIndexReader("trectext");
		//Initialize the MyRetrievalModel 
		QueryRetrievalModel model = new QueryRetrievalModel(ixreader);
		//Load all topics, each topic's query content should be processed in the same way as how the collection documents are pre-processed
		List<Query> QList = new ExtractQuery().GetQueries();
		long startTime=System.currentTimeMillis(); //star time of running code
		if (QList != null) {
			for (Query aQuery : QList) {
				System.out.println(aQuery.GetTopicId()+"\t"+aQuery.GetQueryContent());
				//conduct retrieval on the index for each topic, and return top 20 documents
				List<Document> results = model.retrieveQuery(aQuery, 3);
				if (results != null) {
					int rank = 1;
					for (Document result : results) {
						System.out.println(aQuery.GetTopicId() + " Q0 " + result.docno() + " " + rank + " " + result.score() + " MYRUN");
						rank++;
					}
				}
			}
		}
		long endTime=System.currentTimeMillis(); //end time of running code
		System.out.println("\n\n4 queries search time: "+(endTime-startTime)/60000.0+" min");
		ixreader.close();
	}

}
