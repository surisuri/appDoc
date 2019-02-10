package com.lkssoft.appDoc.schedule.web;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.lkssoft.appDoc.com.vo.ResultsVO;
import com.lkssoft.appDoc.schedule.svc.ScheduleSEI;
import com.lkssoft.appDoc.schedule.svc.ScheduleSMSVO;
import com.lkssoft.appDoc.schedule.svc.ScheduleVO;

@EnableWebMvc
@Controller
@PropertySource({ "classpath:property/sms.properties" })
public class ScheduleController {
     
	@Autowired
	private ScheduleSEI scheduleSEI;
	
	@Value("${sms.NCPServiceId}")
	private String serviceId;
	
	@Value("${sms.nCPAuthKey}")
	private String nCPAuthKey;
	
	@Value("${sms.nCPServiceSecret}")
	private String nCPServiceSecret; 
	
	@Value("${sms.type}")
	private String type;

	@Value("${sms.contentType}")
	private String contentType;
	
	@Value("${sms.contryCode}")
	private String countryCode;

	@Value("${sms.from}")
	private String from;

	@Value("${sms.to}")
	private List<String> to;
	
	/**
	 * 
	 * @param commandMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/registerSchedule", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ScheduleVO registerSchedule(HttpServletRequest req, ScheduleVO scheduleVo) throws Exception {
		ScheduleVO result = new ScheduleVO();
    		try {
	    		
    			// 
    			if ( StringUtils.isEmpty( scheduleVo.getScheduleId() )) {
	    			scheduleSEI.insertSchedule(scheduleVo);
	    		
	    		//
	    		}else {

	    			scheduleSEI.updateSchedule(scheduleVo);
	    			result.setResult("suc");
	    			
	    			// SMS 전송
	    			String uriTemp = "https://api-sens.ncloud.com/v1/sms/services/"
	    							+serviceId
	    							+"/messages";
	    			URI uri = URI.create(uriTemp); 
	    			HttpHeaders headers = new HttpHeaders();
	    			headers.add("X-NCP-auth-key", nCPAuthKey);
	    			headers.add("X-NCP-service-secret", nCPServiceSecret);
	    			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	    			
	    			// ScheduleSMSVO 세팅
	    			ScheduleSMSVO smsVO = new ScheduleSMSVO();
	    			smsVO.setType(type);
	    			smsVO.setContentType(contentType);
	    			smsVO.setCountryCode(countryCode);
	    			smsVO.setFrom(from);
	    			smsVO.setTo(to);
	    			/*
	    			List<String> to = new ArrayList<String>();
	    			to.add(new String("01071600229"));
	    			smsVO.setTo(to);
	    			*/
	    			smsVO.setContent("예약되었습니다.");
	    			
	    			// HttpEntity 
	    			HttpEntity requestEntity = new HttpEntity(smsVO, headers);

	    			// RestTemplate
	    			RestTemplate restTemplate = new RestTemplate();
	    			try {
	    				restTemplate.exchange(uri,HttpMethod.POST, requestEntity, ScheduleSMSVO.class);
	    			}catch(RestClientException e) {
	    				result.setResult(e.toString());
	    			}
	    		}
	    		
	    	}catch(Exception e) {
	    		result.setResult(e.toString());
	    	}

	    	return result;
    }

	/**
	 * 
	 * @param commandMap
	 * @return ModelAndView
	 * @throws Exception
	 */
    @RequestMapping(value="/cancelSchedule", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ScheduleVO cancelSchedule(HttpServletRequest req, ScheduleVO scheduleVo) throws Exception{
    		ScheduleVO result = new ScheduleVO();
    		try {
    			scheduleSEI.cancelSchedule(scheduleVo);
	    		
	    		result.setResult("suc");
	    	}catch(Exception e) {
	    		result.setResult(e.toString());
	    	}

	    	return result;
    }
    
	/**
	 * 
	 * @param commandMap
	 * @return ModelAndView
	 * @throws Exception
	 */
    @RequestMapping(value="/deleteSchedule", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ScheduleVO deleteSchedule(HttpServletRequest req, ScheduleVO scheduleVo) throws Exception{
    		ScheduleVO result = new ScheduleVO();
    		try {
	    		scheduleSEI.deleteSchedule(scheduleVo);
	    		
	    		result.setResult("suc");
	    	}catch(Exception e) {
	    		result.setResult(e.toString());
	    	}

	    	return result;
    }
    
    /**
     * 
     * @param req httprservletequest 媛앹껜 
     * @param startDateTime
     * @param endDateTime
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/selectListSchedule", method= RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResultsVO selectListSchedule(HttpServletRequest req, 
    											  String startDate,
    											  String endDate) throws Exception{
  
      	List<Map<String, Object>> results = new ArrayList<Map<String, Object>>(); 		
    		ResultsVO resultsVo = new ResultsVO();
    		
    		try {
    			
    			ScheduleVO searchVO = new ScheduleVO();
    			searchVO.setStartDate(startDate);
    			searchVO.setEndDate(endDate);
    			results = scheduleSEI.selectListEvent(searchVO);
    			
    			resultsVo.setResult(true);
    			resultsVo.setObjList(results);
    			
    		}catch(Exception e) {
    			resultsVo.setResult(false);
    			resultsVo.setMsg(e.toString());
    		}

    		return resultsVo;
    }
    
	/**
	 * 
	 * @param commandMap
	 * @return ModelAndView
	 * @throws Exception
	 */
    @RequestMapping(value="/registerScheduleBatch", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResultsVO registerScheduleBatch(HttpServletRequest req, 
    														 String startDate,
    														 String endDate) throws Exception{
		ResultsVO resultsVo = new ResultsVO();
    		try {
	    		
	    		scheduleSEI.insertBatchSchedule(startDate, endDate);
    			
    			resultsVo.setResult(true);
	    	}catch(Exception e) {
	    		resultsVo.setResult(false);
	    		resultsVo.setMsg(e.toString());
	    	}

	    	return resultsVo;
    }    
}