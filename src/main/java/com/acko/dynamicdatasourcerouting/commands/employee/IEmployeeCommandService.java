package com.acko.dynamicdatasourcerouting.commands.employee;

import com.acko.dynamicdatasourcerouting.domain.Employee;

public interface IEmployeeCommandService {
  void handleCreateEmployeeCommand(Employee command);
}
