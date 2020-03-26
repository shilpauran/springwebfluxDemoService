package com.sap.slh.tax.attributes.determination.springwebfluxdemo.model;

public class RedisRepo {
	
	
	private TaxDetails taxDetails;
	private TaxLine taxLine;
	
	
	public void setTaxLine(TaxLine taxLine) {
		this.taxLine = taxLine;
	}
	public TaxDetails getTaxDetails() {
		return taxDetails;
	}
	public void setTaxDetails(TaxDetails taxDetails) {
		this.taxDetails = taxDetails;
	}
	public RedisRepo(TaxLine taxLine,TaxDetails taxDetails)
	{
		this.taxLine = taxLine;
		this.taxDetails = taxDetails;
	}
	
	public TaxLine getTaxLine() {
		return taxLine;
	}
	

}
