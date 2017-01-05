package org.handler.property.redis.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.handler.property.redis.persistence.entity.Association;
import org.handler.property.redis.persistence.entity.TabDeveloper;
import org.handler.property.redis.persistence.entity.User;
import org.handler.property.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/toIndex")
	@ResponseBody
	public ModelAndView toIndex(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView){
		
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping("/saveDeveloper")
	@ResponseBody
	public void saveUser(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("good", "yes i do");
		Association a = new Association();
		a.setAssociationName("Daisy");
		a.setAssociationAge("24");
		request.getSession().setAttribute("association", a);
		TabDeveloper td = new TabDeveloper();
		td.setId(UUID.randomUUID().toString().replace("-", ""));
		String i =UUID.randomUUID().toString().replace("-", "").substring(0, 10);
		td.setAccount("acount"+i);
		td.setAddress("address"+i);
		td.setAppId("appId"+i);
		td.setDevelopName(""+i);
		
		
		userService.addDeveloper(td);
		Map<String,String> result=new HashMap<String,String>();
		
		result.put("state", "success");
		result.put("message", "success");
		try {
			response.getWriter().print(JSON.toJSONString(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/updateDeveloper")
	@ResponseBody
	public void updateDeveloper(HttpServletRequest request,HttpServletResponse response){
		Association a = new Association();
		a.setAssociationName("Daisy JING JING");
		a.setAssociationAge("24");
		request.getSession().setAttribute("association", a);
		TabDeveloper td = new TabDeveloper();
		td.setId("1539a621c7584f8b819853f172781a6f");
		String i =UUID.randomUUID().toString().replace("-", "").substring(0, 10);
		td.setAccount("acount"+i);
		td.setAddress("address"+i);
		td.setAppId("appId"+i);
		td.setDevelopName(""+i);
		
		
		userService.updateDeveloper(td);
		Map<String,String> result=new HashMap<String,String>();
		
		result.put("state", "success");
		result.put("message", "success");
		try {
			response.getWriter().print(JSON.toJSONString(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getDeveloper")
	@ResponseBody
	public void getUser(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("good", "YES I DO");
		request.getServletContext().setAttribute("goodman", "I am good man");
		TabDeveloper td = userService.getDeveloperById("1539a621c7584f8b819853f172781a6f");
		Map<String,String> result=new HashMap<String,String>();
		
		result.put("state", "success");
		result.put("message", "success");
		try {
			response.getWriter().print(JSON.toJSONString(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/reloadCache")
	@ResponseBody
	public void reloadCache(HttpServletRequest request,HttpServletResponse response){
		request.removeAttribute("good");
		request.getServletContext().removeAttribute("goodman");
		request.getSession().removeAttribute("association");
		userService.reloadCache();
	}
	
}
