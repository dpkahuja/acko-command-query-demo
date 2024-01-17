package com.acko.dynamicdatasourcerouting.repository.writerepository;

import com.acko.dynamicdatasourcerouting.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeWriteRepository extends CrudRepository<Employee, Long> {
    List<Employee> findByEmployeeName(String name);
}