package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Seller;

public class SalesBySellerDTO {

	private String sellerName;
	private Double total;

	public SalesBySellerDTO(String sellerName, Double total) {
		this.sellerName = sellerName;
		this.total = total;
	}
	
	public SalesBySellerDTO(Seller entity) {
		sellerName = entity.getName();
		total = entity.getTotal();
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getTotal() {
		return total;
	}
}
