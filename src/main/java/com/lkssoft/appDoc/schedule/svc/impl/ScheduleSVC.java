package com.lkssoft.appDoc.schedule.svc.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lkssoft.appDoc.schedule.dao.ScheduleDAO;
import com.lkssoft.appDoc.schedule.svc.ScheduleSEI;
import com.lkssoft.appDoc.schedule.svc.ScheduleVO;

@Service
public class ScheduleSVC implements ScheduleSEI{
	
	/**
	 * schedule
	 */
	@Resource(name="ScheduleDAO")
	private ScheduleDAO scheduleDAO;

	/**
	 * 
	 * @param scheculeVo
	 * @return Object
	 * @throws Exception
	 */
	@Override
	public Object updateSchedule(ScheduleVO scheduleVo) throws Exception {
		scheduleVo.setUpdateUsrId(SecurityContextHolder.getContext().getAuthentication().getName());
		
	    if( StringUtils.isEmpty( scheduleVo.getPatientName().trim() ) ) { 
	    		scheduleVo.setEventStatus("01"); 
	    }else {
	    		scheduleVo.setEventStatus("02"); 
		}
		return scheduleDAO.updateSchedule(scheduleVo);
	}

	/**
	 * 
	 * @param scheculeVo
	 * @return Object
	 * @throws Exception
	 */
	@Override
	public Object cancelSchedule(ScheduleVO scheduleVo) throws Exception {
		
	    scheduleVo.setEventStatus("01"); 
	    scheduleVo.setPatientName(null);
	    scheduleVo.setPrescriberUsrNm(null);
	    scheduleVo.setSimpleMsgCtnt(null);
	    scheduleVo.setDeleteYn("N");
		scheduleVo.setUpdateUsrId(null);
	    
		return scheduleDAO.updateSchedule(scheduleVo);
	}
	
	/**
	 * 
	 * @param searchVO 
	 * @return List<ScheduleVO> 
	 * @throws Exception 
	 */
	@Override
	public List<Map<String, Object>> selectListEvent(ScheduleVO searchVO) throws Exception {
		return scheduleDAO.selectListEvent(searchVO);
	}

	/**
	 * 예약을 등록한다
	 * 
	 * @param scheduleVo
	 * @return Object
	 * @throws Exceptio 
	 * 
	 */
	@Override
	public Object insertSchedule(ScheduleVO scheduleVo) throws Exception {
		if( StringUtils.isEmpty(scheduleVo.getScheduleId()) ) {
			scheduleVo.setScheduleId(scheduleDAO.nextSequeceNumber());
		}
		
		String patientName = scheduleVo.getPatientName();
		if( StringUtils.isEmpty(patientName)) {
			scheduleVo.setEventStatus("01");  
		}else {
			scheduleVo.setEventStatus("02");  
		}
			
	    scheduleVo.setCreateUsrId( SecurityContextHolder.getContext().getAuthentication().getName() );
	    scheduleVo.setUpdateUsrId(SecurityContextHolder.getContext().getAuthentication().getName() );
	    scheduleVo.setDeleteYn("N");
		
		return scheduleDAO.insertSchedule(scheduleVo);
	}

	/**
	 * 
	 * @param scheculeVo
	 * @return Object
	 * @throws Exception
	 */
	@Override
	public Object deleteSchedule(ScheduleVO scheduleVo) throws Exception{
		scheduleVo.setDeleteYn("Y");
		return scheduleDAO.deleteSchedule(scheduleVo);
	}
	
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return Object
	 * @exception Exception 
	 */
	@Override
	public void insertBatchSchedule(String strStartDate, String strEndDate) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(strStartDate);
		Calendar curCal = Calendar.getInstance();
		curCal.setTime(startDate);
		
		Date endDate   = sdf.parse(strEndDate);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);		
		endCal.add(Calendar.DAY_OF_WEEK, 1);
		
		while( curCal.before(endCal) ) {
			int day = curCal.get(Calendar.DAY_OF_WEEK);
			
			ScheduleVO scheduleVo = new ScheduleVO();
		    scheduleVo.setEventDate( sdf.format(curCal.getTime()) );
		    scheduleVo.setEventStatus("01"); 
		    scheduleVo.setExamUsrNm("김경미");
		    scheduleVo.setScheduleBatchCreateYn("Y");
			
			if( day == 2 || day == 3 || day == 4 || day == 5 || day == 6 ) {  // mon, tuesday, thursday, friday
				scheduleVo.setEventStartTime("09:00");
				scheduleVo.setEventEndTime("11:00");
				this.insertSchedule(scheduleVo);
				
				scheduleVo.setScheduleId(null);
				scheduleVo.setEventStartTime("11:00");
				scheduleVo.setEventEndTime("13:00");
				this.insertSchedule(scheduleVo);
			}
			
			curCal.add(Calendar.DAY_OF_WEEK, 1);
		}
		
	}
}