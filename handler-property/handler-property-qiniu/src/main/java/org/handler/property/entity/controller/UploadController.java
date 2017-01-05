package org.handler.property.entity.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.handler.property.entity.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/upload")
public class UploadController {

	@Autowired
	private UploadService uploadService;
	
	@RequestMapping("/toIndex")
	@ResponseBody
	public ModelAndView uploadToken(HttpServletRequest request,HttpServletResponse response,ModelAndView modelAndView) {
		String token = uploadService.getToken();
		String domainname = uploadService.getDomainName();
		modelAndView.addObject("uptoken", token);
		modelAndView.addObject("domainname",domainname);
		modelAndView.setViewName("index");
		try {
			Method method = Class.forName("UploadController").getMethod("uploadToken");
			Object obj = Class.forName("UploadController").newInstance();
			method.invoke(obj, "godd");
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelAndView;
	}
}
