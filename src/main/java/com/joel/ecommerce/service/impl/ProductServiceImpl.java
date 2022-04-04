package com.joel.ecommerce.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.joel.ecommerce.domain.Product;
import com.joel.ecommerce.exception.EntityNotFoundException;
import com.joel.ecommerce.model.ProductRequestModel;
import com.joel.ecommerce.model.ProductResponseModel;
import com.joel.ecommerce.repository.ProductRepository;
import com.joel.ecommerce.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private ModelMapper modelMapper;
	
	public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
		super();
		this.productRepository = productRepository;
		this.modelMapper       = modelMapper;
	}

	@Override
	public Collection<ProductResponseModel> getAll() {

		log.info("Getting all products information...");
		
		Collection<ProductResponseModel> products = productRepository
				.findAll()
				.stream()
				.map(product -> modelMapper.map(product, ProductResponseModel.class))
				.collect(Collectors.toList());
		
		log.info("Total products obtained: {}", products.size());
		
		return products;
	}

	@Override
	public ProductResponseModel getById(String id) {

		log.info("Getting product {} information...", id);
		
		Product product = productRepository
				.findById(id)
				.orElseThrow(EntityNotFoundException::new);
		
		log.info("Product {} information obtained", id);

		
		ProductResponseModel productResponse = modelMapper.map(product, ProductResponseModel.class);
		
		return productResponse;
	}

	@Override
	public ProductResponseModel add(ProductRequestModel productRequest) {
		
		log.info("Adding new product information...");
		
		Product product = modelMapper.map(productRequest, Product.class);
		
		product = productRepository.save(product);
		
		log.info("Product {} added", product.getId());

		
		ProductResponseModel productResponse = modelMapper.map(product, ProductResponseModel.class);
		
		return productResponse;
	}

	@Override
	public ProductResponseModel update(String id, ProductRequestModel productRequest) {
	
		log.info("Getting product {} to be updated", id);
		
		Product product = productRepository
				.findById(id)
				.orElseThrow(EntityNotFoundException::new);
		
		log.info("Product {} obtained", id);
		
		
		log.info("Updating product {}", product.getId());
		
		product.setName(productRequest.getName());
		product.setPrice(productRequest.getPrice());
		
		productRepository.save(product);
		
		log.info("Product {} updated", product.getId());
		
		
		ProductResponseModel productResponse = modelMapper.map(product, ProductResponseModel.class);
		
		return productResponse;
	}

	@Override
	public void delete(String id) {
		
		log.info("Getting product {} information to be deleted...", id);
		
		Product product = productRepository
				.findById(id)
				.orElseThrow(EntityNotFoundException::new);
		
		log.info("Product {} information obtained", product.getId());
		
		
		log.info("Deleting product {}", product.getId());
		
		productRepository.delete(product);
		
		log.info("Product {} deleted", product.getId());
	}
}


