package com.example.motorbike.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.motorbike.models.Bill;
import com.example.motorbike.models.CustomerContractDetail;
import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.PartnerContract;
import com.example.motorbike.models.PartnerContractDetail;
import com.example.motorbike.serviceImpls.CustomerContractDetailServiceImpl;
import com.example.motorbike.serviceImpls.PartnerContractServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer_contract_detail")
public class CustomerContractDetailController {
	@Autowired
	CustomerContractDetailServiceImpl customerContractDetailServiceImpl;
	@Autowired
	PartnerContractServiceImpl partnerContractServiceImpl;
	
	@GetMapping("/")
	public String getListContract(Model model, @SessionAttribute("bill") Bill bill, HttpSession session) {
		List<PartnerContract> partnerContracts = partnerContractServiceImpl.getByPartner(bill.getPartner());
		List<PartnerContractDetail> partnerContractDetails = new ArrayList<>();
		
		for (PartnerContract p : partnerContracts) {
			for (PartnerContractDetail pcd : p.getPartnerContractDetails()) {
				partnerContractDetails.add(pcd);
			}
		}
		List<CustomerContractDetail> customerContractDetails = customerContractDetailServiceImpl.getToPay(bill.getPartner());
		List<CustomerContractDetail> list = bill.getCustomerContractDetails();

		for (CustomerContractDetail c : list) {
			for (int i  = 0; i<customerContractDetails.size(); i++) {
				if (c.getId() == customerContractDetails.get(i).getId()) {
					customerContractDetails.remove(i);
				}
			}
		}
		
		model.addAttribute("customerContractDetails", customerContractDetails);
		session.setAttribute("customerContractDetails", customerContractDetails);
		session.setAttribute("partnerContractDetails", partnerContractDetails);
		return "listCustomerContractDetail";
	}
	
	
	@GetMapping("/select/{id}")
	private String selectToPay(Model model, @PathVariable int id, @SessionAttribute("bill") Bill bill,
								@SessionAttribute("customerContractDetails") List<CustomerContractDetail> customerContractDetailsl,
								@SessionAttribute("partnerContractDetails") List<PartnerContractDetail> partnerContractDetails) {
		for (CustomerContractDetail customerContractDetail : customerContractDetailsl) {
			if (customerContractDetail.getId() == id) {
				List<CustomerContractDetail> l = bill.getCustomerContractDetails();
				l.add(customerContractDetail);
				bill.setCustomerContractDetails(l);
				int money = bill.getTotalMoney();
				Date start = customerContractDetail.getDateStart();
				Date end = customerContractDetail.getDateEnd();
				
				LocalDate startLocal = start.toLocalDate();
				LocalDate endLocal = end.toLocalDate();
				
				long totalDay = ChronoUnit.DAYS.between(startLocal, endLocal);
				
				Motorbike m = customerContractDetail.getMotorbike();
				for (PartnerContractDetail p : partnerContractDetails) {
					if(p.getMotorbike().equals(m) && p.getDateStart().compareTo(start) <= 0 && p.getDateEnd().compareTo(end) >= 0) {
						money += totalDay * p.getCost();
						break;
					}
				}
				bill.setTotalMoney(money);
			}
		}
		return "redirect:/bill/add";
	}
}
