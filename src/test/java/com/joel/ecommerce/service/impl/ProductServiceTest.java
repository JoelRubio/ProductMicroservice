package com.joel.ecommerce.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.joel.ecommerce.domain.Product;
import com.joel.ecommerce.exception.EntityNotFoundException;
import com.joel.ecommerce.model.ProductRequestModel;
import com.joel.ecommerce.model.ProductResponseModel;
import com.joel.ecommerce.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductServiceImpl productService;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	
	@Test
	@Disabled
	public void getAllProducts() {
		
		//given
		ProductResponseModel product1 = new ProductResponseModel("123", "product1", new BigDecimal(12.45), "image1.png");
		ProductResponseModel product2 = new ProductResponseModel("567", "product2", new BigDecimal(23.57), "image2.png");
		List<ProductResponseModel> expectedResponse = List.of(product1, product2);
		Collection<ProductResponseModel> actualResponse;
		
		//when
		when(productRepository.findAll()
				.stream()
				.map(product -> modelMapper.map(product, ProductResponseModel.class))
				.collect(Collectors.toList())).thenReturn(expectedResponse);
		
		//execute
		actualResponse = productService.getAll();
		
		//then
		assertThat(actualResponse).isEqualTo(expectedResponse);
	}

	@Test
	public void getProductById() {
		
		//given
		String id                             = UUID.randomUUID().toString();
		Optional<Product> product             = Optional.of(new Product());
		ProductResponseModel expectedResponse = new ProductResponseModel();
		ProductResponseModel actualResponse;
		
		expectedResponse.setName("fake product");
		expectedResponse.setPrice(BigDecimal.TEN);
		
		//when
		when(productRepository.findById(id)).thenReturn(product);
		when(modelMapper.map(product.get(), ProductResponseModel.class)).thenReturn(expectedResponse);
		
		//execute
		actualResponse = productService.getById(id);
		
		
		//then
		verify(modelMapper, times(1)).map(product.get(), ProductResponseModel.class);
		
		assertThat(actualResponse).isEqualTo(expectedResponse);
	}
	
	@Test
	public void addNewProduct() {
		
		//given
		Product product                       = new Product();
		ProductRequestModel productRequest    = new ProductRequestModel();
		ProductResponseModel expectedResponse = new ProductResponseModel();
		ProductResponseModel actualResponse;
		
		expectedResponse.setName("product1");
		expectedResponse.setPrice(BigDecimal.TEN);
		
		//when
		when(modelMapper.map(productRequest, Product.class)).thenReturn(product);
		when(productRepository.save(product)).thenReturn(product);
		when(modelMapper.map(product, ProductResponseModel.class)).thenReturn(expectedResponse);
		
		//execute
		actualResponse = productService.add(productRequest);
		
		//then
		verify(modelMapper, times(1)).map(productRequest, Product.class);
		verify(modelMapper, times(1)).map(product, ProductResponseModel.class);
		
		assertThat(actualResponse).isEqualTo(expectedResponse);
	}
	
	@Test
	public void updateProductById() {
		
		//given
		String id = UUID.randomUUID().toString();
		Optional<Product> product             = Optional.of(new Product());
		ProductRequestModel productRequest    = new ProductRequestModel();
		ProductResponseModel expectedResponse = new ProductResponseModel();
		ProductResponseModel actualResponse;
		
		expectedResponse.setName(id);
		expectedResponse.setPrice(BigDecimal.TEN);
		expectedResponse.setImageUrl("image.png");
		
		//when
		when(productRepository.findById(id)).thenReturn(product);
		when(productRepository.save(product.get())).thenReturn(product.get());
		when(modelMapper.map(product.get(), ProductResponseModel.class)).thenReturn(expectedResponse);
		
		//execute
		actualResponse = productService.update(id, productRequest);
		
		//then
		verify(productRepository, times(1)).findById(id);
		verify(modelMapper, times(1)).map(product.get(), ProductResponseModel.class);
		
		assertThat(actualResponse).isEqualTo(expectedResponse);
	}
	
	@Test
	public void deleteProductById() {
		
		//given
		String id                 = UUID.randomUUID().toString();
		Optional<Product> product = Optional.of(new Product());
		
		//when
		when(productRepository.findById(id)).thenReturn(product);
		doNothing().when(productRepository).delete(product.get());
		
		//execute
		productService.delete(id);
		
		//then
		verify(productRepository, times(1)).findById(id);
		verify(productRepository, times(1)).delete(product.get());
	}
	
	@Test
	public void expectExceptionWhenIdIsNotFound() {
		
		//given
		String emptyId = "";
		
		//when
		when(productRepository.findById(emptyId)).thenThrow(EntityNotFoundException.class);
		
		//then
		assertThrows(EntityNotFoundException.class, () -> productService.getById(emptyId));
	}
}




