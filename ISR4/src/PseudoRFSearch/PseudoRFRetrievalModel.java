package PseudoRFSearch;

import Classes.Document;

import Classes.Query;
import IndexingLucene.MyIndexReader;
import Search.DocDetail;
import Search.QueryRetrievalModel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Class of Assignment 4.
 * Implement your pseudo feedback retrieval model here
 * -- INFSCI 2140: Information Storage and Retrieval Spring 2016
 */
public class PseudoRFRetrievalModel {

	MyIndexReader ixreader;
	private long miu = 4;


	public PseudoRFRetrievalModel(MyIndexReader ixreader)
	{
		this.ixreader=ixreader;
	}

	/**
	 * Search for the topic with pseudo relevance feedback. 
	 * The returned results (retrieved documents) should be ranked by the score (from the most relevant to the least).
	 * 
	 * @param aQuery The query to be searched for.
	 * @param TopN The maximum number of returned document
	 * @param TopK The count of feedback documents
	 * @param alpha parameter of relevance feedback model
	 * @return TopN most relevant document, in List structure
	 */
	public List<Document> RetrieveQuery( Query aQuery, int TopN, int TopK, double alpha) throws Exception {	
		// this method will return the retrieval result of the given Query, and this result is enhanced with pseudo relevance feedback
		// (1) you should first use the original retrieval model to get TopK documents, which will be regarded as feedback documents
		// (2) implement GetTokenRFScore to get each query token's P(token|feedback model) in feedback documents
		// (3) implement the relevance feedback model for each token: combine the each query token's original retrieval score P(token|document) with its score in feedback documents P(token|feedback model)
		// (4) for each document, use the query likelihood language model to get the whole query's new score, P(Q|document)=P(token_1|document')*P(token_2|document')*...*P(token_n|document')
		QueryRetrievalModel model = new QueryRetrievalModel(ixreader);
		List<Document> documentsFB = model.retrieveQuery(aQuery, TopK);


		//get P(token|feedback documents)
		HashMap<String,Double> TokenRFScore=GetTokenRFScore(aQuery,TopK);


		String[] tokens = aQuery.GetQueryContent().split("\\s+");

		//get P(token|Collection) and put them into tokenRefProb
		HashMap<Integer,DocDetail> DocList = new HashMap<>();
		HashMap<String, Double>tokenRefProb = new HashMap<>();
		for(int i = 0; i < tokens.length; i++){
			int[][] listOfDocument = ixreader.getPostingList(tokens[i]);
			for(int j = 0; j < listOfDocument.length; j++){
				int docId = listOfDocument[j][0];
				DocDetail myDocDetail = new DocDetail();
				HashMap<String, Integer> tdFreq = new HashMap<String, Integer>();
				tdFreq.put(tokens[i], listOfDocument[j][1]);
				myDocDetail.setTdFreq(tdFreq);
				int docSize = ixreader.docLength(docId);
				myDocDetail.setDocSize(docSize);
				DocList.put(docId, myDocDetail);
			}
			double freqTokenRef = (double)ixreader.CollectionFreq(tokens[i])/142065539;
			//System.out.println(indexReader.CollectionFreq(token[i]));
			tokenRefProb.put(tokens[i], freqTokenRef);
		}

		//create new ranked list
		Result[] rList=new Result[DocList.size()]; 
		int k = 0;
		//Implement the pseudo-relevance feedback model
		for (Entry<Integer, DocDetail> docInfo : DocList.entrySet()) {
			int docId = docInfo.getKey();
			double[] probOfWordInDocument = new double[tokens.length];
			double[] probOfWordInDocumentPlus = new double[tokens.length];
			int[] countOfWord = new int[tokens.length];
			double[] probOfWordInCollection = new double[tokens.length];
			double probOfQuery = 1;
			int lengthOfDocument = ixreader.docLength(docId);

			for(int i = 0; i < tokens.length; i++){
				probOfWordInCollection[i] = tokenRefProb.get(tokens[i]);

				countOfWord[i] = 0;
				HashMap<String, Integer> currentTdFreq = docInfo.getValue().tdFreq();
				if(currentTdFreq.containsKey(tokens[i]))
					countOfWord[i] = docInfo.getValue().tdFreq().get(tokens[i]);
				probOfWordInDocument[i] = (double)(countOfWord[i] + miu*probOfWordInCollection[i])/(lengthOfDocument + miu);//This is P(token|documents) with Dirichlet smoothing
				probOfWordInDocumentPlus[i] = alpha * probOfWordInDocument[i] + (1 - alpha) *  TokenRFScore.get(tokens[i]);//alpha*P(token|documents) + (1-alpha)* P(token|feedback documents)
				probOfQuery *= probOfWordInDocumentPlus[i];//Calculate the P(Q|document)=P(token_1|document')*P(token_2|document')*...*P(token_n|document')

			}

			//save (docId, probOfQuery) into the list rlist;
			Result initial = new Result();
			initial.setDocId(docId);
			initial.setScore(probOfQuery);

			rList[k] = initial;
			k++;

		}

		// sort all retrieved documents from most relevant to least, and return TopN
		List<Document> results = new ArrayList<Document>();
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true"); 
		Arrays.sort(rList, new ResultComprator());
		for(int k1 = 0; k1 < TopN; k1++){

			Document aDocument = new Document(null, null, miu);
			String docIdString = rList[k1].docId() + "";
			String docNoString = ixreader.getDocno(rList[k1].docId());
			double scoreDouble = rList[k1].score();
			aDocument.setDocid(docIdString);
			aDocument.setDocno(docNoString);
			aDocument.setScore(scoreDouble);
			results.add(aDocument);
		}
		return results;
	}

	public HashMap<String,Double> GetTokenRFScore(Query aQuery,  int TopK) throws Exception
	{
		// for each token in the query, you should calculate token's score in feedback documents: P(token|feedback documents)
		// use Dirichlet smoothing
		// save <token, score> in HashMap TokenRFScore, and return it
		HashMap<String,Double> TokenRFScore=new HashMap<String,Double>();
		String[] tokens = aQuery.GetQueryContent().split("\\s+");
		QueryRetrievalModel model = new QueryRetrievalModel(ixreader);
		List<Document> documentsFB = model.retrieveQuery(aQuery, TopK);
		for(int i = 0; i < tokens.length; i++){
			int[][] listOfDocument = ixreader.getPostingList(tokens[i]);
			HashMap<Integer, Integer> postings = new HashMap<Integer, Integer>();
			for(int j = 0; j < listOfDocument.length; j++){
				postings.put(listOfDocument[j][0], listOfDocument[j][1]);
			}

			int freqFB = 0;
			int docSize = 0;
			long probCollection = ixreader.CollectionFreq(tokens[i]);

			for(Document aDocument : documentsFB){
				int docId= Integer.parseInt(aDocument.docid());
				if(postings.get(docId) != null)
					freqFB += postings.get(docId);
				docSize += ixreader.docLength(docId);

			}

			double score = (freqFB + miu  * probCollection)/(docSize + miu);
			TokenRFScore.put(tokens[i], score);
		}


		return TokenRFScore;
	}


}