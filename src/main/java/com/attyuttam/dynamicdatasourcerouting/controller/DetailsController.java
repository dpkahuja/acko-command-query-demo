package com.attyuttam.dynamicdatasourcerouting.controller;

import com.attyuttam.dynamicdatasourcerouting.commands.employee.CreateEmployeeCommand;
import com.attyuttam.dynamicdatasourcerouting.commands.employee.EmployeeCommandService;
import com.attyuttam.dynamicdatasourcerouting.domain.Employee;
import com.attyuttam.dynamicdatasourcerouting.query.employee.EmployeeQueryService;
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

    @GetMapping(value = "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployeeDetails(@PathVariable String employeeId) {
        if (employeeId.equals("master")) {
            return employeeQueryService.getFromMaster();
        }
        return employeeQueryService.getFromSecondary();

    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addEmployee(@RequestBody CreateEmployeeCommand employeeCommand) {
        try {
            employeeCommandService.handleCreateEmployeeCommand(employeeCommand);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
