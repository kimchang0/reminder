package poly.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import poly.service.IAudioService;
import poly.util.TTSUtil;

@Service("AudioService")
public class AudioService implements IAudioService{

	Logger log = Logger.getLogger(this.getClass());
	
	static final String TEMP_DIR = "C:\\Users\\shfkf\\AppData\\Local\\Temp\\";
	
	@Override
	public byte[] getTodaySentenceAudio(String user_id, String name) throws Exception {
		
		//String finalPath = TTSUtil.TTS_PATH + today + TTSUtil.SLASH + idx + ".ogg";
		//String finalPath = TTSUtil.TTS_PATH + newsUrl + TTSUtil.SLASH + idx + ".ogg";
		
		user_id = user_id.replaceAll("[^A-Za-z0-9]", "");
		String finalPath = TTSUtil.TTS_PATH + user_id + TTSUtil.SLASH + name + ".wav";
		
		File f = new File(finalPath);
		log.info(this.getClass().getName() + " f: " + f);
		InputStream in = new FileInputStream(f);
		log.info(this.getClass().getName() + " in: " + in);
		return IOUtils.toByteArray(in);
	}

	@Override
	public Map<String, Object> analyzeAudio(String data, String newsUrl, String sentenceAudioIdx) throws Exception {
		String requestURL = "http://localhost:5000/score";
		
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(requestURL);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, -7);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		
//		fixed date to 200619 for development purpose
		params.add(new BasicNameValuePair("date", sdf.format(c.getTime())));
//		params.add(new BasicNameValuePair("date", "200619"));
		params.add(new BasicNameValuePair("data", data));
		newsUrl = newsUrl.replaceAll("[^A-Za-z0-9]", "");
		params.add(new BasicNameValuePair("newsUrl", newsUrl));
		params.add(new BasicNameValuePair("idx", sentenceAudioIdx));
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity resEntity = response.getEntity();
		System.out.println(response.getStatusLine());
		StringBuffer sb = new StringBuffer();
		if(resEntity != null) {
			try(InputStream instream = resEntity.getContent()){
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		        	sb.append(line);
		        }
			}
		}
		
		
		
		JSONObject res = new JSONObject(sb.toString());
		String answerTempFile = (String)res.get("answer_temp_file");
		answerTempFile = answerTempFile.replace(".ogg", "");
		
		// 확장자 없는 임시파일 지우기
		File f = new File(answerTempFile);
		f.delete();
		
//		확장자 없는 웨이브파일 지우기
		f = new File(answerTempFile + ".wav");
		f.delete();
		
		return res.toMap();
	}

	@Override
	public byte[] getAnswerAudio(String answer_temp_file) throws Exception {

		File f = new File(TEMP_DIR + answer_temp_file);
		InputStream in = new FileInputStream(f);
		byte[] res =IOUtils.toByteArray(in);
		f.delete();
		return res;
	}


}
