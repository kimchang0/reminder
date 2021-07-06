package poly.util;

import java.util.Iterator;
import java.util.Properties;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class CmmUtil {
	public static String nvl(String str, String chg_str) {
		String res;

		if (str == null) {
			res = chg_str;
		} else if (str.equals("")) {
			res = chg_str;
		} else {
			res = str;
		}
		return res;
	}
	
	public static String nvl(String str){
		return nvl(str,"");
	}
	
	public static String checked(String str, String com_str){
		if(str.equals(com_str)){
			return " checked";
		}else{
			return "";
		}
	}
	
	public static String checked(String[] str, String com_str){
		for(int i=0;i<str.length;i++){
			if(str[i].equals(com_str))
				return " checked";
		}
		return "";
	}
	
	public static String select(String str,String com_str){
		if(str.equals(com_str)){
			return " selected";
		}else{
			return "";
		}
	}
	
public static void main(String[] args) {
		
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,lemma");
		props.setProperty("coref.algorithm", "neural");
		
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		CoreDocument doc = new CoreDocument("Hello, my name is Adam. I have two sisters. I went to California");
		
		pipeline.annotate(doc);
		
		Iterator<CoreSentence> it = doc.sentences().iterator();
		
		while(it.hasNext()) {
			
			CoreSentence sent = it.next();
			
			System.out.println(sent.text());
		}
	}
}
