package com.lkssoft.appDoc.schedule.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.lkssoft.appDoc.com.vo.ResultsVO;
import com.lkssoft.appDoc.schedule.svc.ScheduleSEI;
import com.lkssoft.appDoc.schedule.svc.ScheduleVO;

@EnableWebMvc
@Controller
public class ScheduleController {
     
	@Autowired
	private ScheduleSEI scheduleSEI;
	
	/**
	 * 에약을 등록/변경한다.
	 * 
	 * @param commandMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/registerSchedule", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ScheduleVO registerSchedule(HttpServletRequest req, ScheduleVO scheduleVo) throws Exception {
		ScheduleVO result = new ScheduleVO();
    		try {
	    		
    			// 예약 최초 등록은 
    			// 담당자가 일괄 등록하거나 개별 등록하므로 SMS 발송 불필요
    			if ( StringUtils.isEmpty( scheduleVo.getScheduleId() )) {
	    			scheduleSEI.insertSchedule(scheduleVo);
	    			result.setResult("suc");
	    		
	    		// 예약 등록/변경/삭제는 간호사가 수행하고 담당자에게 SMS 발송	
	    		}else {

	    			scheduleSEI.updateSchedule(scheduleVo);
	    			result.setResult("suc");
	    			
	    			scheduleSEI.sendSMS(scheduleVo, false);  // 예약, 예약변경 SMS 발송
	    		}
	    		
	    	}catch(Exception e) {
	    		result.setResult(e.toString());
	    	}

	    	return result;
    }

	/**
	 * drag & drop에 의해 에약을 변경한다.
	 * 
	 * @param commandMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSchedule", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ScheduleVO updateSchedule(HttpServletRequest req, ScheduleVO scheduleVo) throws Exception {
		ScheduleVO result = new ScheduleVO();
    		try {
	    		
				scheduleSEI.updateSchedule(scheduleVo);
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
    @RequestMapping(value="/cancelSchedule", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ScheduleVO cancelSchedule(HttpServletRequest req, ScheduleVO scheduleVo) throws Exception{
    		ScheduleVO result = new ScheduleVO();
    		try {
    			String name = scheduleVo.getPatientName();
    			scheduleSEI.cancelSchedule(scheduleVo);
	    		result.setResult("suc");
	    		
	    		scheduleVo.setPatientName(name); // 환자이름 다시 세팅
	    		scheduleSEI.sendSMS(scheduleVo, true); // 예약취소 SMS 발송
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