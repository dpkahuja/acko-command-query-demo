package com.acko.dynamicdatasourcerouting.commands.employee;

import com.acko.dynamicdatasourcerouting.application.exception.EmployeeDetailsAlreadyExistException;
import com.acko.dynamicdatasourcerouting.domain.Employee;
import com.acko.dynamicdatasourcerouting.repository.writerepository.EmployeeWriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeCommandService {
    private EmployeeWriteRepository writeRepository;

    public void handleCreateEmployeeCommand(CreateEmployeeCommand command) {
        String name = command.getName();
        List<Employee> employeeList = writeRepository.findByEmployeeName(name);
        if (!employeeList.isEmpty()) {
            throw new EmployeeDetailsAlreadyExistException(name);
        }
        Employee eb = new Employee();
        eb.setEmployeeName(name);
        eb.setEmployeeRole("tester");

        writeRepository.save(eb);
    }
}