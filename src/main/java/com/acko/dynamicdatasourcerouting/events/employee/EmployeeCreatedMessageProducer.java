package com.acko.dynamicdatasourcerouting.events.employee;

import com.acko.dynamicdatasourcerouting.events.MessageProducer;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class EmployeeCreatedMessageProducer<EmployeeCreated>
    implements MessageProducer<EmployeeCreated> {

  @Autowired private KafkaTemplate<String, EmployeeCreated> kafkaTemplate;

  @Value(value = "${spring.kafka.message.topicOne}")
  private String topicName;

  public CompletableFuture<SendResult<String, EmployeeCreated>> sendMessage(
      EmployeeCreated message) {

    CompletableFuture<SendResult<String, EmployeeCreated>> future =
        kafkaTemplate.send(topicName, message);
    return future;
    //    future.whenComplete(
    //        (result, ex) -> {
    //          if (ex == null) {
    //            System.out.println(
    //                "Sent message=["
    //                    + message
    //                    + "] with offset=["
    //                    + result.getRecordMetadata().offset()
    //                    + "]");
    //          } else {
    //            System.out.println(
    //                "Unable to send message=[" + message + "] due to : " + ex.getMessage());
    //          }
    //        });
  }
}
