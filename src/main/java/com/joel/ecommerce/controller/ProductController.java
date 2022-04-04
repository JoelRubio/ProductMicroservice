package com.joel.ecommerce.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joel.ecommerce.model.ProductRequestModel;
import com.joel.ecommerce.model.ProductResponseModel;
import com.joel.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}
	

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<ProductResponseModel>> getAll() {
		
		Collection<ProductResponseModel> products = productService.getAll();
		
		return ResponseEntity.ok(products);
	}
	
	@GetMapping(path = "/{id}",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponseModel> getById(@PathVariable String id) {
		
		ProductResponseModel productResponse = productService.getById(id);
		
		return ResponseEntity.ok(productResponse);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponseModel> add(@RequestBody ProductRequestModel productRequest) {
		
		ProductResponseModel productResponse = productService.add(productRequest);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(productResponse);
	}
	
	@PutMapping(path = "/{id}",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponseModel> update(@PathVariable String id, @RequestBody ProductRequestModel product) {
		
		ProductResponseModel productResponse = productService.update(id, product);
		
		return ResponseEntity.ok(productResponse);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		
		productService.delete(id);
		
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}
}