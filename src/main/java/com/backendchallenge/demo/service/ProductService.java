package com.backendchallenge.demo.service;


import com.backendchallenge.demo.client.ProductClient;
import com.backendchallenge.demo.model.ErrorHandle;
import com.backendchallenge.demo.model.ProductDetail;
import com.backendchallenge.demo.model.SimilarProducts;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

  private final ProductClient productClient;

  /**
   * Create a new instance of the Service.
   *
   * @param productClient used to communicate with Product Client
   */
  public ProductService(ProductClient productClient) {
    this.productClient = productClient;
  }

  /**
   * Get all similar products from the given productId.
   *
   * @param productId Unique identifier of the user.
   * @return Returns a list of Similar products with detail.
   */
  public ResponseEntity<?> similarProducts(String productId) {
    return getSimilarProducts(productId);
  }

  /**
   * This method validate if the productId has similar products and return a list.
   * If the item hasn't similar products return 404.
   *
   * @param productId Unique identifier of the user.
   * @return Returns a list of Similar products with detail.
   */
  private ResponseEntity<?> getSimilarProducts(String productId) {
    List<ProductDetail> productDetailsList;
    try {
      List<String> similarIds = getSimilarIds(productId);
      if (!similarIds.isEmpty()) {
        productDetailsList = similarIds
            .stream()
            .map(id -> getProductDetails(id))
            .collect(Collectors.toList());
      } else {
        return new ResponseEntity<>(ErrorHandle
            .builder()
            .code(-1)
            .description(HttpStatus.NOT_FOUND.toString())
            .build(), HttpStatus.NOT_FOUND);
      }
    } catch (Exception exception) {
      log.error("Error: " + exception.getMessage(), ProductService.class.getName());
      return new ResponseEntity<>(ErrorHandle
          .builder()
          .code(-1)
          .description(exception.getMessage())
          .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return ResponseEntity.ok(SimilarProducts
        .builder()
        .items(productDetailsList)
        .build());
  }

  private List<String> getSimilarIds(String productId) {
    return productClient.findSimilarIds(productId);
  }

  private ProductDetail getProductDetails(String productId) {
    return productClient.findById(productId);
  }
}
