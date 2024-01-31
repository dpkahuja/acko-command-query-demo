package com.acko.dynamicdatasourcerouting.domain;

import com.acko.dynamicdatasourcerouting.events.EmployeeCreated;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee extends AbstractAggregateRoot {

  @Id // primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
  @Column(name = "employeeId")
  private int employeeId;

  @Column(name = "employeeName")
  private String employeeName;

  @Column(name = "employeeRole")
  private String employeeRole;

  public Employee complete() {
    registerEvent(new EmployeeCreated(this));
    return this;
  }
}
