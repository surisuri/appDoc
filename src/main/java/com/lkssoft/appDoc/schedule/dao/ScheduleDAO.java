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
	    return insert("com.lkssoft.appDoc.schedule.dao.insertSchedule", scheculeVo);	
	}
	
	/**
	 * 
	 * @param scheculeVo
	 * @return Object
	 * @throws Exception
	 */
	public Object updateSchedule(ScheduleVO scheculeVo) throws Exception{ 	
	    return update("com.lkssoft.appDoc.schedule.dao.updateSchedule", scheculeVo);	
	}	
	
	/**
	 * 
	 * @param scheculeVo
	 * @return Object
	 * @throws Exception
	 */
	public Object deleteSchedule(ScheduleVO scheduleVo) throws Exception{ 	
	    return update("com.lkssoft.appDoc.schedule.dao.deleteSchedule", scheduleVo);	
	}
	
	/**
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String nextSequeceNumber() throws Exception{
		return (String)selectOne("com.lkssoft.appDoc.schedule.dao.nextSequeceNumber");
	}
	
	/**
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectListEvent(ScheduleVO searchVO) throws Exception{
		List<Map<String, Object>> results = selectList("com.lkssoft.appDoc.schedule.dao.selectListEvent", searchVO);
		
		return results;
	}
}