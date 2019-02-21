package com.jlp.www.pricededuction.exception;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Resolves all system errors such as 404s and 500s.
 */
@RestController
public class ErrorResolver implements ErrorController {
  private static final String ERROR_PATH = "/error";

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  /**
   * Resolved errors thrown by Spring.
   *
   * @param request  {@link javax.servlet.http.HttpServletRequest}
   * @param response {@link javax.servlet.http.HttpServletResponse}
   * @return {@link org.springframework.http.ResponseEntity}
   *     with {@link ErrorResponse}
   */
  @RequestMapping(value = ERROR_PATH)
  @ResponseBody
  public ResponseEntity<ErrorResponse> error(HttpServletRequest request,
                                             HttpServletResponse response) {
    final HttpStatus status = (response.getStatus() == 0)
        ? HttpStatus.INTERNAL_SERVER_ERROR
        : HttpStatus.valueOf(response.getStatus());
    final String url = ExceptionUtility.getRequestUrl(request);

    String message;

    switch (status) {
      case NOT_FOUND:
        message = "The requested resource could not be found";
        break;
      case INTERNAL_SERVER_ERROR:
        message = "An internal error has occurred";
        break;
      case UNAUTHORIZED:
        message = "User is not authorized";
        break;
      case FORBIDDEN:
        message = "Access is forbidden";
        break;
      default:
        message = "An unexpected error has occurred";
        break;
    }

    return new ResponseEntity<>(new ErrorResponse(status, message, url), status);
  }
}
