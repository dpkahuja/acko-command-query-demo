package com.attyuttam.dynamicdatasourcerouting.query.employee;

import com.attyuttam.dynamicdatasourcerouting.domain.Employee;
import com.attyuttam.dynamicdatasourcerouting.datasource.DBContext;
import com.attyuttam.dynamicdatasourcerouting.datasource.DataSourceContextHolder;
import com.attyuttam.dynamicdatasourcerouting.datasource.DataSourceEnum;
import com.attyuttam.dynamicdatasourcerouting.repository.readrepository.EmployeeReadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class EmployeeQueryService {
    private final EmployeeReadRepository readRepository;

    @DBContext(source = DataSourceEnum.DATASOURCE_TWO)
    public List<Employee> getFromSecondary() {
        List<Employee> result = new ArrayList<Employee>();
        Iterable<Employee> itr = readRepository.findAll();
        itr.forEach(result::add);
        return result;
    }

    @DBContext(source = DataSourceEnum.DATASOURCE_ONE)
    public List<Employee> getFromMaster() {
        List<Employee> result = new ArrayList<Employee>();
        Iterable<Employee> itr = readRepository.findAll();
        itr.forEach(result::add);
        return result;
    }
}
