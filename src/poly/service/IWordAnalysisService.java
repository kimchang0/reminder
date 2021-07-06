package poly.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface IWordAnalysisService {

	Iterator<String> doWordNouns(String text) throws Exception;
	
	Map<String, Integer> doWordCount(List<String> pList) throws Exception;
	
	Map<String, Integer> doWordAnalysis(String text) throws Exception;
	
}