package com.backendchallenge.demo.controller;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backendchallenge.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

  private static final String ID = "1234";

  @InjectMocks
  private ProductController productController;

  @Mock
  private ProductService productService;

  @BeforeEach
  void setUp() {
    productController = new ProductController(productService);
  }

  @Test
  void successCallById() {
    when(productService
        .similarProducts(any()))
        .thenReturn(new ResponseEntity<>(HttpStatus.ACCEPTED));
    ResponseEntity response = productController.getSimilarProducts(ID);
    assertTrue(response.getStatusCode().is2xxSuccessful());
    verify(productService, times(INTEGER_ONE)).similarProducts(ID);
  }

  @Test
  void notSuccessCallById() {
    when(productService
        .similarProducts(any()))
        .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    ResponseEntity response = productController.getSimilarProducts(ID);
    assertTrue(response.getStatusCode().is5xxServerError());
    verify(productService, times(INTEGER_ONE)).similarProducts(ID);
  }

  @Test
  void notFoundSuccessCallById() {
    when(productService
        .similarProducts(any()))
        .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    ResponseEntity response = productController.getSimilarProducts(ID);
    assertTrue(response.getStatusCode().is4xxClientError());
    verify(productService, times(INTEGER_ONE)).similarProducts(ID);
  }

}
