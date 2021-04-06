package com.berkan.productscraper.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Only one parameter accepted")
public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }

}

