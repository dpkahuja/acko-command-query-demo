package com.acko.dynamicdatasourcerouting.repository.readrepository;

import com.acko.dynamicdatasourcerouting.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeReadRepository extends CrudRepository<Employee, Long> {}
