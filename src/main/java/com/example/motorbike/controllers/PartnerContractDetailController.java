package com.example.motorbike.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.PartnerContract;
import com.example.motorbike.models.PartnerContractDetail;
import com.example.motorbike.serviceImpls.MotorbikeServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/partner_contract_detail")
public class PartnerContractDetailController {
	@Autowired
	MotorbikeServiceImpl motorbikeServiceImpl;
	@GetMapping("/addTerm")
	private String addTerm(Model model, @SessionAttribute("partnerContract") PartnerContract partnerContract,
								@SessionAttribute("partnerContractDetail") PartnerContractDetail partnerContractDetail,
								@SessionAttribute("partnerContractDetails") List<PartnerContractDetail> partnerContractDetails,
								@ModelAttribute("partnerContractDetail") PartnerContractDetail modelPartnerContractDetail) {
		partnerContractDetail.setCost(modelPartnerContractDetail.getCost());
		partnerContractDetail.setDateStart(modelPartnerContractDetail.getDateStart());
		partnerContractDetail.setDateEnd(modelPartnerContractDetail.getDateEnd());
		boolean check1 = true;
		boolean check2 = true;
		Motorbike m = partnerContractDetail.getMotorbike();
		Date start1 = partnerContractDetail.getDateStart();
		Date end1 = partnerContractDetail.getDateEnd();
		Date start2 = new Date(0);
		Date end2 = new Date(0);
		for (PartnerContractDetail p : partnerContractDetails) {
			if (p.getMotorbike().getId() == m.getId()) {
				start2 = p.getDateStart();
				end2 = p.getDateEnd();
				check1 = (end1.before(start2) || start1.after(end2));
			}
		}
		for (PartnerContractDetail p : partnerContract.getPartnerContractDetails()) {
			if (p.getMotorbike().getId() == m.getId()) {
				start2 = p.getDateStart();
				end2 = p.getDateEnd();
				check2 = (end1.before(start2) || start1.after(end2));
			}
		}
		if(check1 && check2) {
			List<PartnerContractDetail> l = partnerContract.getPartnerContractDetails();
			l.add(partnerContractDetail);
			partnerContract.setPartnerContractDetails(l);
			return "redirect:/partner_contract/add";
		}
		String error = "Xe đã được ký 1 hợp đồng khác trong khoảng thời gian từ " + start2 + " đến " + end2;
		model.addAttribute("error", error);
		model.addAttribute("partnerContractDetail", partnerContractDetail);
		return "fillTermPage";
	}
	
	@GetMapping("/add")
	private String addPartnerContractDetail(Model model, @SessionAttribute("partnerContract") PartnerContract partnerContract, HttpSession session) {
		PartnerContractDetail partnerContractDetail = new PartnerContractDetail();
		partnerContractDetail.setPartnerContract(partnerContract);
		Motorbike motorbike = new Motorbike();
		partnerContractDetail.setMotorbike(motorbike);
		model.addAttribute("partnerContractDetail", partnerContractDetail);
		session.setAttribute("partnerContractDetail", partnerContractDetail);
		return "addPartnerContractDetail";
	}
	
	@PostMapping("/save")
	private String saveMotorbikeAndGoToRegisterPage(@ModelAttribute("partnerContractDetail") PartnerContractDetail modelPartnerContractDetail,
													@SessionAttribute("partnerContract") PartnerContract partnerContract,
													@SessionAttribute("partnerContractDetail") PartnerContractDetail partnerContractDetail) {
		motorbikeServiceImpl.updateMotorbike(modelPartnerContractDetail.getMotorbike());
		partnerContractDetail.setCost(modelPartnerContractDetail.getCost());
		partnerContractDetail.setDateStart(modelPartnerContractDetail.getDateStart());
		partnerContractDetail.setDateEnd(modelPartnerContractDetail.getDateEnd());
		partnerContractDetail.setMotorbike(modelPartnerContractDetail.getMotorbike());
		List<PartnerContractDetail> l = partnerContract.getPartnerContractDetails();
		l.add(partnerContractDetail);
		partnerContract.setPartnerContractDetails(l);
		return "redirect:/partner_contract/add";
	}
}
