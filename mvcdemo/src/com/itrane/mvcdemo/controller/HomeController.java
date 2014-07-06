package com.itrane.mvcdemo.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	final static private Logger log = LoggerFactory.getLogger(HomeController.class); 

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String navbar(Model model) {
		log.debug("");
		model.addAttribute("today", Calendar.getInstance().getTime());
		return "/home/home";
	}
}
