package org.example.backend.model;

import lombok.Getter;


@Getter
public enum Family {
  CHAIR("Chair"),
  STOOL( "Stool"),
  SOFA("Sofa"),
  COUCH("Couch");

  private final String value;

  Family(String value) {
    this.value = value;
  }
}
