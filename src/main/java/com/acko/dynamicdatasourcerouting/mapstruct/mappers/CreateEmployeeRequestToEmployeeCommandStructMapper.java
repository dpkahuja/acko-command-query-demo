package com.acko.dynamicdatasourcerouting.mapstruct.mappers;

import com.acko.dynamicdatasourcerouting.mapstruct.dtos.CreateEmployeeCommand;
import com.acko.dynamicdatasourcerouting.models.EmployeeRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateEmployeeRequestToEmployeeCommandStructMapper {

  @Named("getFullName")
  static String getFullName(EmployeeRequest employeeRequest) {
    return employeeRequest.getFirstName() + " " + employeeRequest.getLastName();
  }

  @Mappings({@Mapping(source = ".", target = "name", qualifiedByName = "getFullName")})
  CreateEmployeeCommand employeeRequestToEmployeeCommand(EmployeeRequest employeeRequest);
}
