package com.lkssoft.appDoc.schedule.svc;

import com.lkssoft.appDoc.com.vo.CommonVO;

public class ScheduleVO extends CommonVO{

	private String scheduleId;
	private String eventDate;
	private String eventStartTime;
	private String eventEndTime;
	private String patientName;
	private String treatDvsCode;
	private String treatDvsName;
	private String simpleMsgCtnt;
	private String eventStatus;
	private String oldEventStatus;
	private String examUsrNm;
	private String prescriberUsrNm;
	private String scheduleBatchCreateYn;
	private String deleteYn;
	private String createDt;
	private String createUsrId;
	private String updateDt;
	private String updateUsrId;
	
	/**
	 * �떆�옉�씪�옄(議고쉶議곌굔)
	 */
	private String startDate;
	/**
	 * 醫낅즺�씪�옄(議고쉶議곌굔) 
	 */
	private String endDate;
	
	/**
	 * �떆�옉�씪�떆
	 */
	private String startDateTime;
	
	/**
	 * 醫낅즺�씪�떆 
	 */
	private String endDateTime;
	
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	public String getEventStartTime() {
		return eventStartTime;
	}
	public void setEventStartTime(String eventStartTime) {
		this.eventStartTime = eventStartTime;
	}
	public String getEventEndTime() {
		return eventEndTime;
	}
	public void setEventEndTime(String eventEndTime) {
		this.eventEndTime = eventEndTime;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getTreatDvsCode() {
		return treatDvsCode;
	}
	public void setTreatDvsCode(String treatDvsCode) {
		this.treatDvsCode = treatDvsCode;
	}
	public String getSimpleMsgCtnt() {
		return simpleMsgCtnt;
	}
	public void setSimpleMsgCtnt(String simpleMsgCtnt) {
		this.simpleMsgCtnt = simpleMsgCtnt;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public String getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	public String getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getCreateUsrId() {
		return createUsrId;
	}
	public void setCreateUsrId(String createUsrId) {
		this.createUsrId = createUsrId;
	}
	public String getUpdateUsrId() {
		return updateUsrId;
	}
	public void setUpdateUsrId(String updateUsrId) {
		this.updateUsrId = updateUsrId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	public String getExamUsrNm() {
		return examUsrNm;
	}
	public void setExamUsrNm(String examUsrNm) {
		this.examUsrNm = examUsrNm;
	}
	public String getPrescriberUsrNm() {
		return prescriberUsrNm;
	}
	public void setPrescriberUsrNm(String prescriberUsrNm) {
		this.prescriberUsrNm = prescriberUsrNm;
	}
	public String getScheduleBatchCreateYn() {
		return scheduleBatchCreateYn;
	}
	public void setScheduleBatchCreateYn(String scheduleBatchCreateYn) {
		this.scheduleBatchCreateYn = scheduleBatchCreateYn;
	}
	/**
	 * @return the treatDvsName
	 */
	public String getTreatDvsName() {
		return treatDvsName;
	}
	/**
	 * @param treatDvsName the treatDvsName to set
	 */
	public void setTreatDvsName(String treatDvsName) {
		this.treatDvsName = treatDvsName;
	}
	public String getOldEventStatus() {
		return oldEventStatus;
	}
	public void setOldEventStatus(String oldEventStatus) {
		this.oldEventStatus = oldEventStatus;
	}
}
