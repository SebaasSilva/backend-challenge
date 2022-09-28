package com.backendchallenge.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorHandle {
  private Integer code;
  private String description;
}
