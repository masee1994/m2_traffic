package com.tech.m2.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.tech.m2.dto.Traffic_RoadInfoDto;
import com.tech.m2.dto.Traffic_SpotInfoDto;
import com.tech.m2.service.MapService;
import com.tech.m2.serviceInter.MapServiceInter;
import com.tech.m2.traffic_api.Seoul_traffic_api;
import com.tech.m2.traffic_api.XmlPaser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );

		return "home";
	}
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test(Model model) {
		MapServiceInter service = new MapService();
		XmlPaser xmlPaser = new XmlPaser();

//		ArrayList<Traffic_RoadInfoDto> road_info = Seoul_traffic_api.traffic_API("RoadInfo",1,1000,02);


//		ArrayList<Traffic_SpotInfoDto> info = new ArrayList<>();
//		info = xmlPaser.spotInfo_Parsing(Seoul_traffic_api.traffic_API("SpotInfo",1,1000));
//		model.addAttribute("info", info);
		return "test";
	}
	
}
