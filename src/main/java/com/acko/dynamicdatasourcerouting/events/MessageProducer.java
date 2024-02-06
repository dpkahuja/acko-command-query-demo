package com.acko.dynamicdatasourcerouting.events;

public interface MessageProducer<T> {
  void sendMessage(T message);
}
