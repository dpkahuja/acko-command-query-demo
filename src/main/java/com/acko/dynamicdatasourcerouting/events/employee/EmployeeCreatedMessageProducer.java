package com.acko.dynamicdatasourcerouting.events.employee;

import com.acko.dynamicdatasourcerouting.events.MessageProducer;
import com.acko.dynamicdatasourcerouting.events.employee.models.EmployeeCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class EmployeeCreatedMessageProducer<EmployeeCreated>
    implements MessageProducer<EmployeeCreated> {

  @Autowired
  private KafkaTemplate<String, EmployeeCreated> kafkaTemplate;

  @Value(value = "${spring.kafka.message.topicOne}")
  private String topicName;

  public void sendMessage(EmployeeCreated message) {

    ListenableFuture<SendResult<String, EmployeeCreated>> future =
        kafkaTemplate.send(topicName, message);
    future.addCallback(
        new ListenableFutureCallback<SendResult<String, EmployeeCreated>>() {

          @Override
          public void onSuccess(SendResult<String, EmployeeCreated> result) {
            System.out.println(
                "Sent message=["
                    + message
                    + "] with offset=["
                    + result.getRecordMetadata().offset()
                    + "]");
          }

          @Override
          public void onFailure(Throwable ex) {
            System.out.println(
                "Unable to send message=[" + message + "] due to : " + ex.getMessage());
          }
        });
  }
}
