package com.lkssoft.appDoc.schedule.dao;
 
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lkssoft.appDoc.com.dao.AbstractDAO;
import com.lkssoft.appDoc.schedule.svc.ScheduleVO;  

@Repository("ScheduleDAO")
public class ScheduleDAO extends AbstractDAO{

	/**
	 * 
	 * @param scheduelMap
	 * @return Object
	 * @exception Exception 
	 */
	public Object insertSchedule(ScheduleVO scheculeVo) throws Exception{ 	
	    return insert("Schedule.insertSchedule", scheculeVo);	
	}
	
	/**
	 * 
	 * @param scheculeVo
	 * @return Object
	 * @throws Exception
	 */
	public Object updateSchedule(ScheduleVO scheculeVo) throws Exception{ 	
	    return insert("Schedule.updateSchedule", scheculeVo);	
	}	
	
	/**
	 * 
	 * @param scheculeVo
	 * @return Object
	 * @throws Exception
	 */
	public Object deleteSchedule(ScheduleVO scheduleVo) throws Exception{ 	
	    return insert("Schedule.deleteSchedule", scheduleVo);	
	}
	
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String nextSequeceNumber() throws Exception{
		return (String)selectOne("Schedule.nextSequeceNumber");
	}
	
	/**
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectListEvent(ScheduleVO searchVO) throws Exception{
		List<Map<String, Object>> results = selectList("Schedule.selectListEvent", searchVO);
		
		return results;
	}
}