package com.attyuttam.dynamicdatasourcerouting.repository.writerepository;

import com.attyuttam.dynamicdatasourcerouting.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeWriteRepository extends CrudRepository<Employee, Long> {
    List<Employee> findByEmployeeName(String name);
}