package com.example.motorbike.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.motorbike.models.CustomerContract;
import com.example.motorbike.models.CustomerContractDetail;
import com.example.motorbike.models.Motorbike;
import com.example.motorbike.models.Order;
import com.example.motorbike.models.PartnerContractDetail;
import com.example.motorbike.models.Review;
import com.example.motorbike.models.User;
import com.example.motorbike.serviceImpls.CustomerContractDetailServiceImpl;
import com.example.motorbike.serviceImpls.OrderServiceImpl;
import com.example.motorbike.serviceImpls.PartnerContractDetailServiceImpl;

import jakarta.servlet.http.HttpSession;

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
			model.addAttribute("registerMode", false);
			return "listOrder";
		} else {
			model.addAttribute("error", "Vui lòng đăng nhập để xem thông tin giỏ hàng của bạn");
			return "loginForm";
		}
	}
	
	@GetMapping("/select")
	private String showOrderOfCustomer(HttpSession sesion, Model model, @SessionAttribute("customerContract") CustomerContract customerContract) {
		if(customerContract.getCustomer() != null) {
			List<Order> orders = orderServiceImpl.getOrderByUser(customerContract.getCustomer().getUser());
			model.addAttribute("orders",orders);
			model.addAttribute("registerMode", true);
			List<Order> ordersSelected = new ArrayList<>();
			sesion.setAttribute("ordersSelected", ordersSelected);
			sesion.setAttribute("orders", orders);
			return "listOrder";
		} else {
			model.addAttribute("error", "Chọn khách hàng trước");
			return "redirect:/customer_contract/add";
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
			List<Order> orderOfUser = orderServiceImpl.getToCheckCreate(user.getId(), dateStart, dateEnd);
//			System.out.println("so luong hop dong khach hang: "+ customerContractDetails.size());
//			System.out.println("so luong hop dong doi tac: " + partnerContractDetails.size());
//			System.out.println("so luong order bi trung cua user: " + orderOfUser.size());
//			System.out.println("Chi phi thue xe: " + motorbike.getCost());
			if(motorbike.getCost() != 0 || !partnerContractDetails.isEmpty()) {
				if(customerContractDetails.isEmpty() && orderOfUser.isEmpty()) {
					Order order = new Order();
					order.setUser(user);
					order.setMotorbike(motorbike);
					order.setDateEnd(dateEnd);
					if (motorbike.getCost() != 0) order.setCost(motorbike.getCost());
					else {
						order.setCost((int)(partnerContractDetails.get(0).getCost() * 1.5));
					}
					order.setDateStart(dateStart);
					order.setStatus("Not approved");
					orderServiceImpl.createOrder(order);
					model.addAttribute("success","Đặt lịch thuê xe thành công, đến cửa hàng để ký hợp đồng và nhận xe");
				} else {
			        if (!orderOfUser.isEmpty()) {
			            Order existingOrder = orderOfUser.get(0);
			            model.addAttribute("error", "Bạn đã có một đơn đặt hàng không được xác nhận từ ngày "
			                    + existingOrder.getDateStart() + " đến " + existingOrder.getDateEnd());
			        } else {
			            CustomerContractDetail c = customerContractDetails.get(0);
			            model.addAttribute("error", "Xe đã được thuê trong khoảng thời gian " + c.getDateStart() + " đến " + c.getDateEnd());
			        }
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
	
	@GetMapping("/select/{id}")
	private String selectToRegisterCustomerContract(Model model, HttpSession session, @PathVariable int id,
			@SessionAttribute("customerContract") CustomerContract customerContract,
			@SessionAttribute("orders") List<Order> orders,
			@SessionAttribute("ordersSelected") List<Order> ordersSelected) {
		for (Order order : orders) {
			if (order.getId() == id) {
				List<CustomerContractDetail> customerContractDetails = customerContractDetailServiceImpl.getToCheckCreate(order.getMotorbike(), order.getDateStart(), order.getDateEnd());
				if (customerContractDetails.size() == 0) {
					CustomerContractDetail customerContractDetail = new CustomerContractDetail();
					customerContractDetail.setMotorbike(order.getMotorbike());
					customerContractDetail.setDateStart(order.getDateStart());
					customerContractDetail.setDateEnd(order.getDateEnd());
					customerContractDetail.setCost(order.getCost());
					customerContractDetail.setCustomerContract(customerContract);
					List<CustomerContractDetail> l = customerContract.getCustomerContractDetails();
					l.add(customerContractDetail);
					customerContract.setCustomerContractDetails(l);
					ordersSelected.add(order);
					break;
				} else {
					model.addAttribute("error", "xe đã bị thuê mất, lêu lêu");
				}
			}
		}
		return "redirect:/customer_contract/add";
	}
}
