package com.acko.dynamicdatasourcerouting.mapstruct.mappers;

import com.acko.dynamicdatasourcerouting.domain.Employee;
import com.acko.dynamicdatasourcerouting.mapstruct.dtos.CreateEmployeeCommand;
import com.acko.dynamicdatasourcerouting.mapstruct.dtos.EmployeeDTO;
import com.acko.dynamicdatasourcerouting.mapstruct.dtos.EmployeeSlimDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

  @Named("getFullName")
  static String getFullName(CreateEmployeeCommand employeeCommand) {
    return employeeCommand.getFirstName() + " " + employeeCommand.getLastName();
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
