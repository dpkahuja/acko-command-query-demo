package com.acko.dynamicdatasourcerouting.mapstruct.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeCommand {
  private String userId;
  private String name;
}
