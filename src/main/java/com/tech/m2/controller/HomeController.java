package com.tech.m2.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.tech.m2.dao.BusStopDao;
import com.tech.m2.dto.BusInfoDto;
import com.tech.m2.dto.Traffic_AccInfoDto;
import com.tech.m2.dto.WeatherDto;
import com.tech.m2.service.MapService;
import com.tech.m2.serviceInter.MapServiceInter;
import com.tech.m2.traffic_api.Seoul_traffic_api;
import com.tech.m2.traffic_api.XmlPaser;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.tech.m2.traffic_api.Seoul_traffic_api.Traffic_crawling;
import static com.tech.m2.traffic_api.Seoul_traffic_api.weather_API;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	private SqlSession sqlSession;
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
		model.addAttribute("sqlSession", sqlSession);
		model.addAttribute("serverTime", formattedDate );

		BusStopDao busStopDao = sqlSession.getMapper(BusStopDao.class);
		ArrayList<BusInfoDto> dto = busStopDao.getInfo();
		System.out.println("---------------------------");
		System.out.println("버스정류장 정보");
		System.out.println(dto.get(0).getARS_ID());
		System.out.println(dto.get(0).getName());
		System.out.println(dto.get(0).getdPx_longitude());
		System.out.println(dto.get(0).getdPy_latitude());
		System.out.println("---------------------------");


		return "home";
	}

	@RequestMapping(value = "bushome", method = RequestMethod.GET)
	public String busTab(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);



		return "bushome";
	}
//	@RequestMapping(value = "test", method = RequestMethod.GET)
//	public String test(Model model) {
//		MapServiceInter service = new MapService();
//		XmlPaser xmlPaser = new XmlPaser();

//		ArrayList<Traffic_RoadInfoDto> road_info = Seoul_traffic_api.traffic_API("RoadInfo",1,1000,02);


//		ArrayList<Traffic_SpotInfoDto> info = new ArrayList<>();
//		info = xmlPaser.spotInfo_Parsing(Seoul_traffic_api.traffic_API("SpotInfo",1,1000));
//		model.addAttribute("info", info);
//		return "test";
//	}

//	@RequestMapping(value = "test2", method = RequestMethod.GET)
//	public String test2(Model model) {
//		MapServiceInter service = new MapService();
//		XmlPaser xmlPaser = new XmlPaser();
//		String accinfo_xml = Seoul_traffic_api.traffic_API("AccInfo",1,100);
//		ArrayList<Traffic_AccInfoDto> info = xmlPaser.Xml_Parsing(accinfo_xml,"accInfo",Traffic_AccInfoDto.class);
//		model.addAttribute("accInfo", info);
//		return "test2";
//	}

	@RequestMapping(value = "test", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public ArrayList<Traffic_AccInfoDto> test() {
		MapServiceInter service = new MapService();
		XmlPaser xmlPaser = new XmlPaser();
		String accinfo_xml = Seoul_traffic_api.traffic_API("AccInfo", 1, 100);
		ArrayList<Traffic_AccInfoDto> info = xmlPaser.Traffic_Xml_Parsing(accinfo_xml, "accInfo", Traffic_AccInfoDto.class);
		return info;
	}

	@RequestMapping(value = "test2", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public ArrayList<Traffic_AccInfoDto> test2() {
		MapServiceInter service = new MapService();
		XmlPaser xmlPaser = new XmlPaser();
		String accinfo_xml = Seoul_traffic_api.traffic_API("AccInfo", 1, 100);
		ArrayList<Traffic_AccInfoDto> info = xmlPaser.Traffic_Xml_Parsing(accinfo_xml, "accInfo", Traffic_AccInfoDto.class);
//		String data = Traffic_crawling();
		WeatherDto weather_data = new WeatherDto();
		weather_data = xmlPaser.Weather_Xml_Parsing(weather_API());

		return info;
	}

	@RequestMapping(value = "test1", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public WeatherDto test1() {
		MapServiceInter service = new MapService();
		XmlPaser xmlPaser = new XmlPaser();
		WeatherDto weather_data = new WeatherDto();
		weather_data = xmlPaser.Weather_Xml_Parsing(weather_API());
//		BusStopDao busStopDao = sqlSession.getMapper(BusStopDao.class);
//		ArrayList<BusInfoDto> dto = busStopDao.getInfo();
//		System.out.println("---------------------------");
//		System.out.println("버스정류장 정보");
//		System.out.println(dto.get(0).getARS_ID());
//		System.out.println(dto.get(0).getName());
//		System.out.println(dto.get(0).getdPx_longitude());
//		System.out.println(dto.get(0).getdPy_latitude());
//		System.out.println("---------------------------");

		return weather_data;
	}



	@RequestMapping(value = "bustest", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public ArrayList<BusInfoDto> bustest() {
		BusStopDao busStopDao = sqlSession.getMapper(BusStopDao.class);
		MapServiceInter service = new MapService();
		XmlPaser xmlPaser = new XmlPaser();
		ArrayList<BusInfoDto> dto = busStopDao.getInfo();
		return dto;
	}

}
