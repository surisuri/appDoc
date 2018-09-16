package com.lkssoft.appDoc.charge.svc;

import java.util.List;
import java.util.Map;

public interface MedicalChargeSEI{
	
	/**
	 * 
	 * @param medicalChargeVO
	 * @return Object
	 * @exception Exception 
	 */
	public Object mergeMedicalCharge(MedicalChargeVO medicalChargeVO) throws Exception;
	
	/**
	 * 
	 * @param searchVO
	 * @return List<Map<String, Object>> 
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectListMedicalCharge() throws Exception;
	
	/**
	 * 
	 * @param medicalChargeVO  
	 * @return Object 
	 * @throws Exception
	 */
	public Object deleteMedicalCharge(MedicalChargeVO medicalChargeVO) throws Exception;	
}
