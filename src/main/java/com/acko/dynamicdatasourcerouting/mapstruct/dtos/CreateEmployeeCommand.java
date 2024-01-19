package com.acko.dynamicdatasourcerouting.mapstruct.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateEmployeeCommand {
  private String userId;
  private String firstName;
  private String lastName;
}
