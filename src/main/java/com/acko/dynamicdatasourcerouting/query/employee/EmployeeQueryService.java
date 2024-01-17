package com.acko.dynamicdatasourcerouting.query.employee;

import com.acko.dynamicdatasourcerouting.domain.Employee;
import com.acko.dynamicdatasourcerouting.repository.readrepository.EmployeeReadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class EmployeeQueryService {
    private final EmployeeReadRepository readRepository;


    public List<Employee> getFromDB() {
        List<Employee> result = new ArrayList<Employee>();
        Iterable<Employee> itr = readRepository.findAll();
        itr.forEach(result::add);
        return result;
    }
}
