package poly.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.dto.UserDTO;
import poly.service.IMailService;
import poly.service.IMongoScheduleService;
import poly.service.IUserService;
import poly.service.IWordAnalysisService;
import poly.util.CmmUtil;
import poly.util.EncryptUtil;

@Controller
public class TodayController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "UserService")
	IUserService userService;

	@Resource(name = "MailService")
	IMailService MailService;
	
	@Resource(name = "MongoScheduleService")
	IMongoScheduleService mongoScheduleService;
	
	@Resource(name = "WordAnalysisService")
	private IWordAnalysisService wordAnalysisService;

	/**
	 * ########################################## 메인페이지
	 * ##########################################
	 */
	@RequestMapping(value = "Today/TodayMain")
	public String TodayMain(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {

		log.info("TodayMain 시작");

		String user_id = (String) session.getAttribute("user_id");
		if (user_id == null) {
			return "/The/TheLogin";
		}
		
		UserDTO pDTO = new UserDTO();
		String dec_id = EncryptUtil.encAES128CBC(user_id);
		pDTO.setUser_id(dec_id);
		
		log.info("getInteres 시작");
		UserDTO rDTO = userService.getInterest(pDTO);
		log.info("getInteres 종료");
		
		if(rDTO == null ) {
			rDTO = new UserDTO();
		}
		
		String user_interest = rDTO.getUser_interest();
		
		Map<String, Object> pMap = new HashMap<>();
		
		pMap.put("user_id", dec_id);
		
		List<Map<String, String>> rList = mongoScheduleService.getSchedule(pMap);

		model.addAttribute("user_id", user_id);
		model.addAttribute("user_interest", user_interest);
		model.addAttribute("rList", rList);
		
		log.info("TodayMain 종료");
		return "/Today/TodayMain";
	}

	@RequestMapping(value = "Today/TodayAddList")
	public String TodayAddList(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {

		log.info("TodayAddList 시작");

		String user_id = (String) session.getAttribute("user_id");
		if (user_id == null) {
			return "/The/TheLogin";
		}
		
		UserDTO pDTO = new UserDTO();
		String decId = EncryptUtil.encAES128CBC(user_id);
		pDTO.setUser_id(decId);
		
		log.info("getInteres 시작");
		UserDTO rDTO = userService.getInterest(pDTO);
		log.info("getInteres 종료");
		if(rDTO == null) {
			rDTO = new UserDTO();
		}
		String user_interest = CmmUtil.nvl(rDTO.getUser_interest());
		
		model.addAttribute("user_interest", user_interest);

		log.info("TodayAddList end");
		return "/Today/TodayAddList";
	}
	
	@RequestMapping(value = "Today/importantEdit")
	public String importantEdit(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {

		log.info("TodayAddList 시작");

		String user_id = (String) session.getAttribute("user_id");
		String user_interest = request.getParameter("user_interest");
		if (user_id == null) {
			return "/The/TheLogin";
		}
		UserDTO pDTO = new UserDTO();
		String encId = EncryptUtil.encAES128CBC(user_id);
		String text = user_interest;
		user_interest = "";
		Iterator<String> it =  wordAnalysisService.doWordNouns(text);
		while(it.hasNext()) {
			user_interest += it.next() + ",";
			
		}
		pDTO.setUser_id(encId);
		pDTO.setUser_interest(user_interest);
		
		log.info("getInteres 시작");
		int res = userService.updateUserInterest(pDTO);
		log.info("getInteres 종료");

		if(res == 1) {
			String url = "/Today/TodayMain.do";
			model.addAttribute("url", url);
			log.info("res: 1 TodayAddList success");
			return "/redirectNArt";
		} else {
			String url = "/Today/TodayMain.do";
			String msg = "중요 물품 수정에 실패했습니다.";
			model.addAttribute("url", url);
			model.addAttribute("msg", msg);
			log.info("res: 0 TodayAddList failed");
			return "/redirect";
		}

	}

}