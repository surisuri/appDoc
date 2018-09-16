package com.lkssoft.appDoc.charge.svc.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lkssoft.appDoc.charge.dao.MedicalChargeDAO;
import com.lkssoft.appDoc.charge.svc.MedicalChargeSEI;
import com.lkssoft.appDoc.charge.svc.MedicalChargeVO;

@Service
public class MedicalChargeSVC implements MedicalChargeSEI{
	
	/**
	 * Medical Charge
	 */
	@Resource(name="MedicalChargeDAO")
	private MedicalChargeDAO medicalChargeDAO;

	/**
	 * 
	 * @param medicalChargeVO
	 * @return Object 
	 * @exception Exception
	 */
	@Override
	public Object mergeMedicalCharge(MedicalChargeVO medicalChargeVO) throws Exception {
		String createUsrId = medicalChargeVO.getCreateUsrId();
		if( StringUtils.isEmpty(createUsrId) ) {
			// createUsrId, createUsrDt, updateUsrId, updateUsrDt 
			medicalChargeVO.setCreateUsrId("kim");
			return medicalChargeDAO.insertMedicalCharge(medicalChargeVO);
		}else {
			return medicalChargeDAO.updateMedicalCharge(medicalChargeVO);
		}
	}

	@Override
	public List<Map<String, Object>> selectListMedicalCharge() throws Exception {
		return medicalChargeDAO.selectListMedicalCharge();
	}

	/**
	 * 
	 * @param medicalChargeVO 
	 * @return Object 
	 * @throws Exception
	 */
	@Override
	public Object deleteMedicalCharge(MedicalChargeVO medicalChargeVO) throws Exception{
		return medicalChargeDAO.deleteMedicalCharge(medicalChargeVO);
	}
	
}