package com.lkssoft.appDoc.charge.dao;
 
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lkssoft.appDoc.charge.svc.MedicalChargeVO;
import com.lkssoft.appDoc.com.dao.AbstractDAO;  

@Repository("MedicalChargeDAO")
public class MedicalChargeDAO extends AbstractDAO{

	/**
	 * 
	 * @param scheduelMap
	 * @return Object
	 * @exception Exception 
	 */
	public Object insertMedicalCharge(MedicalChargeVO medicalChargeVO) throws Exception{ 	
	    return insert("MedicalCharge.insertMedicalCharge", medicalChargeVO);	
	}
	
	/**
	 * 
	 * @param scheduelMap
	 * @return Object
	 * @exception Exception 
	 */
	public Object updateMedicalCharge(MedicalChargeVO medicalChargeVO) throws Exception{ 	
	    return insert("MedicalCharge.updateMedicalCharge", medicalChargeVO);	
	}
	
	/**
	 * 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectListMedicalCharge() throws Exception{
		List<Map<String, Object>> results = selectList("MedicalCharge.selectListMedicalCharge");
		
		return results;
	}

	/**
	 * 
	 * @param medicalChargeVO
	 * @return
	 * @throws Exception
	 */
	public Object deleteMedicalCharge(MedicalChargeVO medicalChargeVO) throws Exception{
		return delete("MedicalCharge.deleteMedicalCharge", medicalChargeVO);
	}
}