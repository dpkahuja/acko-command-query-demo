package com.acko.dynamicdatasourcerouting.events;

public class Identifier<T> {

  private final T value;

  public Identifier(T value) {
    this.value = value;
  }

  public boolean equals(Identifier<T> id) {
    if (id == null || id == null) {
      return false;
    }
    if (!id.getClass().equals(this.getClass())) {
      return false;
    }
    return id.toValue().equals(this.value);
  }

  public String toString() {
    return String.valueOf(this.value);
  }

  /** Return raw value of identifier */
  public T toValue() {
    return this.value;
  }
}
