package poly.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.dto.UserDTO;
import poly.service.IMailService;
import poly.service.IUserService;
import poly.util.EncryptUtil;

@Controller
public class MypageContorller {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "UserService")
	IUserService userService;

	@Resource(name = "MailService")
	IMailService MailService;

	// 회원정보 수정
	@RequestMapping(value = "/Mypage/UserCorrection")
	public String UserCorrection(HttpSession session, Model model) {

		log.info("/Mypage/UserCorrection 시작");

		String user_id = (String) session.getAttribute("user_id");

		user_id = (String) session.getAttribute("user_id");
		if (user_id == null) {

			return "/Reminder/ReminderLogin";

		}

		UserDTO uDTO = new UserDTO();

		uDTO.setUser_id(user_id);

		UserDTO res = userService.getUserCorrection(uDTO);

		if (res == null) {
			model.addAttribute("msg", "회원 정보가 없습니다. 자세한 내용은 고객센터에 문의해주세요.");
			model.addAttribute("url", "/Setting/TheMypage.do");
			return "/redirect";
		}

		model.addAttribute("res", res);

		log.info("/Mypage/UserCorrection 종료");

		return "/Mypage/UserCorrection";
	}

	// 비밀번호 변경
	@RequestMapping(value = "/Mypage/PassWordChange")
	public String ThePassWordChange(HttpSession session) {

		log.info("/Mypage/PassWordChange 시작");

		String user_id = (String) session.getAttribute("user_id");
		if (user_id == null) {

			return "/Reminder/ReminderLogin";

		}

		log.info("/Mypage/PassWordChange 종료");

		return "/Mypage/PassWordChange";
	}

	@RequestMapping(value = "/Mypage/passWordChangeProc")
	public String passWordChangeProc(HttpSession session, HttpServletRequest request, Model model) throws Exception {

		log.info("/Mypage/passWordChangeProc 시작");

		String user_id = (String) session.getAttribute("user_id");
		String user_pwd = request.getParameter("pwd");

		if (user_id == null) {

			return "/Reminder/ReminderLogin";

		}

		String HashEnc = EncryptUtil.enHashSHA256(user_pwd);
		String enc_id = EncryptUtil.encAES128CBC(user_id);
		
		UserDTO uDTO = new UserDTO();
		log.info("uDTO.set 시작");
		uDTO.setUser_id(enc_id);
		uDTO.setUser_pwd(HashEnc);
		log.info("uDTO.set 종료");

		int res = userService.pwdChange(uDTO);

		String msg;
		String url = "/Setting/Mypage.do";

		if (res > 0) {
			msg = "회원정보 수정에 성공했습니다.";
		} else {
			msg = "회원정보 수정에 실패했습니다. 고객센터에 문의해주세요.";
		}

		log.info("model.addAttribute 시작");
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		log.info("model.addAttribute 종료");

		log.info("/Mypage/passWordChangeProc 종료");

		return "/redirect";
	}

	// 비밀번호 중복확인
	@ResponseBody
	@RequestMapping(value = "/Mypage/passWordCheck", method = RequestMethod.POST)
	public int passWordCheck(HttpServletRequest request, HttpSession session) throws Exception {

		log.info("/Mypage/passWordCheck 시작");
		int result = 0;
		log.info("String 변수저장 시작");
		String user_pwd = request.getParameter("pwd");
		String user_id = (String) session.getAttribute("user_id");
		log.info("String 변수저장 종료");

		String HashEnc = EncryptUtil.enHashSHA256(user_pwd);
		String enc_id = EncryptUtil.encAES128CBC(user_id);

		UserDTO uDTO = new UserDTO();
		log.info("uDTO.set 시작");
		uDTO.setUser_pwd(HashEnc);
		uDTO.setUser_id(enc_id);
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
		log.info("/Setting/passWordCheck 종료");

		return result;
	}

	// 회원 탈퇴
	@RequestMapping(value = "/Mypage/UserDelete")
	public String TheUserDelete(HttpSession session) {

		log.info("/Mypage/UserDelete 시작");

		String user_id = (String) session.getAttribute("user_id");
		if (user_id == null) {

			return "/Reminder/ReminderLogin";

		}

		log.info("/Mypage/UserDelete 종료");

		return "/Mypage/UserDelete";
	}

	@RequestMapping(value = "/Mypage/UserDeleteProc")
	public String TheUserDeleteProc(HttpServletRequest request, Model model, HttpSession session) throws Exception {

		log.info("/Mypage/UserDeleteProc 시작");
		String user_id = (String) session.getAttribute("user_id");

		if (user_id == null) {

			return "/Reminder/ReminderLogin";

		}
		
		String enc_id = EncryptUtil.encAES128CBC(user_id);
		
		UserDTO uDTO = new UserDTO();
		uDTO.setUser_id(enc_id);

		int res = userService.deleteUser(uDTO);
		log.info("uDTO null? : " + (uDTO == null));

		String msg = "";
		String url = "";
		if (res > 0) {
			msg = "회원 탈퇴를 성공했습니다. 이용해 주셔서 감사합니다.";
		} else {
			msg = "회원 탈퇴를 실패했습니다. 고객센터에 문의해주세요.";
		}

		url = "/Reminder/ReminderLogin.do";

		model.addAttribute("msg", msg);
		model.addAttribute("url", url);

		session.invalidate();

		log.info("/Mypage/UserDeleteProc 종료");

		return "/redirect";
	}

	// 탈퇴 입력문자열 확인
	@ResponseBody
	@RequestMapping(value = "/Mypage/UserDeleteCheck", method = RequestMethod.POST)
	public int TheUserDeleteCheck(HttpServletRequest request, HttpSession session) {

		log.info("/Mypage/UserDeleteCheck 시작");

		int result = 0;
		log.info("String 변수저장 시작");
		String DeleteCheck = request.getParameter("DeleteCheck");
		log.info("String 변수저장 종료");
		log.info("DeleteCheck : " + DeleteCheck);

		if (DeleteCheck.equals("Account_withdrawal")) {
			result = 1;
		} else {
			result = 0;
		}

		log.info("result :" + result);
		log.info("/Mypage/UserDeleteCheck 종료");

		return result;
	}
}
