package com.acko.dynamicdatasourcerouting.commands.employee;

import com.acko.dynamicdatasourcerouting.application.exception.EmployeeDetailsAlreadyExistException;
import com.acko.dynamicdatasourcerouting.audit.*;
import com.acko.dynamicdatasourcerouting.commands.employee.models.CreateEmployeeCommand;
import com.acko.dynamicdatasourcerouting.commands.employee.models.DeleteEmployeesCommand;
import com.acko.dynamicdatasourcerouting.domain.Employee;
import com.acko.dynamicdatasourcerouting.events.employee.models.AllEmployeeDeleted;
import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeCreated;
import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeFound;
import com.acko.dynamicdatasourcerouting.events.employee.models.LogCreated;
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

  @Audit(requiredName = "CreateEmployeeCommand", mode = AuditMode.AUTO)
  public void handleCreateEmployeeCommand(
      final CreateEmployeeCommand createEmployeeCommand, final String by) {

    Employee employee = mapStructMapper.employeeCommandToEmployee(createEmployeeCommand);
    String name = employee.getEmployeeName();
    List<Employee> employeeList = writeRepository.findByEmployeeName(name);
    if (!employeeList.isEmpty()) {
      throw new EmployeeDetailsAlreadyExistException(name);
    }
    AuditEventManager auditEventManager = AuditContextHolder.get();

    auditEventManager.addDomainEvent(new EmployeeFound(new UniqueEntityIDString()));
    writeRepository.save(employee);
    auditEventManager.addDomainEvent(new EmployeeCreated(employee, by));
    auditEventManager.addDomainEvent(
        new LogCreated(
            new UniqueEntityIDString(), "employee is created " + employee.getEmployeeId()));
    subTask(employee);
  }

  @Audit(requiredName = "CreateEmployeeCommand2", mode = AuditMode.AUTO)
  private void subTask(Employee employee) {
    AuditEventManager auditEventManager = AuditContextHolder.get();
    auditEventManager.addDomainEvent(new EmployeeCreated(employee, "subtask"));
  }

  @Audit(requiredName = "DeleteEmployeesCommand", mode = AuditMode.AUTO)
  public void deleteAllEmployees(DeleteEmployeesCommand deleteEmployeesCommand, String by) {
    AuditEventManager auditEventManager = AuditContextHolder.get();
    writeRepository.deleteAll();
    // raise event
    auditEventManager.addDomainEvent(new AllEmployeeDeleted(new UniqueEntityIDString(), by));
    auditEventManager.addDomainEvent(
        new LogCreated(new UniqueEntityIDString(), "employee is deleted " + by));
  }
}
