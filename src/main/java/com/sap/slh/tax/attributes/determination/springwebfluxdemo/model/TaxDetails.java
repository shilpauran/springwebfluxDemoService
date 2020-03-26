package com.sap.slh.tax.attributes.determination.springwebfluxdemo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxDetails{
	
//	private static final long serialVersionUID = -7204238087759434704L;
	private String taxableCountry;
	private String taxEventCode;
	
	public String getTaxableCountry() {
		return taxableCountry;
	}

	public void setTaxableCountry(String taxableCountry) {
		this.taxableCountry = taxableCountry;
	}

	public String getTaxEventCode() {
		return taxEventCode;
	}

	public void setTaxEventCode(String taxEventCode) {
		this.taxEventCode = taxEventCode;
	}

}
