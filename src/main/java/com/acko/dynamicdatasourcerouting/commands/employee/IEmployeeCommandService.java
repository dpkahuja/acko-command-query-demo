package com.acko.dynamicdatasourcerouting.commands.employee;

import com.acko.dynamicdatasourcerouting.mapstruct.dtos.CreateEmployeeCommand;

public interface IEmployeeCommandService {
  void handleCreateEmployeeCommand(CreateEmployeeCommand command);
}
