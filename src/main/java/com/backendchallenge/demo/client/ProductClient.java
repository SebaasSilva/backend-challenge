package com.backendchallenge.demo.client;

import static java.util.Optional.ofNullable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.backendchallenge.demo.model.ProductDetail;


@Slf4j
@Component
public class ProductClient {

  private static final String url = "http://localhost:3001/product/";

  /**
   * Find ProductDetail by id.
   *
   * @param id Item unique identification.
   * @return Item detail.
   */
  public ProductDetail findById(String id) {
    RestTemplate restTemplate = new RestTemplate();
    log.debug("Find by [Id : {}]", id);
    ProductDetail item = restTemplate.getForObject(url + id, ProductDetail.class);
    return ofNullable(item)
        .get();
  }

  /**
   * Find List of similar Ids by id.
   *
   * @param id Item unique identification.
   * @return List of similar item ids.
   */
  public List<String> findSimilarIds(String id) {
    RestTemplate restTemplate = new RestTemplate();
    log.debug("Find by [Id : {}]", id);
    List<Integer> idList = restTemplate.getForObject(url + id + "/similarids", List.class);
    List<String> list = idList
        .stream()
        .map(String::valueOf)
        .collect(Collectors.toList());
    return ofNullable(list)
        .get();
  }
}
