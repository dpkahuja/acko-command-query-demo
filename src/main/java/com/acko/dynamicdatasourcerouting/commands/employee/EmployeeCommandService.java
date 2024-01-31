package com.acko.dynamicdatasourcerouting.commands.employee;

import com.acko.dynamicdatasourcerouting.application.exception.EmployeeDetailsAlreadyExistException;
import com.acko.dynamicdatasourcerouting.domain.Employee;
import com.acko.dynamicdatasourcerouting.mapstruct.dtos.CreateEmployeeCommand;
import com.acko.dynamicdatasourcerouting.mapstruct.mappers.CreateEmployeeCommandToEmployeeStructMapper;
import com.acko.dynamicdatasourcerouting.repository.writerepository.EmployeeWriteRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeCommandService implements IEmployeeCommandService {
  private final EmployeeWriteRepository writeRepository;
  private final CreateEmployeeCommandToEmployeeStructMapper mapStructMapper;

  public void handleCreateEmployeeCommand(CreateEmployeeCommand createEmployeeCommand) {
    Employee employee = mapStructMapper.employeeCommandToEmployee(createEmployeeCommand);
    String name = employee.getEmployeeName();
    List<Employee> employeeList = writeRepository.findByEmployeeName(name);
    if (!employeeList.isEmpty()) {
      throw new EmployeeDetailsAlreadyExistException(name);
    }

    writeRepository.save(employee);
  }
}
