package poly.controller;

import java.util.HashMap;
import java.util.LinkedList;
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
import poly.service.IMongoScheduleService;
import poly.service.IUserService;
import poly.util.DateUtil;
import poly.util.EncryptUtil;

@Controller
public class ScheduleController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "UserService")
	IUserService userService;

	@Resource(name = "MailService")
	IMailService MailService;
	
	@Resource(name = "MongoScheduleService")
	IMongoScheduleService mongoScheduleService;

	@RequestMapping(value = "Schedule/ScheduleList")
	public String ScheduleList(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
		
		log.info(this.getClass().getName() + "ScheduleList start");
		
		String user_id = (String) session.getAttribute("user_id");
		if (user_id == null) {
			return "/The/TheLogin";
		}
		String enc_id = EncryptUtil.encAES128CBC(user_id);
	
		Map <String, Object> pMap = new HashMap<>();
		
		List<Map<String, String>> rList = new LinkedList<>();
		
		pMap.put("user_id", enc_id);
		
		rList = mongoScheduleService.getSchedule(pMap);
		
		log.info(this.getClass().getName() + "ScheduleList end");
		
		model.addAttribute("rList", rList);
		
		return "/Schedule/ScheduleList";
	}
	
	@RequestMapping(value = "Schedule/scheduleAdd", method = RequestMethod.POST)
	public String ScheduleAdd(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception{
		
		log.info(this.getClass().getName() + "ScheduleAdd start");
		
		String user_id = (String) session.getAttribute("user_id");
		String schedule_name = request.getParameter("schedule_name");
		String schedule_alarm = request.getParameter("alarm");
		String schedule_time = request.getParameter("time");
		String schedule_minute = request.getParameter("minute");
		String insert_date = DateUtil.getDateTime("yyyyMMdd"); 	
		
		if(user_id == null) {
			String addr = "/Reminder/ReminderLogin.do";
			model.addAttribute("url", addr);
			return "/redirectNArt";
		}
		String enc_id = EncryptUtil.encAES128CBC(user_id);
		
		Map<String, Object> pMap = new HashMap<>();
		
		pMap.put("user_id", enc_id);
		pMap.put("schedule_name", schedule_name);
		pMap.put("schedule_alarm", schedule_alarm);
		pMap.put("schedule_time", schedule_time);
		pMap.put("schedule_minute", schedule_minute);
		pMap.put("insert_date", insert_date);
		
		mongoScheduleService.insertSchedule(pMap);
		
		String url = "/Schedule/ScheduleList.do";
		
		model.addAttribute("url", url);
		
		log.info(this.getClass().getName() + "ScheduleAdd end");
		
		return "/redirectNArt";
	}
	
	@RequestMapping(value = "Schedule/deleteList", method = RequestMethod.POST)
	public String deleteList(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception{
		
		log.info(this.getClass().getName() + "deleteList start");
		
		String user_id = (String) session.getAttribute("user_id");
		String schedule_name = request.getParameter("schedule_name");
		String schedule_time = request.getParameter("schedule_time");
		String schedule_minute = request.getParameter("schedule_minute");
		
		if(user_id == null) {
			String addr = "/Reminder/ReminderLogin.do";
			model.addAttribute("url", addr);
			return "/redirectNArt";
		}
		String enc_id = EncryptUtil.encAES128CBC(user_id);
		
		Map<String, Object> pMap = new HashMap<>();
		
		log.info("enc_id: " + enc_id);
		log.info("schedule_name: " + schedule_name);
		log.info("schedule_time: " + schedule_time);
		log.info("schedule_minute: " + schedule_minute);
		
		pMap.put("user_id", enc_id);
		pMap.put("schedule_name", schedule_name);
		pMap.put("schedule_time", schedule_time);
		pMap.put("schedule_minute", schedule_minute);
		
		int res = mongoScheduleService.deleteSchedule(pMap);
		
		if(res == 1) {
			String url = "/Schedule/ScheduleList.do";
			
			model.addAttribute("url", url);
			log.info(this.getClass().getName() + "deleteList end");
			return "/redirectNArt";
		} else {
			String url = "/Schedule/ScheduleList.do";
			String msg = "삭제를 실패했습니다.";
			
			model.addAttribute("url", url);
			model.addAttribute("msg", msg);
			log.info(this.getClass().getName() + "deleteList end");
			return "/redirect";
		}
	}
	
	@RequestMapping(value = "Schedule/scheduleEdit", method = RequestMethod.POST)
	public String scheduleEdit(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception{
		
		log.info(this.getClass().getName() + "scheduleEdit start");
		
		String user_id = (String) session.getAttribute("user_id");
		if(user_id == null) {
			String addr = "/Reminder/ReminderLogin.do";
			model.addAttribute("url", addr);
			return "/redirectNArt";
		}
		
		String schedule_name = request.getParameter("schedule_name");
		String schedule_alarm = request.getParameter("schedule_alarm");
		String schedule_time = request.getParameter("time");
		String schedule_minute = request.getParameter("minute");
		String insert_date = DateUtil.getDateTime("yyyyMMdd");
		
		String before_schedule_name =  request.getParameter("before_schedule_name"); 	
		String before_schedule_alarm =  request.getParameter("before_schedule_alarm"); 	
		String before_schedule_time =  request.getParameter("before_schedule_time"); 	
		String before_schedule_minute =  request.getParameter("before_schedule_minute"); 	

		String enc_id = EncryptUtil.encAES128CBC(user_id);
		
		Map<String, Object> before_map = new HashMap<>();
		
		log.info("schedule_alarm: " + schedule_alarm);
		
		before_map.put("user_id", enc_id);
		before_map.put("schedule_name", before_schedule_name);
		before_map.put("schedule_alarm", before_schedule_alarm);
		before_map.put("schedule_time", before_schedule_time);
		before_map.put("schedule_minute", before_schedule_minute);
		
		Map<String, Object> after_map = new HashMap<>();
		
		after_map.put("user_id", enc_id);
		after_map.put("schedule_name", schedule_name);
		after_map.put("schedule_alarm", schedule_alarm);
		after_map.put("schedule_time", schedule_time);
		after_map.put("schedule_minute", schedule_minute);
		after_map.put("insert_date", insert_date);
		
		int res = mongoScheduleService.updateSchedule(before_map, after_map);
		if(res == 0) {
			String msg = "수정을 실패했습니다.";
			String url = "/Schedule/ScheduleList.do";
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
			
			return "/redirect";
		}
		String url = "/Schedule/ScheduleList.do";
		
		model.addAttribute("url", url);
		
		log.info(this.getClass().getName() + "scheduleEdit end");
		
		return "/redirectNArt";
	}
	


}