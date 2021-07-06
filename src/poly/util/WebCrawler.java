package poly.util;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebCrawler {
	
	public static void main(String[] args) throws IOException {
		// uk뉴스 사이트 (https:// 는 보안때문에 불가)
				String url = "http://www.reuters.com/news/world";

				// JSOUP 라이브러리를 통해 사이트에 접속되면, 그 사이트 전체의 HTML 소스를 저장할 변수
				Document doc = null;

				// 사이트 접속
				doc = Jsoup.connect(url).get();

				// 사이트에 접속하여 전체 기사 중 메인 기사를 찾아 들어가야 함.

				// 웹 페이지 전체 소스 중 일부 태그를 선택하기 위해 사용
				// 메인페이지의 url을 가져오기 위함
				Element element_urlGet = doc.select(".story-content a").first();

				// element_urlGet 소스에 href를 가져옴
				String href = element_urlGet.attr("href");
				
				// 크롤링하는 값 앞에 기본 주소가 있어야 함.
				url = "https://www.reuters.com/article/us-usa-election-kremlin/kremlin-says-lack-of-clarity-at-u-s-election-could-have-bad-impact-on-global-economy-idUSKBN27L10L";
				System.out.println(url);
				// 기사 링크로 들어가기
				doc = Jsoup.connect(url).get();

				// 뉴스의 제목
				Element element_title = doc.select(".BackdropImage-image-a7Ah5").first();
				String news_title = CmmUtil.nvl(element_title.text().trim().toString());
				System.out.println(element_title);
				
				
	}
	// 코리아헤럴드
	public static String[] crawlHerald() throws IOException {
		// 코리아헤럴드 사이트 (https:// 는 보안때문에 불가)
				String url = "http://www.koreaherald.com";

				// JSOUP 라이브러리를 통해 사이트에 접속되면, 그 사이트 전체의 HTML 소스를 저장할 변수
				Document doc = null;
				// 사이트 접속
				doc = Jsoup.connect(url).get();
				// 사이트에 접속하여 전체 기사 중 메인 기사를 찾아 들어가야 함.
				// 웹 페이지 전체 소스 중 일부 태그를 선택하기 위해 사용

				Elements element_urlGet = doc.select("a.main_c_art_main");
				// element_urlGet 소스에 href를 가져옴
				String newsUrl = (element_urlGet.attr("href")).toString();
				
				// 크롤링하는 값 앞에 기본 주소가 있어야 함.
				url = "http://www.koreaherald.com" + newsUrl;
				System.out.println(url);
				// 메인 기사 주소로 재접속
				doc = Jsoup.connect(url).get();
				
				// 본문 전체를 가져옴 
				Elements element = doc.select("div.view_bg"); 
				
			
				Iterator<Element> it_title = element.select("div.view_tit").iterator();
				
				// 뉴스의 제목
				String news_title = element.select("h1").text();
				
				System.out.println(news_title);
				
//				// 뉴스의 기자
//				Iterator<Element> it_author = element.select("div.view_tit_byline_l").iterator();
//				
//				String newsGetAuthor = element.select("a").text();
//			
//				String news_author = newsGetAuthor.substring(2, 15);
				
				
//				// 뉴스의 날짜
//				Elements element_date = doc.select("div.view_tit_byline_r");
//				Iterator<Element> it_date = element.select("div.view_tit_byline_r").iterator();
//				
//				String newsGetDate = element_date.select("div.view_tit_byline_r").text();
//				String news_dt = newsGetDate.substring(11, 24);
				
				// 뉴스의 내용
				Elements element_contents = doc.select("div.view_con_t");
				String news_contents = element_contents.text();
				System.out.println(news_contents);
				

		return new String[] { news_title, news_contents, url };

	}	
	
	/**
	 *  로이터 영어 웹크롤링
	 */
	public static String[] crawlreuters() throws IOException {
		

		// uk뉴스 사이트 (https:// 는 보안때문에 불가)
		String url = "http://www.reuters.com/news/world";

		// JSOUP 라이브러리를 통해 사이트에 접속되면, 그 사이트 전체의 HTML 소스를 저장할 변수
		Document doc = null;

		// 사이트 접속
		doc = Jsoup.connect(url).get();

		// 사이트에 접속하여 전체 기사 중 메인 기사를 찾아 들어가야 함.

		// 웹 페이지 전체 소스 중 일부 태그를 선택하기 위해 사용
		// 메인페이지의 url을 가져오기 위함
		Element element_urlGet = doc.select(".story-content a").first();

		// element_urlGet 소스에 href를 가져옴
		String href = element_urlGet.attr("href");
		
		// 크롤링하는 값 앞에 기본 주소가 있어야 함.
		url = "https://www.reuters.com"+href;
		System.out.println(url);
		// 기사 링크로 들어가기
		doc = Jsoup.connect(url).get();

		// 뉴스의 제목
		Element element_title = doc.select(".ArticlePage-article-header-23J2O h1").first();
		String news_title = CmmUtil.nvl(element_title.text().trim().toString());
		System.out.println(news_title);
		// <div class="view_tit_byline_l"><a
		// href="/search/list_name.php?byline=Ock+Hyun-ju">By Ock Hyun-ju</a></div>
//		// 뉴스의 기자
//		Elements element_authorGet = doc.select("div.view_tit_byline_l");
//		String element_author = element_authorGet.attr("a");
//
//		// <div class="view_tit_byline_r">Published : Aug 26, 2020 - 15:44
//		// &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Updated : Aug 26, 2020 - 20:36</div>z
//		// 뉴스의 날짜
//		Elements element_date = doc.select("dd.e1ojgjhb2");
//		// split으로 앞의 불필요한 문자들을 삭제 후 삽입할 것.

		// <div class="view_con_t"> 뉴스의 내용
		Elements element_contents = doc.select("div.ArticleBodyWrapper p");
		String news_contents = CmmUtil.nvl(element_contents.next().text().trim().toString());
		System.out.println(news_contents);
		
		return new String[] { news_title, news_contents, url };
	}
	
	/**
	 * times 웹크롤링
	 */
	public static String[] crawltimes() throws IOException {

		// 코리아타임즈 사이트 (https:// 는 보안때문에 불가)
		String url = "https://www.koreatimes.co.kr/www2/index.asp";

		// JSOUP 라이브러리를 통해 사이트에 접속되면, 그 사이트 전체의 HTML 소스를 저장할 변수
		Document doc = null;
		// 사이트 접속
		doc = Jsoup.connect(url).get();
		// 사이트에 접속하여 전체 기사 중 메인 기사를 찾아 들어가야 함.
		// 웹 페이지 전체 소스 중 일부 태그를 선택하기 위해 사용

		Elements element_urlGet = doc.select("div.top1_headline a");
		
		// element_urlGet 소스에 href를 가져옴
		String newsUrl = (element_urlGet.attr("href")).toString();
		
		// 크롤링하는 값 앞에 기본 주소가 있어야 함.
		url = "http://www.koreatimes.co.kr" + newsUrl;
		
		// 메인 기사 주소로 재접속
		doc = Jsoup.connect(url).get();
		
		// 제목과 날짜
		Elements element_title_date = doc.select("div.view_HD_div"); 
		
		// 뉴스의 제목
		// 로그를 찍기 위함
		String news_title = element_title_date.select("div.view_headline").text();
				
		// 뉴스의 날짜
		String newsGetDate = element_title_date.select("div.view_date").text();
		String news_dt = newsGetDate.substring(9, 19);
		
		// 뉴스의 내용
		Elements element_contents = doc.select("div#startts span");
		String news_contents = element_contents.text();
		String test_news_contents = news_contents.substring(0, 100);
		
		return new String[] { news_title, news_contents, url };
	}
	
	/**
	 * yonhap 웹크롤링
	 */
	public static String[]crawlyonhap() throws IOException {

		// 연합뉴스 사이트 (https:// 는 보안때문에 불가)
		String url = "http://en.yna.co.kr";

		// JSOUP 라이브러리를 통해 사이트에 접속되면, 그 사이트 전체의 HTML 소스를 저장할 변수
		Document doc = null;

		// 사이트 접속
		doc = Jsoup.connect(url).get();
		//log.info(doc.toString());

		// 사이트에 접속하여 전체 기사 중 메인 기사를 찾아 들어가야 함.

		// 웹 페이지 전체 소스 중 일부 태그를 선택하기 위해 사용
		// 메인페이지의 url을 가져오기 위함
		Elements element_urlGet = doc.select("article.top-news-zone" + " div.box" + " a");
		//log.info("element_urlGet: "+ element_urlGet.toString());
		
		// element_urlGet 소스에 href를 가져옴
		url = "http:" + element_urlGet.attr("href");

		// 메인 기사 주소로 재접속
		doc = Jsoup.connect(url).get();

		// 뉴스의 제목
		Elements element_titleget = doc.select("h1.tit");
		String news_title = element_titleget.text();

		// <div class="view_tit_byline_l"><a
		// href="/search/list_name.php?byline=Ock+Hyun-ju">By Ock Hyun-ju</a></div>
		// 뉴스의 기자
		String element_author = "없음";

		// <div class="view_tit_byline_r">Published : Aug 26, 2020 - 15:44
		// &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Updated : Aug 26, 2020 - 20:36</div>
		// 뉴스의 날짜
		Elements element_dateget = doc.select("div.info-con" + " span.txt");
		String element_date = element_dateget.text();
		// split으로 앞의 불필요한 문자들을 삭제 후 삽입할 것
		// <div class="view_con_t"> 뉴스의 내용
		Elements element_contentsget = doc.select("div.article-story");
		String news_contents = element_contentsget.text();
		
		return new String[] { news_title, news_contents, url };
	}
	public static String getMeaning(String word) throws IOException {

		// 위키 주소
		String url = "https://en.wiktionary.org/wiki/" + word;

		// 페이지 내용 담을 객체
		Document doc = null;

		// 홈페이지 정보 가져오기
		doc = Jsoup.connect(url).get();

		// 첫번째 단어 가져오기
		Elements e = doc.select("ol > li");
		Iterator<Element> it = e.iterator();
		String meaning = null;
		while (it.hasNext()) {
			e.select("span.HQToggle").remove();
			e.select("ul").remove();
			e.select("dl").remove();
			if (e.text().trim().equals("")) {
				continue;
			} else {
				meaning = e.text().trim();
				break;
			}
		}

		return meaning;

	}
}
