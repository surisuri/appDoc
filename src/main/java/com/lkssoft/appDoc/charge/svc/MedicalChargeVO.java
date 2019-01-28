package com.lkssoft.appDoc.charge.svc;
        
import com.lkssoft.appDoc.com.vo.CommonVO;

public class MedicalChargeVO extends CommonVO{

	private String inspectionCode;
	private String inspectionName;
    private String treatDvsCd;
    private String treatDvsNm;
	private String remark;
	private int charge;
	private String createDt;
	private String createUsrId;
	private String updateDt;
	private String updateUsrId;
	
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
	public String getInspectionCode() {
		return inspectionCode;
	}
	public void setInspectionCode(String inspectionCode) {
		this.inspectionCode = inspectionCode;
	}
	public String getInspectionName() {
		return inspectionName;
	}
	public void setInspectionName(String inspectionName) {
		this.inspectionName = inspectionName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getCharge() {
		return charge;
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	public String getTreatDvsCd() {
		return treatDvsCd;
	}
	public void setTreatDvsCd(String treatDvsCd) {
		this.treatDvsCd = treatDvsCd;
	}
	public String getTreatDvsNm() {
		return treatDvsNm;
	}
	public void setTreatDvsNm(String treatDvsNm) {
		this.treatDvsNm = treatDvsNm;
	}
}
