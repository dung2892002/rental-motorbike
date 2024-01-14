package com.example.motorbike.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.motorbike.models.Bill;
import com.example.motorbike.models.CustomerContractDetail;
import com.example.motorbike.models.Partner;
import com.example.motorbike.models.People;
import com.example.motorbike.serviceImpls.BillServiceImpl;
import com.example.motorbike.serviceImpls.CustomerContractDetailServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bill")
public class BillController {
	@Autowired 
	BillServiceImpl billServiceImpl;
	
	@Autowired
	CustomerContractDetailServiceImpl customerContractDetailServiceImpl;
	
	@GetMapping("/")
	private String getListBill(Model model, HttpSession session) {
		List<Bill> bills = billServiceImpl.getAllBills();
		model.addAttribute("bills", bills);
		session.setAttribute("bills", bills);
		Bill bill = new Bill();
		session.setAttribute("bill", bill);
		session.setAttribute("paymentMode", true);
		return "listBill";
	}
	@GetMapping("/{id}")
	private String getViewBill(Model model, @PathVariable int id,@SessionAttribute("bills")List<Bill> bills) {
		for (Bill bill : bills) {
			if (bill.getId() == id) {
				model.addAttribute("paymentMode", false);
				model.addAttribute("bill", bill);
			}
		}
		return "paymentPartnerBill";
	}
	
	@GetMapping("/add")
	private String getPaymentPage(Model model, @SessionAttribute("bill") Bill bill) {
		if (bill.getPartner() == null)
			bill.setPartner(new Partner());
		if (bill.getPartner().getPeople() == null)
			bill.getPartner().setPeople(new People());
		List<CustomerContractDetail> l = new ArrayList<>();
		if(bill.getCustomerContractDetails() == null)
			bill.setCustomerContractDetails(l);
		model.addAttribute("bill", bill);
		model.addAttribute("paymentMode", true);
		return "paymentPartnerBill";
	}
	
	@PostMapping("/save")
	private String saveBill(@SessionAttribute("bill") Bill bill) {
		bill.setTime(Date.valueOf(LocalDate.now()));
		List<CustomerContractDetail> customerContractDetails = bill.getCustomerContractDetails();
		billServiceImpl.createBill(bill);
		for(CustomerContractDetail p : customerContractDetails) {
			p.setBill(bill);
			customerContractDetailServiceImpl.updateCustomerContractDetail(p);
		}
		return "redirect:/bill/";
	}
}
