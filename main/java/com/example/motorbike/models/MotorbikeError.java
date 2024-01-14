package com.example.motorbike.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class MotorbikeError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Motorbike tblMotorbike;

    @ManyToOne
    private Error tblError;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Motorbike getTblMotorbike() {
		return tblMotorbike;
	}

	public void setTblMotorbike(Motorbike tblMotorbike) {
		this.tblMotorbike = tblMotorbike;
	}

	public Error getTblError() {
		return tblError;
	}

	public void setTblError(Error tblError) {
		this.tblError = tblError;
	}

    
}
