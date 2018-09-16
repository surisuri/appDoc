package com.lkssoft.appDoc.schedule.svc;

import java.util.List;
import java.util.Map;

public interface ScheduleSEI{
	
	/**
	 * 
	 * @param scheduelMap
	 * @return Object
	 * @exception Exception 
	 */
	public Object insertSchedule(ScheduleVO scheduleVo) throws Exception;
	
	/**
	 * 
	 * @param scheculeVo
	 * @return Object
	 * @throws Exception
	 */
	public Object updateSchedule(ScheduleVO scheduleVo) throws Exception;
	
	/**
	 * 
	 * @param scheculeVo
	 * @return Object
	 * @throws Exception
	 */
	public Object cancelSchedule(ScheduleVO scheduleVo) throws Exception;

	/**
	 * 
	 * @param scheculeVo
	 * @return Object
	 * @throws Exception
	 */
	public Object deleteSchedule(ScheduleVO scheduleVo) throws Exception;
	
	/**
	 * 
	 * @param searchVO
	 * @return List<ScheduleVO> 
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectListEvent(ScheduleVO searchVO) throws Exception;
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return Object
	 * @exception Exception 
	 */
	public void insertBatchSchedule(String startDate, String endDate) throws Exception;	
}
