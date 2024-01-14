package com.example.motorbike.models;

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
public class PartnerContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;
    
    @OneToMany(mappedBy = "partnerContract", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PartnerContractDetail> partnerContractDetails;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public List<PartnerContractDetail> getPartnerContractDetails() {
		return partnerContractDetails;
	}

	public void setPartnerContractDetails(List<PartnerContractDetail> partnerContractDetails) {
		this.partnerContractDetails = partnerContractDetails;
	}
	
}
