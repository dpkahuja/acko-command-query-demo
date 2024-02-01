package com.acko.dynamicdatasourcerouting.commands.employee;

import com.acko.dynamicdatasourcerouting.commands.employee.models.CreateEmployeeCommand;

public interface IEmployeeCommandService {
  void handleCreateEmployeeCommand(final CreateEmployeeCommand command, final String by);
}
