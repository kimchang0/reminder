package poly.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import poly.service.IAudioService;
import poly.service.IMailService;
import poly.service.IUserService;
import poly.service.IWordAnalysisService;
import poly.util.EncryptUtil;
import poly.util.TTSUtil;

@Controller
public class VoiceController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "UserService")
	IUserService userService;

	@Resource(name = "MailService")
	IMailService MailService;

	@Resource(name = "AudioService")
	private IAudioService audioService;

	@Resource(name = "WordAnalysisService")
	private IWordAnalysisService wordAnalysisService;
	
	@RequestMapping(value = "voice/insertMainVoice")
	@ResponseBody
	public String insertMainVoice(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
		log.info(getClass().getName() + "insertImportantVoice start");

		String sentence = "명령어는 물품, 일정, 물품 추가, 일정 추가, 비밀번호 조회 입니다.";
		String user_id = "main";

		TTSUtil.saveTTS("안내", sentence, user_id);
		
		log.info(getClass().getName() + "insertImportantVoice end");
		return "success";
	}
	
	
	@RequestMapping(value = "voice/insertImportantVoice", method=RequestMethod.POST)
	@ResponseBody
	public String insertImportantVoice(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
		log.info(getClass().getName() + "insertImportantVoice start");

		String sentence = request.getParameter("important");
		String user_id = (String)session.getAttribute("user_id");
		if(user_id == null) {
			String url = "/The/TheLogin.do";
			model.addAttribute("url", url);
			return "/redirectNArt";
		}
		String enc_id = EncryptUtil.encAES128CBC(user_id);
		
		TTSUtil.saveTTS("물품", sentence, enc_id);
		
		log.info(getClass().getName() + "insertImportantVoice end");
		return "success";
	}
	
	@RequestMapping(value = "voice/insertScheduleVoice", method=RequestMethod.POST)
	@ResponseBody
	public String insertScheduleVoice(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
		log.info(getClass().getName() + "insertImportantVoice start");

		String sentence = request.getParameter("schedule");
		String user_id = (String)session.getAttribute("user_id");
		if(user_id == null) {
			String url = "/The/TheLogin.do";
			model.addAttribute("url", url);
			return "/redirectNArt";
		}
		String enc_id = EncryptUtil.encAES128CBC(user_id);
		
		TTSUtil.saveTTS("일정", sentence, enc_id);
		
		log.info(getClass().getName() + "insertImportantVoice end");
		return "success";
	}

	@RequestMapping(value = "voice/getMainvoice", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public byte[] getVoice(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".getVoice start");
		
		String user_id = "main";
		String name = "안내";
		byte[] res = audioService.getTodaySentenceAudio(user_id, name);
		log.info("res: " + res);
		log.info(this.getClass().getName() + ".getVoice end");
		return res;
	}
	
	@RequestMapping(value = "voice/getImportantVoice", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public byte[] getImportantVoice(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".getImportantVoice start");
		
		String user_id = (String)session.getAttribute("user_id");
		String name = "물품";
		String enc_id = EncryptUtil.encAES128CBC(user_id);
		log.info("name: " + name);
		byte[] res = audioService.getTodaySentenceAudio(enc_id, name);
		log.info("res: " + res);
		log.info(this.getClass().getName() + ".getImportantVoice end");
		return res;
	}
	
	@RequestMapping(value = "voice/getScheduleVoice", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public byte[] getScheduleVoice(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".getScheduleVoice start");
		
		String user_id = (String)session.getAttribute("user_id");
		String name = "일정";
		String enc_id = EncryptUtil.encAES128CBC(user_id);
		log.info("name: " + name);
		byte[] res = audioService.getTodaySentenceAudio(enc_id, name);
		log.info("res: " + res);
		log.info(this.getClass().getName() + ".getScheduleVoice end");
		return res;
	}
	
	
	
	
}
