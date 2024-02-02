package com.acko.dynamicdatasourcerouting.mapstruct.mappers;

import com.acko.dynamicdatasourcerouting.commands.employee.models.CreateEmployeeCommand;
import com.acko.dynamicdatasourcerouting.domain.Employee;
import com.acko.dynamicdatasourcerouting.mapstruct.dtos.EmployeeDTO;
import com.acko.dynamicdatasourcerouting.mapstruct.dtos.EmployeeSlimDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateEmployeeCommandToEmployeeStructMapper {

  @Named("getFullName")
  static String getFullName(CreateEmployeeCommand employeeCommand) {
    return "Mr. " + employeeCommand.getName();
  }

  @Named("setRoleName")
  static String setRoleName(CreateEmployeeCommand employeeCommand) {
    return "qa tester";
  }

  EmployeeSlimDTO employeeToEmployeeSlimDTO(Employee employee);

  EmployeeDTO employeeToEmployeeDTO(Employee employee);

  @Mappings({
    @Mapping(source = ".", target = "employeeName", qualifiedByName = "getFullName"),
    @Mapping(source = ".", target = "employeeRole", qualifiedByName = "setRoleName")
  })
  Employee employeeCommandToEmployee(CreateEmployeeCommand employeeCommand);
}
