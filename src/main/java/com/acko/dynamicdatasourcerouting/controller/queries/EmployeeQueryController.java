package com.acko.dynamicdatasourcerouting.controller.queries;

import com.acko.dynamicdatasourcerouting.commands.employee.EmployeeCommandService;
import com.acko.dynamicdatasourcerouting.datasource.DBContext;
import com.acko.dynamicdatasourcerouting.datasource.DataSourceEnum;
import com.acko.dynamicdatasourcerouting.mapstruct.dtos.EmployeeDTO;
import com.acko.dynamicdatasourcerouting.mapstruct.dtos.EmployeeSlimDTO;
import com.acko.dynamicdatasourcerouting.query.employee.EmployeeQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/employee")
public class EmployeeQueryController {
  private final EmployeeCommandService employeeCommandService;
  private final EmployeeQueryService employeeQueryService;

  @GetMapping(value = "/sync", produces = MediaType.APPLICATION_JSON_VALUE)
  @DBContext(source = DataSourceEnum.DATASOURCE_ONE)
  public List<EmployeeDTO> getAllEmployeeDetails() {
    return employeeQueryService.getEmployeeFromDB();
  }

  @GetMapping(value = "/sync/slim", produces = MediaType.APPLICATION_JSON_VALUE)
  @DBContext(source = DataSourceEnum.DATASOURCE_ONE)
  public List<EmployeeSlimDTO> getAllEmployeeSlimDetails() {
    return employeeQueryService.getEmployeeSlimFromDB();
  }

  @GetMapping(value = "/async", produces = MediaType.APPLICATION_JSON_VALUE)
  @DBContext(source = DataSourceEnum.DATASOURCE_TWO)
  public List<EmployeeDTO> getAllEmployeeDetailsAsync() {
    return employeeQueryService.getEmployeeFromDB();
  }
}
