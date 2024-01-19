package com.acko.dynamicdatasourcerouting.query.employee;

import com.acko.dynamicdatasourcerouting.mapstruct.dtos.EmployeeDTO;
import com.acko.dynamicdatasourcerouting.mapstruct.dtos.EmployeeSlimDTO;
import java.util.List;

public interface IEmployeeQueryService {
  List<EmployeeDTO> getEmployeeFromDB();

  List<EmployeeSlimDTO> getEmployeeSlimFromDB();
}
