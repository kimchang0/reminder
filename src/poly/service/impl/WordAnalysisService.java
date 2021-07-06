package poly.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import poly.service.IWordAnalysisService;
import poly.util.CmmUtil;

@Service("WordAnalysisService")
public class WordAnalysisService implements IWordAnalysisService{

	private Logger log = Logger.getLogger(this.getClass());
	
	Komoran nlp = null;
	

	public WordAnalysisService() {
		log.info(this.getClass().getName() + ".WordAnalysisService creator Start");
		
		this.nlp = new Komoran(DEFAULT_MODEL.FULL);
		
		//this.nlp.setUserDic("/myDic/wordDic.txt");
		
		log.info("톰캣 부팅하면서 스프링 프레임워크가 자동 실행되었고, 스프링 실행할 때 nlp변수에 Komoran 객체를 생성하여 저장");
		
		log.info(this.getClass().getName() + ".WordAnalysisService creator end");
	}

	
	@Override
	public Iterator<String> doWordNouns(String text) throws Exception {
		
		log.info(this.getClass().getName() + ".doWordNouns Start");
		
		log.info("분석할 문장: " + text);
		
		String replace_text = text.replaceAll("[^가-힣a-zA-Z0-9]", " ");
		
		log.info("한국어, 영어, 숫자 제어 단어 모두 한 칸으로 변환시킨 문장: " + replace_text);
		
		String trim_text = replace_text.trim();
		
		log.info("분석할 문장의 앞 뒤에 존재할 수 있는 필요없는 공백제거: " + trim_text);
		
		KomoranResult analyzeResultList = this.nlp.analyze(trim_text);
		
		List<String> rList = analyzeResultList.getNouns();
		
		if(rList == null) {
			rList = new ArrayList<String>();
		}
		
		Iterator<String> it = rList.iterator();
		
		log.info(this.getClass().getName() + ".doWordNouns End");
		return it;
	}

	@Override
	public Map<String, Integer> doWordCount(List<String> pList) throws Exception {
		
		log.info(this.getClass().getName() + ".doWordCount Start");
		
		if(pList == null) {
			pList = new ArrayList<String>();
		}
		
		Map<String, Integer> rMap = new HashMap<>();
		
		Set<String> rSet = new HashSet<String>(pList);
		
		Iterator<String> it = rSet.iterator();
		
		while(it.hasNext()) {
			String word = CmmUtil.nvl(it.next());
			
			int frequency = Collections.frequency(pList, word);
			
			log.info("word: " + word);
			log.info("frequency: " + frequency);
			
			rMap.put(word, frequency);
		}
		log.info(this.getClass().getName() + ".doWordCount End");
		
		return rMap;
	}

	@Override
	public Map<String, Integer> doWordAnalysis(String text) throws Exception {
		/*
		log.info(this.getClass().getName() + ".doWordAnalysis Start");
		
		//String newContext = newsCollectService.doNaverNewContents("https://news.naver.com/main/read.nhn?mode=LSD&mid=sec&sid1=101&oid=001&aid=0011955578");
		String newContext = text;
		
		List<String> rList = this.doWordNouns(newContext);
		
		if(rList == null) {
			rList = new ArrayList<String>();
		}
		
		Map<String, Integer> rMap = this.doWordCount(rList);
		
		if(rMap == null) {
			rMap = new HashMap<String, Integer>();
		}
		log.info(this.getClass().getName() + ".doWordAnalysis End");
		
		return rMap;
		*/
		return null;
	}
	

}