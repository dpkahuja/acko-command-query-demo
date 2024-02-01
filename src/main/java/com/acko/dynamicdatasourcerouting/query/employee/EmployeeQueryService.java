package com.acko.dynamicdatasourcerouting.query.employee;

import com.acko.dynamicdatasourcerouting.domain.Employee;
import com.acko.dynamicdatasourcerouting.mapstruct.dtos.EmployeeDTO;
import com.acko.dynamicdatasourcerouting.mapstruct.dtos.EmployeeSlimDTO;
import com.acko.dynamicdatasourcerouting.mapstruct.mappers.CreateEmployeeCommandToEmployeeStructMapperImpl;
import com.acko.dynamicdatasourcerouting.repository.readrepository.EmployeeReadRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmployeeQueryService implements IEmployeeQueryService {
  private final EmployeeReadRepository readRepository;
  private final CreateEmployeeCommandToEmployeeStructMapperImpl mapStructMapper;

  public List<EmployeeDTO> getEmployeeFromDB() {
    List<EmployeeDTO> result = new ArrayList<>();
    Iterable<Employee> itr = readRepository.findAll();
    itr.forEach(i -> result.add(mapStructMapper.employeeToEmployeeDTO(i)));
    return result;
  }

  public List<EmployeeSlimDTO> getEmployeeSlimFromDB() {
    List<EmployeeSlimDTO> result = new ArrayList<>();
    Iterable<Employee> itr = readRepository.findAll();
    itr.forEach(i -> result.add(mapStructMapper.employeeToEmployeeSlimDTO(i)));
    return result;
  }
}
