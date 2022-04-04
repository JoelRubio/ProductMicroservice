package com.joel.ecommerce.service;

import java.util.Collection;

import com.joel.ecommerce.model.ProductRequestModel;
import com.joel.ecommerce.model.ProductResponseModel;

public interface ProductService {

	Collection<ProductResponseModel> getAll();
	ProductResponseModel getById(String id);
	ProductResponseModel add(ProductRequestModel product);
	ProductResponseModel update(String id, ProductRequestModel product);
	void delete(String id);
}