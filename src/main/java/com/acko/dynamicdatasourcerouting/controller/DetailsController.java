package com.acko.dynamicdatasourcerouting.controller;

import com.acko.dynamicdatasourcerouting.commands.employee.CreateEmployeeCommand;
import com.acko.dynamicdatasourcerouting.commands.employee.EmployeeCommandService;
import com.acko.dynamicdatasourcerouting.datasource.DBContext;
import com.acko.dynamicdatasourcerouting.datasource.DataSourceEnum;
import com.acko.dynamicdatasourcerouting.domain.Employee;
import com.acko.dynamicdatasourcerouting.query.employee.EmployeeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/employee")
public class DetailsController {
    private final EmployeeCommandService employeeCommandService;
    private final EmployeeQueryService employeeQueryService;

    @GetMapping(value = "/sync", produces = MediaType.APPLICATION_JSON_VALUE)
    @DBContext(source = DataSourceEnum.DATASOURCE_ONE)
    public List<Employee> getAllEmployeeDetails() {
            return employeeQueryService.getFromDB();
    }

    @GetMapping(value = "/async", produces = MediaType.APPLICATION_JSON_VALUE)
    @DBContext(source = DataSourceEnum.DATASOURCE_TWO)
    public List<Employee> getAllEmployeeDetailsAsync() {
        return employeeQueryService.getFromDB();
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @DBContext(source = DataSourceEnum.DATASOURCE_ONE)
    public ResponseEntity<Void> addEmployee(@RequestBody CreateEmployeeCommand employeeCommand) {
        try {
            employeeCommandService.handleCreateEmployeeCommand(employeeCommand);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
