package poly.controller;

import static poly.util.CmmUtil.nvl;

import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.dto.UserDTO;
import poly.service.IMailService;
import poly.service.IUserService;
import poly.service.IWordAnalysisService;
import poly.util.EncryptUtil;
import poly.util.RandomUtil;

@Controller
public class UserController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "UserService")
	IUserService userService;

	@Resource(name = "MailService")
	IMailService MailService;

	@Resource(name = "WordAnalysisService")
	private IWordAnalysisService wordAnalysisService;

	// 로그인
	@RequestMapping(value = "/Reminder/ReminderLogin")
	public String ReminderLogin(HttpSession session, ModelMap model) throws Exception {

		log.info("ReminderLogin 시작");

		session.invalidate();

		log.info("ReminderLogin 종료");

		return "/Reminder/ReminderLogin";
	}

	// 로그인 proc
	@RequestMapping(value = "Reminder/ReminderLoginProc")
	public String TheLoginProc(HttpServletRequest request, Model model, HttpSession session) throws Exception {

		log.info("/Reminder/ReminderLoginProc start");
		String id = nvl(request.getParameter("id"));
		String pwd = nvl(request.getParameter("pwd"));

		String encId = EncryptUtil.encAES128CBC(id);
		String HashEnc = EncryptUtil.enHashSHA256(pwd);

		log.info("id :" + encId);
		log.info("pwd :" + HashEnc);

		UserDTO uDTO = new UserDTO();

		uDTO.setUser_id(encId);
		uDTO.setUser_pwd(HashEnc);

		uDTO = userService.getUserInfo(uDTO);
		log.info("uDTO null? : " + (uDTO == null));

		String msg = "";
		String url = "";
		if (uDTO == null) {
			msg = "아이디 비밀번호가 틀렸거나 가입하지 않은 회원입니다.";
			url = "/Reminder/ReminderLogin.do";
		} else {

			String decId = EncryptUtil.decAES128CBC(uDTO.getUser_id());
			String decEmail = EncryptUtil.decAES128CBC(uDTO.getUser_email());
			session.setAttribute("user_id", decId);
			session.setAttribute("user_email", decEmail);
			url = "/Today/TodayMain.do";
			model.addAttribute("url", url);
			return "/redirectNArt";
		}

		model.addAttribute("msg", msg);
		model.addAttribute("url", url);

		log.info("Reminder/ReminderLoginProc end");

		return "/redirect";
	}

	// 로그아웃
	@RequestMapping(value = "Reminder/ReminderLogout")
	public String TheLogout(HttpSession session, Model model) throws Exception {

		log.info("/Reminder/ReminderLogout 시작");

		session.invalidate();

		log.info("/Reminder/ReminderLogout 종료");

		return "/Reminder/ReminderLogin";
	}

	// 회원가입
	@RequestMapping(value = "Reminder/ReminderSignUp")
	public String ReminderSignUp() {

		log.info("ReminderSignUp 시작");

		log.info("ReminderSignUp 종료");

		return "/Reminder/ReminderSignup";
	}

	// 회원가입 proc
	@RequestMapping(value = "Reminder/ReminderSignUpProc", method = RequestMethod.POST)
	public String ReminderSignUpProc(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {

		log.info("/Reminder/ReminderSignUpProc 시작");

		log.info("request.getParameter 시작");

		String user_id = request.getParameter("id");
		String user_pwd = nvl(request.getParameter("pwd"));
		String user_email = nvl(request.getParameter("email"));
		String user_interest = request.getParameter("interest");

		log.info("request.getParameter 종료");
		String text = user_interest;

		Iterator<String> it = wordAnalysisService.doWordNouns(text);
		String encId = EncryptUtil.encAES128CBC(user_id);
		String HashEnc = EncryptUtil.enHashSHA256(user_pwd);
		String encEmail = EncryptUtil.encAES128CBC(user_email);
		String user_interests = "";
		while (it.hasNext()) {
			user_interests += it.next() + ",";
			log.info("interest 형태소 분석 결과: " + user_interests);

		}
		log.info("user_id : " + encId);
		log.info("user_pwd : " + HashEnc);
		log.info("user_email : " + encEmail);
		log.info("user_interest : " + user_interests);

		UserDTO tDTO = new UserDTO();
		log.info("tDTO.set 시작");
		tDTO.setUser_id(encId);
		tDTO.setUser_pwd(HashEnc);
		tDTO.setUser_email(encEmail);
		tDTO.setUser_interest(user_interests);

		log.info("tDTO.set 종료");
		log.info("tDTO" + tDTO);

		log.info("TheService.TheSignUp 시작");
		int res = userService.UserSignUp(tDTO);
		log.info("TheService.TheSignUp 종료");
		log.info("res : " + res);

		String msg;
		String url = "/Reminder/ReminderEmailCertify.do";

		if (res > 0) {
			msg = "회원가입에 성공했습니다.";
			session.setAttribute("user_id", tDTO.getUser_id());
			log.info("sessionSet user_id : " + session.getAttribute("user_id"));
			session.setAttribute("user_email", user_email);
		} else {
			msg = "회원가입에 실패했습니다.";
		}

		log.info("model.addAttribute 시작");
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		log.info("model.addAttribute 종료");

		log.info("ReminderSignUpProc 종료");

		return "/redirect";
	}

	// 아이디 중복확인
	@ResponseBody
	@RequestMapping(value = "/Reminder/idCheck", method = RequestMethod.POST)
	public int idCheck(HttpServletRequest request) throws Exception {
		log.info("idCheck 시작");

		String userId = request.getParameter("userId");

		log.info("TheService.idCheck 시작");
		UserDTO idCheck = userService.idCheck(userId);
		log.info("TheService.idCheck 종료");

		int res = 0;

		log.info("if 시작");
		if (idCheck != null)
			res = 1;

		log.info("result : " + res);
		log.info("if 종료");

		log.info("idCheck 종료");
		return res;
	}
	
	// 비밀번호 찾기
	@RequestMapping(value = "Reminder/ForgotPassWord")
	public String forgotPassWord() {

		log.info("ForgotPassWord 시작");
		
		log.info("ForgotPassWord 종료");

		return "/Reminder/ForgotPassWord";
	}
	
	// 비밀번호 찾기
	@RequestMapping(value = "Reminder/ReminderReMakePW", method = RequestMethod.POST)
	public String TheReMakePW(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {

		log.info("Reminder/ReminderReMakePW 시작");

		String user_email = nvl(request.getParameter("email"));
		String enc_email = EncryptUtil.encAES128CBC(user_email);
		String random = RandomUtil.RandomNum();
		String hash_pw = EncryptUtil.enHashSHA256(random);
		
		UserDTO pDTO = new UserDTO();
		
		pDTO.setUser_email(enc_email);
		pDTO.setUser_pwd(hash_pw);
		
		UserDTO rDTO = userService.getUserEmail(pDTO);
		
		String msg = "";
		String url = "";
		
		if(rDTO == null) {
			msg = "존재하지 않는 이메일입니다. 이메일 주소를 확인해주세요.";
			url = "/Reminder/ForgotPassWord.do";
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
			return "/redirect";
		}
		
		int res = userService.reMakePW(pDTO);
		
		UserDTO uDTO = new UserDTO();
		
		uDTO.setUser_email(enc_email);
		uDTO.setUser_pwd(random);

		
		if(res <= 1) {
			int mailRes = MailService.doSendPassWordMail(uDTO);
			if(mailRes <= 1) {
				msg = "임시 비밀번호가 가입하신 메일 주소로 전송되었습니다.";
				url = "/Reminder/ReminderLogin.do";
			} else {
				msg = "메일 서버의 오류로 임시 비밀번호 전송을 실패했습니다. 잠시 후 다시 시도해주세요.";
				url = "/Reminder/ReminderReMakePW.do";
			}
		}

		log.info("model.addAttribute 시작");
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		log.info("model.addAttribute 종료");

		log.info("Reminder/ReminderReMakePW 종료");

		return "/redirect";
	}
	
	
	
}
