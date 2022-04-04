package com.joel.ecommerce.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joel.ecommerce.model.ProductRequestModel;
import com.joel.ecommerce.model.ProductResponseModel;
import com.joel.ecommerce.service.ProductService;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	private String baseURI;
	
	@BeforeEach
	public void setup() {
		
		baseURI = "/api/v1/products";
	}
	
	@Test
	public void getAllProducts() throws Exception {
		
		//given
		Collection<ProductResponseModel> expectedResponse = List.of(
				new ProductResponseModel("123", "product1", BigDecimal.TEN, "image1.png"),
				new ProductResponseModel("567", "product2", BigDecimal.ONE, "image2.jpg"));
		
		//when
		when(productService.getAll()).thenReturn(expectedResponse);
		
		//then
		this.mockMvc
			.perform(get(baseURI)
						.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id", is("123")))
			.andExpect(jsonPath("$[0].name", is("product1")))
			.andExpect(jsonPath("$[0].price", is(BigDecimal.TEN.toString())))
			.andExpect(jsonPath("$[0].imageUrl", is("image1.png")));
		
		verify(productService, times(1)).getAll();
	}
	
	@Test
	public void getProductById() throws Exception {
		
		//given
		final String URI = baseURI + "/{id}";
		ProductResponseModel expectedResponse = new ProductResponseModel();
		String id        = "12345";
		String name      = "product1";
		BigDecimal price = BigDecimal.TEN;
		String imageUrl  = "image1.png";
		
		expectedResponse.setId(id);
		expectedResponse.setName(name);
		expectedResponse.setPrice(price);
		expectedResponse.setImageUrl(imageUrl);
		
		//when
		when(productService.getById(id)).thenReturn(expectedResponse);
		
		//then		
		this.mockMvc
			.perform(get(URI, id)
						.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(id)))
			.andExpect(jsonPath("$.name", is(name)))
			.andExpect(jsonPath("$.price", is(price.toString())))
			.andExpect(jsonPath("$.imageUrl", is(imageUrl)));
		
		verify(productService, times(1)).getById(id);
	}
	
	@Test
	public void addNewProduct() throws Exception {
		
		//given
		ProductRequestModel productRequest    = new ProductRequestModel("12345", BigDecimal.TEN);
		ProductResponseModel expectedResponse = new ProductResponseModel("12345", "product1", BigDecimal.TEN, "image1.png");
		
		//when
		when(productService.add(productRequest)).thenReturn(expectedResponse);
		
		//then
		this.mockMvc
			.perform(post(baseURI)
						.content(new ObjectMapper().writeValueAsString(productRequest))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is("12345")))
			.andExpect(jsonPath("$.name", is("product1")))
			.andExpect(jsonPath("$.price", is(BigDecimal.TEN.toString())))
			.andExpect(jsonPath("$.imageUrl", is("image1.png")));
		
		verify(productService, times(1)).add(productRequest);
	}
	
	@Test
	public void deleteProductById() throws Exception {
		
		//given
		final String URI = baseURI + "/{id}";
		String id        = "12345";
		
		//when
		doNothing().when(productService).delete(id);
		
		//then
		this.mockMvc
			.perform(delete(URI, id))
			.andExpect(status().isNoContent());
		
		verify(productService, times(1)).delete(id);
	}
}
