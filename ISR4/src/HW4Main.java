import Classes.Document;
import Classes.Query;
import IndexingLucene.MyIndexReader;
import PseudoRFSearch.PseudoRFRetrievalModel;
import Search.ExtractQuery;

import java.util.List;

/**
 * !!! DO NOT CHANGE ANYTHING IN THIS CLASS (but you may make a copy and edit it) !!!
 *
 * Main class of Assignment 4.
 * -- INFSCI 2140: Information Storage and Retrieval Spring 2016
 *
 */
public class HW4Main {

	public static void main(String[] args) throws Exception {
		// Open index, initialize the pseudo relevance feedback retrieval model, and extract queries
		MyIndexReader ixreader = new MyIndexReader("trectext");
		PseudoRFRetrievalModel PRFSearchModel=new PseudoRFRetrievalModel(ixreader);
		List<Query> QList = new ExtractQuery().GetQueries();
		// begin search
		long startTime = System.currentTimeMillis(); 
		
		if (QList != null) {
			for (Query aQuery : QList) {
				System.out.println(aQuery.GetQueryContent());
				List<Document> results = PRFSearchModel.RetrieveQuery(aQuery, 20, 100, 0.4);
				if (results != null) {
					int rank = 1;
					for (Document result : results) {
						System.out.println(aQuery.GetTopicId() + " Q0 " + result.docno() + " " + rank + " " + result.score() + " MYRUN");
						rank++;
					}
				}
			}
		}
		long endTime = System.currentTimeMillis(); // end time of running code
		System.out.println("\n\n4 queries search time: " + (endTime - startTime) / 60000.0 + " min");
		ixreader.close();
	}

}
