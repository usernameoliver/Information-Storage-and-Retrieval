package PseudoRFSearch;


public class Result {
	private int docId; 
	private double score;
	
	public int docId(){
		return docId;
	}
	public double score(){
		return score;
	}
	public void setDocId(int docId){
		this.docId = docId;
	}
	public void setScore(double score){
		this.score = score;
	}



}