package com.acko.dynamicdatasourcerouting.events.employee;

import org.springframework.kafka.annotation.KafkaListener;

public class EmployeeCreatedConsumer {

  @KafkaListener(topics = "topicOne", groupId = "topicOne")
  public void listenGroupFoo(String message) {
    System.out.println("Received Message in group foo: " + message);
  }
}
