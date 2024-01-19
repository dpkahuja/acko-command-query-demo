package com.acko.dynamicdatasourcerouting.commands.employee;

import com.acko.dynamicdatasourcerouting.application.exception.EmployeeDetailsAlreadyExistException;
import com.acko.dynamicdatasourcerouting.domain.Employee;
import com.acko.dynamicdatasourcerouting.repository.writerepository.EmployeeWriteRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeCommandService implements IEmployeeCommandService {
  private EmployeeWriteRepository writeRepository;

  public void handleCreateEmployeeCommand(Employee employee) {
    String name = employee.getEmployeeName();
    List<Employee> employeeList = writeRepository.findByEmployeeName(name);
    if (!employeeList.isEmpty()) {
      throw new EmployeeDetailsAlreadyExistException(name);
    }

    writeRepository.save(employee);
  }
}
