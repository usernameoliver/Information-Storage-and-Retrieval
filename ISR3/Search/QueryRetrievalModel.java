package Search;

import Classes.Document;
import Classes.Query;
import IndexingLucene.MyIndexReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A language retrieval model for ranking documents
 * -- INFSCI 2140: Information Storage and Retrieval Spring 2016
 */
public class QueryRetrievalModel {

	protected MyIndexReader indexReader;

	public QueryRetrievalModel(MyIndexReader ixreader) {
		indexReader = ixreader;
	}

	/**
	 * Search for the topic information. 
	 * The returned results (retrieved documents) should be ranked by the score (from the most relevant to the least).
	 * TopN specifies the maximum number of results to be returned.
	 * 
	 * @param aQuery The query to be searched for.
	 * @param TopN The maximum number of returned document
	 * @return
	 */

	public List<Document> retrieveQuery( Query aQuery, int TopN ) throws IOException {
		// NT: you will find our IndexingLucene.Myindexreader provides method: docLength()
		// implement your retrieval model here, and for each input query, return the topN retrieved documents
		// sort the docs based on their relevance score, from high to low
		String[] token = aQuery.GetQueryContent().split(" ");
		//int[][] listOfDocument = indexReader.getPostingList(token[0]);
		HashMap<Integer,DocDetail> DocList = new HashMap<>();
		HashMap<String, Double>tokenRefProb = new HashMap<>();
		for(int i = 0; i < token.length; i++){
			int[][] listOfDocument = indexReader.getPostingList(token[0]);
			for(int j = 0; j < listOfDocument.length; j++){
				int docId = listOfDocument[j][0];
				DocDetail myDocDetail = new DocDetail();
				HashMap<String, Integer> tdFreq = new HashMap<String, Integer>();
				tdFreq.put(token[i], listOfDocument[j][1]);
				myDocDetail.setTdFreq(tdFreq);
				int docSize = indexReader.docLength(docId);
				myDocDetail.setDocSize(docSize);
				DocList.put(docId, myDocDetail);
			}
			double freqTokenRef = (double)indexReader.CollectionFreq(token[i])/142065539;
			//System.out.println(indexReader.CollectionFreq(token[i]));
			tokenRefProb.put(token[i], freqTokenRef);
		}
		//System.out.println(tokenRefProb.values().toString() );

		//int countOfWord = docInfo.getValue().tdFreq().get(token[0]);

		double miu = 0.02;
		int k = 0;
		//System.out.println("size of DocList is " + DocList.size());

		Result[] rList=new Result[DocList.size()]; 

		for (Entry<Integer, DocDetail> docInfo : DocList.entrySet()) {
			int docId = docInfo.getKey();
			double[] probOfWordInDocument = new double[token.length];
			int[] countOfWord = new int[token.length];
			double[] probOfWordInCollection = new double[token.length];
			double probOfQuery = 1;
			int lengthOfDocument = indexReader.docLength(docId);

			for(int i = 0; i < token.length; i++){
				probOfWordInCollection[i] = tokenRefProb.get(token[i]);

				countOfWord[i] = 0;
				HashMap<String, Integer> currentTdFreq = docInfo.getValue().tdFreq();
				if(currentTdFreq.containsKey(token[0]))
					countOfWord[i] = docInfo.getValue().tdFreq().get(token[i]);
				probOfWordInDocument[i] = (double)(countOfWord[i] + miu*probOfWordInCollection[i])/(lengthOfDocument + miu);
				probOfQuery *= probOfWordInDocument[i];
			}
			//(docId, probOfQuery);
			Result initial = new Result();
			initial.setDocId(docId);
			initial.setScore(probOfQuery);
			rList[k] = initial;
			k++;



		}



		List<Document> listOfDocument = new ArrayList<Document>();
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true"); 
		Arrays.sort(rList, new ResultComprator());
		for(int k1 = 0; k1 < TopN; k1++){
			
			Document aDocument = new Document(null, null, miu);
			String docIdString = rList[k1].docId() + "";
			String docNoString = indexReader.getDocno(rList[k1].docId());
			double scoreDouble = rList[k1].score();
			aDocument.setDocid(docIdString);
			aDocument.setDocno(docNoString);
			aDocument.setScore(scoreDouble);
			listOfDocument.add(aDocument);
		}
			
		return listOfDocument;
	}

}