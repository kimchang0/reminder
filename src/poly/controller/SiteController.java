package poly.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import poly.service.IMailService;
import poly.service.IMongoUserService;
import poly.service.IUserService;
import poly.util.DateUtil;
import poly.util.EncryptUtil;

@Controller
public class SiteController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "UserService")
	IUserService userService;

	@Resource(name = "MailService")
	IMailService MailService;

	@Resource(name = "MongoUserService")
	IMongoUserService mongoUserService;

	@RequestMapping(value = "Site/SiteList")
	public String wordCard(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {

		log.info("SiteList 시작");

		String user_id = (String) session.getAttribute("user_id");
		if (user_id == null) {
			return "/Reminder/ReminderLogin";
		}

		String enc_id = EncryptUtil.encAES128CBC(user_id);

		Map<String, Object> pMap = new HashMap<>();

		pMap.put("user_id", enc_id);

		List<Map<String, String>> rList = mongoUserService.getUserInfo(pMap);

		for (Map<String, String> rMap : rList) {

			log.info("rMap.get('site_name'): " + rMap.get("site_name"));
			log.info("rMap.get('site_address'): " + rMap.get("site_address"));
			log.info("rMap.get('site_id'): " + rMap.get("site_id"));
			log.info("rMap.get('site_pw'): " + rMap.get("site_pw"));
		}

		model.addAttribute("rList", rList);

		log.info("SiteList 종료");

		return "/Site/SiteList";
	}

	@RequestMapping(value = "Site/siteAdd")
	public String siteAdd(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
		log.info("siteAdd start!");

		String user_id = (String) session.getAttribute("user_id");
		if (user_id == null) {
			return "/Reminder/ReminderLogin";
		}
		String site_name = request.getParameter("site_name");
		String site_address = request.getParameter("site_address");
		String site_id = request.getParameter("site_id");
		String site_pw = request.getParameter("site_pw");

		String enc_user_id = EncryptUtil.encAES128CBC(user_id);
		String enc_site_id = EncryptUtil.encAES128CBC(site_id);
		String enc_site_pw = EncryptUtil.encAES128CBC(site_pw);

		String re_enc_site_id = EncryptUtil.encAES128CBC(enc_site_id);
		String re_enc_site_pw = EncryptUtil.encAES128CBC(enc_site_pw);
		String insert_date = DateUtil.getDateTime("yyyyMMddhhmmss");

		Map<String, Object> pMap = new LinkedHashMap<>();

		pMap.put("user_id", enc_user_id);
		pMap.put("site_address", site_address);
		pMap.put("site_id", re_enc_site_id);
		pMap.put("site_name", site_name);
		pMap.put("site_pw", re_enc_site_pw);
		pMap.put("insert_date", insert_date);

		int res = mongoUserService.insertUserInfo(pMap);

		if (res == 1) {
			log.info("insertUserInfo success!");
		} else {
			log.info("insertUserInfo failed!");
		}

		String url = "/Site/SiteList.do";

		model.addAttribute("url", url);
		log.info("siteAdd end!");
		return "/redirectNArt";
	}

	@RequestMapping(value = "Site/deleteList", method = RequestMethod.POST)
	public String deleteList(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {

		log.info("deleteList 시작");

		String user_id = (String) session.getAttribute("user_id");
		if (user_id == null) {
			return "/Reminder/ReminderLogin";
		}

		String site_address = request.getParameter("site_address");
		String site_id = request.getParameter("site_id");
		String dec_id = EncryptUtil.encAES128CBC(user_id);

		Map<String, Object> pMap = new HashMap<>();

		pMap.put("user_id", dec_id);
		pMap.put("site_address", site_address);
		pMap.put("site_id", site_id);

		int res = mongoUserService.deleteUser(pMap);

		String url = "";

		if (res <= 1) {
			url = "/Site/SiteList.do";
		} else {
			String msg = "삭제를 실패했습니다.";
			url = "/Site/SiteList.do";

			model.addAttribute("url", url);
			model.addAttribute("msg", msg);
			return "/redirect";
		}

		model.addAttribute("url", url);
		log.info("deleteList 종료");

		return "/redirectNArt";
	}

	@RequestMapping(value = "Site/siteEdit", method = RequestMethod.POST)
	public String siteEdit(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {

		log.info("siteEdit 시작");

		String user_id = (String) session.getAttribute("user_id");
		if (user_id == null) {
			return "/Reminder/ReminderLogin";
		}

		String site_name = request.getParameter("site_name");
		String site_address = request.getParameter("site_address");
		String site_id = request.getParameter("site_id");
		String site_pw = request.getParameter("site_pw");

		String before_site_name = request.getParameter("before_site_name");
		String before_site_address = request.getParameter("before_site_address");
		String before_site_id = request.getParameter("before_site_id");
		String before_site_pw = request.getParameter("before_site_pw");

		String enc_id = EncryptUtil.encAES128CBC(site_id);
		String reEnc_id = EncryptUtil.encAES128CBC(enc_id);
		String enc_pw = EncryptUtil.encAES128CBC(site_pw);
		String reEnc_pw = EncryptUtil.encAES128CBC(enc_pw);
		String enc_user_id = EncryptUtil.encAES128CBC(user_id);

		Map<String, Object> before_map = new HashMap<>();

		before_map.put("user_id", enc_user_id);
		before_map.put("site_name", before_site_name);
		before_map.put("site_address", before_site_address);
		before_map.put("site_id", before_site_id);
		before_map.put("site_pw", before_site_pw);

		Map<String, Object> after_map = new HashMap<>();

		after_map.put("user_id", enc_user_id);
		after_map.put("site_name", site_name);
		after_map.put("site_address", site_address);
		after_map.put("site_id", reEnc_id);
		after_map.put("site_pw", reEnc_pw);

		int res = mongoUserService.editUserInfo(before_map, after_map);

		String url = "";

		if (res <= 1) {
			url = "/Site/SiteList.do";
		} else {
			String msg = "수정을 실패했습니다.";
			url = "/Site/SiteList.do";

			model.addAttribute("url", url);
			model.addAttribute("msg", msg);
			return "/redirect";
		}

		model.addAttribute("url", url);
		log.info("siteEdit 종료");

		return "/redirectNArt";
	}

}