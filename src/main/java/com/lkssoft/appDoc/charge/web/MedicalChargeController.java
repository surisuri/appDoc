package com.lkssoft.appDoc.charge.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lkssoft.appDoc.charge.svc.MedicalChargeSEI;
import com.lkssoft.appDoc.charge.svc.MedicalChargeVO;
import com.lkssoft.appDoc.com.vo.ResultsVO;

@Controller
public class MedicalChargeController {
	
	@Autowired
	private MedicalChargeSEI medicalChargeSEI;

	/**
	 * 
	 * @param commandMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/medicalChargeView")
	public ModelAndView medicalChargeView(Map<String, Object> commandMap) throws Exception {
		ModelAndView mav = new ModelAndView("charge/medicalCharge");

		SecurityContext context = SecurityContextHolder.getContext();
		mav.addObject("c_username", context.getAuthentication().getName());
		
		return mav;
	}

	/**
	 * 
	 * @param
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectListMedicalCharge", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> selectListMedicalCharge(HttpServletRequest req) throws Exception {
		MedicalChargeVO mdchargeVO = new MedicalChargeVO();
		List<Map<String, Object>> results = medicalChargeSEI.selectListMedicalCharge(mdchargeVO);
		return results;
	}

	/**
	 * 
	 * @param commandMap
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/mergeMedicalCharge", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultsVO mergeMedicalCharge(HttpServletRequest req, MedicalChargeVO medicalChargeVo)
			throws Exception {
		ResultsVO resultsVo = new ResultsVO<MedicalChargeVO>();
		try {
			medicalChargeSEI.mergeMedicalCharge(medicalChargeVo);
			resultsVo.setResult(true);
		} catch (Exception e) {
			resultsVo.setResult(false);
			resultsVo.setMsg(e.toString());
		}

		return resultsVo;
	}
	
	/**
	 * 
	 * @param req
	 * @param medicalChargeVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteMedicalCharge", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResultsVO deleteMedicalCharge(HttpServletRequest req, MedicalChargeVO medicalChargeVo) throws Exception{
		ResultsVO resultsVo = new ResultsVO<MedicalChargeVO>();

		try {
			medicalChargeSEI.deleteMedicalCharge(medicalChargeVo);
			resultsVo.setResult(true);
		} catch (Exception e) {
			resultsVo.setResult(false);
			resultsVo.setMsg(e.toString());
		}

		return resultsVo;
	}
}