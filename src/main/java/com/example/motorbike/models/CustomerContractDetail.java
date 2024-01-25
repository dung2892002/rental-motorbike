package com.example.motorbike.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CustomerContractDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int cost;
    private Date dateStart;
    private Date dateEnd;

    @ManyToOne
    @JoinColumn(name = "motorbike_id", nullable = false)
    private Motorbike motorbike;

    @ManyToOne
    @JoinColumn(name = "customer_contract_id", nullable = false)
    @JsonBackReference
    private CustomerContract customerContract;

    @ManyToOne
    private Bill bill;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Motorbike getMotorbike() {
		return motorbike;
	}

	public void setMotorbike(Motorbike motorbike) {
		this.motorbike = motorbike;
	}

	public CustomerContract getCustomerContract() {
		return customerContract;
	}

	public void setCustomerContract(CustomerContract customerContract) {
		this.customerContract = customerContract;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	
    
}