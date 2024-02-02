package com.acko.dynamicdatasourcerouting.commands.employee;

import com.acko.dynamicdatasourcerouting.application.exception.EmployeeDetailsAlreadyExistException;
import com.acko.dynamicdatasourcerouting.commands.employee.models.CreateEmployeeCommand;
import com.acko.dynamicdatasourcerouting.commands.employee.models.DeleteEmployeesCommand;
import com.acko.dynamicdatasourcerouting.domain.Employee;
import com.acko.dynamicdatasourcerouting.events.AuditEvents;
import com.acko.dynamicdatasourcerouting.events.DomainEvents;
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

  public void handleCreateEmployeeCommand(
      final CreateEmployeeCommand createEmployeeCommand, final String by) {
    AuditEvents auditEvents = new AuditEvents(CreateEmployeeCommand.class);
    Employee employee = mapStructMapper.employeeCommandToEmployee(createEmployeeCommand);
    String name = employee.getEmployeeName();
    List<Employee> employeeList = writeRepository.findByEmployeeName(name);
    if (!employeeList.isEmpty()) {
      throw new EmployeeDetailsAlreadyExistException(name);
    }
    auditEvents.addDomainEvent(new EmployeeFound(auditEvents.get_id()));
    writeRepository.save(employee);
    auditEvents.addDomainEvent(new EmployeeCreated(employee, by));
    auditEvents.addDomainEvent(
        new LogCreated(auditEvents.get_id(), "employee is created " + employee.getEmployeeId()));
    // hook
    DomainEvents.dispatchEventsForAggregate(auditEvents.get_id());
  }

  public void deleteAllEmployees(DeleteEmployeesCommand deleteEmployeesCommand, String by) {
    AuditEvents auditEvents = new AuditEvents(DeleteEmployeesCommand.class);
    writeRepository.deleteAll();
    // raise event
    auditEvents.addDomainEvent(new AllEmployeeDeleted(auditEvents.get_id(), by));
    auditEvents.addDomainEvent(new LogCreated(auditEvents.get_id(), "employee is deleted " + by));
    // hook
    DomainEvents.dispatchEventsForAggregate(auditEvents.get_id());
  }
}
