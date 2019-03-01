package com.lkssoft.appDoc.schedule.svc.impl;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.lkssoft.appDoc.charge.svc.MedicalChargeSEI;
import com.lkssoft.appDoc.charge.svc.MedicalChargeVO;
import com.lkssoft.appDoc.schedule.dao.ScheduleDAO;
import com.lkssoft.appDoc.schedule.svc.ScheduleSEI;
import com.lkssoft.appDoc.schedule.svc.ScheduleSMSVO;
import com.lkssoft.appDoc.schedule.svc.ScheduleVO;

@Service
@PropertySource({ "classpath:property/sms.properties" })
public class ScheduleSVC implements ScheduleSEI{
	
	/**
	 * schedule
	 */
	@Resource(name="ScheduleDAO")
	private ScheduleDAO scheduleDAO;

	/**
	 * mediacalcharge interface
	 */
	@Autowired
	private MedicalChargeSEI medicalChargeSEI;
	
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
	    scheduleVo.setTreatDvsCode("01");
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

	/**
	 * 담당자에게 SMS 문자를 전송한다 
	 * 
	 * @param scheduleVo 예약정보
	 * @param boolean isDelete 예약취소 여부
	 * @return String 
	 */
	@Override
	public String sendSMS(ScheduleVO scheduleVo, boolean isDelete) throws Exception{
		
		String result = "success";
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
	
		StringBuffer strBuf = new StringBuffer();
		if( isDelete == true) {
			strBuf.append("[예약취소]\n");
		}else {
			if( "02".equals(scheduleVo.getOldEventStatus()) ) {
				strBuf.append("[예약변경]\n");
			}else {
				strBuf.append("[예약]\n");
			}
		}
		strBuf.append("날짜:").append(scheduleVo.getEventDate()+"\n");
		strBuf.append("시간:").append(scheduleVo.getEventStartTime()+"\n");
		strBuf.append("이름:").append(scheduleVo.getPatientName()+"\n");
		
		MedicalChargeVO mdVO = new MedicalChargeVO();
		mdVO.setInspectionCode(scheduleVo.getTreatDvsCode());
		List<Map<String, Object>> treatList = medicalChargeSEI.selectListMedicalCharge(mdVO);
		if( treatList.get(0) != null ) {
			strBuf.append("치료/검사:").append( treatList.get(0).get("INSPECTION_NAME") );
		}else {
			strBuf.append("치료/검사:").append("-");
		}
		
		smsVO.setContent(strBuf.toString());
		
		// HttpEntity 
		HttpEntity requestEntity = new HttpEntity(smsVO, headers);
	
		// RestTemplate
		RestTemplate restTemplate = new RestTemplate();
		try {
			restTemplate.exchange(uri,HttpMethod.POST, requestEntity, ScheduleSMSVO.class);
		}catch(RestClientException e) {
			result = "fail";
		}
		
		return result;
	}
}