package com.acko.dynamicdatasourcerouting.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeRequest {
  private String firstName;
  private String lastName;
}
