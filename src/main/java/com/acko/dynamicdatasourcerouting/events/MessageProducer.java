package com.acko.dynamicdatasourcerouting.events;

import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.support.SendResult;

public interface MessageProducer<T> {
  CompletableFuture<SendResult<String, T>> sendMessage(T message);
}
