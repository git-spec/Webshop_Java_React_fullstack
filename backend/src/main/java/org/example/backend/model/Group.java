package org.example.backend.model;

public enum Group {
  TABLE("Table"),
  STORAGE("Storage"),
  SEATING("Seating"),
  BED("Bed");

  private final String value;

  Group(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
