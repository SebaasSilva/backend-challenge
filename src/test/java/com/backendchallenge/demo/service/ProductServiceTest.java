package com.backendchallenge.demo.service;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.backendchallenge.demo.client.ProductClient;
import com.backendchallenge.demo.model.ProductDetail;
import com.backendchallenge.demo.model.SimilarProducts;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  private static final String ID = "1234";

  @InjectMocks ProductService productService;

  @Mock ProductClient productClient;


  @Test
  public void findProducts(){
    when(productClient.findSimilarIds(any())).thenReturn(buildSimilarIdsList());
    when(productClient.findById(any())).thenReturn(ofNullable(buildItem()).get());

    ResponseEntity<?> similarProducts = productService.similarProducts(ID);
    assertEquals(similarProducts, buildSimilarList());
  }

  private List<String> buildSimilarIdsList() {
    List<String> similarIds = new ArrayList<>();
    similarIds.add("1");
    similarIds.add("2");
    similarIds.add("3");
    return ofNullable(similarIds)
        .get();
  }

  private ResponseEntity<?> buildSimilarList() {
    List<ProductDetail> similarProducts = new ArrayList<>();
    similarProducts.add(buildItem());
    similarProducts.add(buildItem());
    similarProducts.add(buildItem());
    return ResponseEntity.ok(ofNullable(SimilarProducts
        .builder()
        .items(similarProducts)
        .build())
        .get());
  }

  private ProductDetail buildItem() {
    return ProductDetail
        .builder()
        .id("1")
        .name("White T-Shirt")
        .price(10L)
        .availability(true)
        .build();
  }


}
