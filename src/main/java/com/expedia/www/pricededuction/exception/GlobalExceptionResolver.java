package com.expedia.www.pricededuction.exception;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * Resolves all global exceptions that are not affinity service-related exceptions.
 */
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
//This class is subclassed from a Spring class. Not much we can do about the fan out complexity.
@SuppressWarnings("classfanoutcomplexity")
public class GlobalExceptionResolver extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                       HttpHeaders headers,
                                                                       HttpStatus status,
                                                                       WebRequest request) {
    pageNotFoundLogger.warn(ex.getMessage());
    final Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
    if (!supportedMethods.isEmpty()) {
      headers.setAllow(supportedMethods);
    }

    return createResponse(status, "HTTP method not supported", ex, headers, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
    final List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
    if (!CollectionUtils.isEmpty(mediaTypes)) {
      headers.setAccept(mediaTypes);
    }

    return createResponse(status, "HTTP media type not supported", ex, headers, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                    HttpHeaders headers,
                                                                    HttpStatus status,
                                                                    WebRequest request) {
    return createResponse(status, "HTTP media type not acceptable", ex, headers, request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                        HttpHeaders headers,
                                                                        HttpStatus status,
                                                                        WebRequest request) {
    return createResponse(status, "Missing servlet request parameter", ex, headers, request);
  }

  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                        HttpHeaders headers,
                                                                        HttpStatus status,
                                                                        WebRequest request) {
    return createResponse(status, "Servlet request binding exception occurred", ex, headers, request);
  }

  @Override
  protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    return createResponse(status, "Conversion not supported", ex, headers, request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                      HttpHeaders headers,
                                                      HttpStatus status,
                                                      WebRequest request) {
    return createResponse(status, "Type mismatch", ex, headers, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    return createResponse(status, "HTTP message not readable", ex, headers, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    return createResponse(status, "HTTP message not writable", ex, headers, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    return createResponse(status, "Method argument not valid", ex, headers, request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
    return createResponse(status, "Missing servlet request part", ex, headers, request);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex,
                                                       HttpHeaders headers,
                                                       HttpStatus status,
                                                       WebRequest request) {
    return createResponse(status, "Bind exception occurred", ex, headers, request);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                 HttpHeaders headers,
                                                                 HttpStatus status,
                                                                 WebRequest request) {
    return createResponse(status, "No handler found", ex, headers, request);
  }

  private ResponseEntity<Object> createResponse(HttpStatus status,
                                                String message,
                                                Exception ex,
                                                HttpHeaders headers,
                                                WebRequest request) {
    final HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();

    final ErrorResponse response = new ErrorResponse(
        status,
        message,
        ExceptionUtility.getRequestUrl(httpServletRequest)
    );
    return this.handleExceptionInternal(ex, response, headers, status, request);
  }
}
