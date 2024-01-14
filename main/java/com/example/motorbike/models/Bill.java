package com.example.motorbike.models;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date time;
    
    private int totalMoney;

    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;
    
    @OneToMany(mappedBy = "bill", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<CustomerContractDetail> customerContractDetails;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public List<CustomerContractDetail> getCustomerContractDetails() {
		return customerContractDetails;
	}

	public void setCustomerContractDetails(List<CustomerContractDetail> customerContractDetails) {
		this.customerContractDetails = customerContractDetails;
	}
    
}
