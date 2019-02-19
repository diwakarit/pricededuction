package com.expedia.www.pricededuction.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Standard error response for REST calls.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
  private static final String DEFAULT_DOCUMENTATION_URL
      = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString();
  private final int status;
  private final String message;
  private final String requestUrl;
  private final String documentationUrl;


  ErrorResponse(HttpStatus status, String message, String requestUrl) {
    this(status, message, requestUrl, DEFAULT_DOCUMENTATION_URL);
  }

  ErrorResponse(HttpStatus status, String message, String requestUrl, String documentationUrl) {
    this.status = status.value();
    this.message = message;
    this.requestUrl = requestUrl;
    this.documentationUrl = documentationUrl;
  }
}
