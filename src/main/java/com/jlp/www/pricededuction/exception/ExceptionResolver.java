package com.jlp.www.pricededuction.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

/**
 * Resolves all exceptions specifically created by the pricededuction service.
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionResolver {

  // TODO: place your custom exception resolution here

  // Example

  //  @ExceptionHandler(MyCustomException.class)
  //  @ResponseStatus(value = HttpStatus.CONFLICT)
  //  public ErrorResponse handleMyCustomException(HttpServletRequest request, MyCustomException ex) {
  //    return new ErrorResponse(HttpStatus.CONFLICT,
  //        ex.getMessage(),
  //        ExceptionUtility.getRequestUrl(request));
  //  }

  /**
   * This exception handler handles validation errors from Spring when using the @Valid or @Validation annotations.
   * @param request {@link javax.servlet.http.HttpServletRequest}
   * @param ex {@link org.springframework.web.bind.MethodArgumentNotValidException}
   * @return {@link ErrorResponse}
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
  public ErrorResponse handleException(HttpServletRequest request, MethodArgumentNotValidException ex) {

    final List<String> errorMessages = new ArrayList<>();

    final BindingResult bindingResult = ex.getBindingResult();

    if (bindingResult != null && bindingResult.hasErrors()) {
      final List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

      errorMessages.addAll(
          fieldErrorList.stream()
              .map(e -> e.getField() + " " + new DefaultMessageSourceResolvable(e).getDefaultMessage())
              .collect(Collectors.toList()));
    }

    return new ErrorResponse(
        HttpStatus.UNPROCESSABLE_ENTITY,
        errorMessages.toString(),
        ExceptionUtility.getRequestUrl(request));
  }
}
