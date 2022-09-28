package com.backendchallenge.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.backendchallenge.demo.service.ProductService;

/**
 * Rest Controller to expose fields to other Apis.
 */
@Api(tags = "Similar Product")
@RestController
public class ProductController {

  private ProductService productService;

  /**
   * Constructor to create an {@link ProductController} instance.
   *
   * @param productService instance of {@link ProductService}.
   */
  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * Return data for similar products.
   *
   * @param productId used to get the Similar Products data.
   * @return SimilarProduct Object
   */
  @ApiOperation("Get all similar products by Id")
  @GetMapping("/product/{productId}/similar")
  public ResponseEntity<?> getSimilarProducts(
      @PathVariable("productId") String productId) {
    return productService.similarProducts(productId);
  }

  @ApiOperation("Get pong to test the Api running")
  @GetMapping("/ping")
  public String ping() {
    return "pong";
  }
}
