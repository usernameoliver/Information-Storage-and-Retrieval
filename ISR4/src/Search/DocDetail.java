package Search;

import java.util.HashMap;

public class DocDetail {
	private HashMap<String, Integer> tdFreq;
	private int docSize;
	public int docSize(){
		return docSize;
	}
	public HashMap<String, Integer> tdFreq(){
		return tdFreq;
	}
	public void setTdFreq(HashMap<String, Integer> tdFreq){
		this.tdFreq = tdFreq;
	}
	public void setDocSize(int docSize){
		this.docSize = docSize;
	}

}
