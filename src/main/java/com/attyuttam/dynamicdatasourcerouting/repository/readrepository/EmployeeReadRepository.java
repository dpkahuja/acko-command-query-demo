package com.attyuttam.dynamicdatasourcerouting.repository.readrepository;

import com.attyuttam.dynamicdatasourcerouting.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeReadRepository extends CrudRepository<Employee, Long> {
}