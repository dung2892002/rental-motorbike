package com.example.motorbike.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.motorbike.models.CustomerContractDetail;
import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.Order;
import com.example.motorbike.models.PartnerContractDetail;
import com.example.motorbike.models.Review;
import com.example.motorbike.models.User;
import com.example.motorbike.serviceImpls.CustomerContractDetailServiceImpl;
import com.example.motorbike.serviceImpls.OrderServiceImpl;
import com.example.motorbike.serviceImpls.PartnerContractDetailServiceImpl;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderServiceImpl orderServiceImpl;
	
	@Autowired
	CustomerContractDetailServiceImpl customerContractDetailServiceImpl;
	
	@Autowired 
	PartnerContractDetailServiceImpl partnerContractDetailServiceImpl;
	
	@GetMapping("/myOrder")
	private String showMyOrder(Model model, @SessionAttribute(required = false) User user) {
		if(user != null) {
			List<Order> orders = orderServiceImpl.getOrderByUser(user);
			model.addAttribute("orders",orders);
			return "listOrder";
		} else {
			model.addAttribute("error", "Vui lòng đăng nhập để xem thông tin giỏ hàng của bạn");
			return "loginForm";
		}
	}
	
	@PostMapping("/addOrder/")
	private String showListOrderOfUser(@SessionAttribute(required = false) User user, 
			@SessionAttribute("motorbike") Motorbike motorbike,
			@SessionAttribute("reviews") List<Review> reviews,
			@RequestParam("dateStart") Date dateStart,
			@RequestParam("dateEnd") Date dateEnd,
			Model model) {
		if (user != null) {	
			List<CustomerContractDetail> customerContractDetails = customerContractDetailServiceImpl.getToCheckCreate(motorbike, dateStart, dateEnd);
			List<PartnerContractDetail> partnerContractDetails = partnerContractDetailServiceImpl.getToCheckCreate(motorbike, dateStart, dateEnd);
//			System.out.println("so luong hop dong khach hang: "+ customerContractDetails.size());
//			System.out.println("so luong hop dong doi tac: " + partnerContractDetails.size());
//			System.out.println("Chi phi thue xe: " + motorbike.getCost());
			if(motorbike.getCost() != 0 || !partnerContractDetails.isEmpty()) {
				if(customerContractDetails.isEmpty()) {
					Order order = new Order();
					order.setUser(user);
					order.setMotorbike(motorbike);
					order.setDateEnd(dateEnd);
					order.setDateStart(dateStart);
					order.setStatus("Not approved");
					orderServiceImpl.createOrder(order);
					model.addAttribute("success","Đặt lịch thuê xe thành công, đến cửa hàng để ký hợp đồng và nhận xe");
				} else {
					CustomerContractDetail c = customerContractDetails.get(0);
					model.addAttribute("error","Xe đã được thuê trong khoảng thời gian " + c.getDateStart() + " đến " + c.getDateEnd());
				}
			} else {
				model.addAttribute("error", "Xe hiện không thể thuê, vui lòng chọn khung thời gian khác");
			}
			model.addAttribute("motorbike", motorbike);
			model.addAttribute("reviews", reviews);
			return "motorbikeDetail";
		} else {
			model.addAttribute("error", "Đăng nhập để đặt lịch thuê xe");
			return "loginForm";
		}
	}
}
