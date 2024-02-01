package com.acko.dynamicdatasourcerouting.commands.employee.models;

import com.acko.dynamicdatasourcerouting.commands.ICommands;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeCommand implements ICommands {
  private String userId;
  private String name;
}
