package com.acko.dynamicdatasourcerouting.events;

import java.util.UUID;

public class UniqueEntityIDString extends Identifier<String> {

  public UniqueEntityIDString(String id) {
    super(id != null ? id : UUID.randomUUID().toString());
  }

  public UniqueEntityIDString() {
    super(UUID.randomUUID().toString());
  }
}
