package dev.vaccinelocator;

public class CowinException extends RuntimeException{

  public CowinException(String message) {
    super("Exception Occoured "+message);
  }
}
