package poly.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.dto.UserDTO;
import poly.service.IMailService;
import poly.service.IUserService;
import poly.util.CmmUtil;
import poly.util.EncryptUtil;

@Controller
public class SetController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "UserService")
	IUserService userService;

	@Resource(name = "MailService")
	IMailService MailService;

	// 설정 창
	@RequestMapping(value = "Setting/setting")
	public String setting() {

		log.info("/Setting/setting 시작");

		log.info("/Setting/setting 종료");

		return "/Setting/setting";
	}

	// 앱 소개
	@RequestMapping(value = "/Setting/AppIntroduction")
	public String AppIntroduction(HttpSession session) {

		log.info("/Setting/AppIntroduction 시작");

		String user_id = (String) session.getAttribute("user_id");
		if (user_id == null) {

			return "/Reminder/ReminderLogin";

		}

		log.info("/Setting/ReminderAppIntroduction 종료");

		return "/Setting/AppIntroduction";
	}

	// 마이페이지
	@RequestMapping(value = "/Setting/Mypage")
	public String Mypage(HttpSession session) {

		log.info("/Setting/Mypage 시작");

		String user_id = (String) session.getAttribute("user_id");
		if (user_id == null) {

			return "/Reminder/ReminderLogin";

		}

		log.info("/Setting/Mypage 종료");

		return "/Setting/Mypage";
	}

	// 마이페이지 비밀번호 확인
	@RequestMapping(value = "/Setting/MypageCheck")
	public String MypageCheck() {

		log.info("/Setting/TheMypageCheck 시작");

		log.info("/Setting/TheMypageCheck 종료");

		return "/Setting/MypageCheck";
	}

	// 마이페이지 비밀번호 확인 proc
	@ResponseBody
	@RequestMapping(value = "/Setting/MypageCheckProc", method = RequestMethod.POST)
	public int MypageCheckProc(HttpServletRequest request, HttpSession session) throws Exception {

		log.info("/Setting/MypageCheckProc 시작");
		int result = 0;
		log.info("변수 저장 시작");
		String user_pwd = CmmUtil.nvl(request.getParameter("pwd"));
		String user_id = (String) CmmUtil.nvl((String) session.getAttribute("user_id"));
		log.info("변수 저장 종료");

		log.info("암호화 시작");
		String encId = EncryptUtil.encAES128CBC(user_id);
		String HashEnc = EncryptUtil.enHashSHA256(user_pwd);
		log.info("암호화 종료");
		log.info("user_pwd : " + user_pwd);
		log.info("user_id : " + user_id);

		UserDTO uDTO = new UserDTO();
		log.info("uDTO.set 시작");
		uDTO.setUser_pwd(HashEnc);
		uDTO.setUser_id(encId);
		log.info("uDTO.set 종료");

		log.info("userService.Userinquire 시작");
		UserDTO res = userService.Userinquire(uDTO);
		log.info("userService.Userinquire 종료");
		log.info("res : " + res);

		if (res != null) {
			result = 1;
		} else {
			result = 0;
		}

		log.info("result :" + result);
		log.info("/Setting/MypageCheckProc 종료");

		return result;
	}
}
