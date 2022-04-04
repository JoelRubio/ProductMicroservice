package com.joel.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestModel implements Serializable {
	
	private static final long serialVersionUID = 1566942304803993885L;

	private String name;
	private BigDecimal price;
//	private MultipartFile image;
}
