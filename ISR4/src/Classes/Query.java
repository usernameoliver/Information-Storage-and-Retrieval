package Classes;

/**
 *  INFSCI 2140: Information Storage and Retrieval Spring 2016
 */
public class Query {
	//you can modify this class

	private String queryContent;	
	private String topicId;	
	
	
	
	public String GetQueryContent() {
		return queryContent;
	}
	public String GetTopicId() {
		return topicId;
	}
	public void SetQueryContent(String content){
		queryContent=content;
	}	
	public void SetTopicId(String id){
		topicId=id;
	}
}
