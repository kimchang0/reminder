package poly.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;



public class TranslateUtil {
	public static void main(String[] args) throws Exception {
		
		
		System.out.println(yonhaptrans("my name is lee sun young"));

	}
	
	public static String kakaotrans(String sentence) throws Exception {

		String apikey = "90ca526dfb732b87fc0d04f830e4b867"; // REST API 키

		String postParams = "src_lang=en&target_lang=kr&query=";
		String apiURL = "https://dapi.kakao.com/v2/translation/translate?" + postParams;

		
		String sent = URLEncoder.encode(sentence, "UTF-8");
		String queryURL = apiURL + sent;
		URL url = new URL(queryURL);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		String userCredentials = apikey;
		String basicAuth = "KakaoAK " + userCredentials;
		con.setRequestProperty("Authorization", basicAuth);
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("charset", "utf-8");
		con.setUseCaches(false);
		con.setDoInput(true);
		con.setDoOutput(true);
		int responseCode = con.getResponseCode();
		System.out.println("response code : " + responseCode);

		BufferedReader br;
		if (responseCode == 200) {
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}

		String inputLine;
		StringBuffer res = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			res.append(inputLine);
		}
		br.close();
		JSONObject json = new JSONObject(res.toString());

		String translated = ((JSONArray) json.getJSONArray("translated_text").get(0)).getString(0);

		return translated;

	}
	
	//// 헤럴드 파파고 번역
	public static String heraldtrans(String sentence) throws Exception {
        String clientId = "idjxFhoFotsyxpoyAGd4"; //"이재훈 ID" 
        String clientSecret = "isbB4fCgMV"; // 암호

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String text;
        try {
            text = URLEncoder.encode(sentence, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = post(apiURL, requestHeaders, text);

      
        // getting translated text
     	String translated = new JSONObject(responseBody).getJSONObject("message").getJSONObject("result")
     						.getString("translatedText");
		return translated;
    }
	
	// 로이터 번역
	public static String reuterstrans(String sentence) throws Exception {
        String clientId = "_uJgObW9Pq1eIVL7KnVY"; //"김승범 ID" 
        String clientSecret = "SkDWM_G0lK"; // 암호

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String text;
        try {
            text = URLEncoder.encode(sentence, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = post(apiURL, requestHeaders, text);

     // getting translated text
     	String translated = new JSONObject(responseBody).getJSONObject("message").getJSONObject("result")
     						.getString("translatedText");
		return translated;
    }
	
	// 타임즈 번역
	public static String timestrans(String sentence) throws Exception {
        String clientId = "_TaJBmjG3B7HOm_Vq8rW"; //"양원석 ID" 
        String clientSecret = "bGyDwXCg0w"; // 암호

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String text;
        try {
            text = URLEncoder.encode(sentence, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = post(apiURL, requestHeaders, text);

     // getting translated text
     	String translated = new JSONObject(responseBody).getJSONObject("message").getJSONObject("result")
     						.getString("translatedText");
		return translated;
    }
	
	// 연합뉴스 번역
	public static String yonhaptrans(String sentence) throws Exception {
        String clientId = "Bd8P4YFLELjbMcNhE2dz"; // "김창영 ID"
        String clientSecret = "LRfsZfXje5"; // 암호

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String text;
        try {
            text = URLEncoder.encode(sentence, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = post(apiURL, requestHeaders, text);

     // getting translated text
     	String translated = new JSONObject(responseBody).getJSONObject("message").getJSONObject("result")
     						.getString("translatedText");
		return translated;
    }
	
//// 예비 번역
	public static String sparetrans(String sentence) throws Exception {
       String clientId = "xMqW4MVTjkV0Zg7kP4Tx"; //"이재훈2 ID" 
       String clientSecret = "vpc4xVYnRg"; // 암호

       String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
       String text;
       try {
           text = URLEncoder.encode(sentence, "UTF-8");
       } catch (UnsupportedEncodingException e) {
           throw new RuntimeException("인코딩 실패", e);
       }

       Map<String, String> requestHeaders = new HashMap<>();
       requestHeaders.put("X-Naver-Client-Id", clientId);
       requestHeaders.put("X-Naver-Client-Secret", clientSecret);

       String responseBody = post(apiURL, requestHeaders, text);

     
       // getting translated text
    	String translated = new JSONObject(responseBody).getJSONObject("message").getJSONObject("result")
    						.getString("translatedText");
		return translated;
   }
	
	private static String post(String apiUrl, Map<String, String> requestHeaders, String text) {
		HttpURLConnection con = connect(apiUrl);
		String postParams = "source=en&target=ko&text=" + text; // 원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
		try {
			con.setRequestMethod("POST");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			con.setDoOutput(true);
			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
				wr.write(postParams.getBytes());
				wr.flush();
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
				return readBody(con.getInputStream());
			} else { // 에러 응답
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}
	
	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}
}
