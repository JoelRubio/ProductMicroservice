package com.joel.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseModel implements Serializable {

	private static final long serialVersionUID = 1566942304803993885L;

	private String id;
	private String name;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal price;
	private String imageUrl;
}
