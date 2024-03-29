package com.acko.dynamicdatasourcerouting.controller.commands;

import com.acko.dynamicdatasourcerouting.commands.employee.EmployeeCommandService;
import com.acko.dynamicdatasourcerouting.datasource.DBContext;
import com.acko.dynamicdatasourcerouting.datasource.DataSourceEnum;
import com.acko.dynamicdatasourcerouting.mapstruct.dtos.CreateEmployeeCommand;
import com.acko.dynamicdatasourcerouting.mapstruct.mappers.CreateEmployeeRequestToEmployeeCommandStructMapper;
import com.acko.dynamicdatasourcerouting.models.EmployeeRequest;
import com.acko.dynamicdatasourcerouting.query.employee.EmployeeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/employee")
public class EmployeeCommandController {
  private final EmployeeCommandService employeeCommandService;
  private final EmployeeQueryService employeeQueryService;
  private final CreateEmployeeRequestToEmployeeCommandStructMapper
      createEmployeeRequestToEmployeeCommandStructMapper;

  @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  @DBContext(source = DataSourceEnum.DATASOURCE_ONE)
  public ResponseEntity<Void> addEmployee(@RequestBody EmployeeRequest employeeRequest) {
    try {
      CreateEmployeeCommand command =
          createEmployeeRequestToEmployeeCommandStructMapper.employeeRequestToEmployeeCommand(
              employeeRequest);
      employeeCommandService.handleCreateEmployeeCommand(command);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
