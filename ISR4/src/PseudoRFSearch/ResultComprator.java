package PseudoRFSearch;

import java.util.Comparator;

public class ResultComprator implements Comparator<Object> {
	public int compare(Object arg0, Object arg1) {
		Result t1=(Result)arg0; Result t2=(Result)arg1;
		if(t1.score() != t2.score())
			return t1.score()<t2.score()? 1:-1;
		else 
			return 1;
	} 
}
