package com.jlp.www.pricededuction.exception;

import javax.servlet.http.HttpServletRequest;

/**
 * Common methods for exception resolvers.
 */
class ExceptionUtility {
  private static final String FORWARD_REQUEST_URI = "javax.servlet.forward.request_uri";
  private static final String FORWARD_REQUEST_QUERY = "javax.servlet.forward.query_string";

  private ExceptionUtility() {
  }

  /**
   * Extracts the full request URL from the {@link javax.servlet.http.HttpServletRequest}
   *
   * @param request {@link javax.servlet.http.HttpServletRequest}
   * @return Full request URL including URI and query string
   */
  static String getRequestUrl(HttpServletRequest request) {
    final Object forwardedUri = request.getAttribute(FORWARD_REQUEST_URI);

    // Errors on the site (404, 500) will be redirected to /error by Spring
    // So check for a forwarded URI and use that instead of showing the caller the /error URL
    // Normal exceptions should have a non-forwarded URI
    if (forwardedUri == null) {
      final String uri = request.getRequestURI();
      final String query = request.getQueryString();

      return query == null ? uri : uri + "?" + query;
    }
    else {
      final Object query = request.getAttribute(FORWARD_REQUEST_QUERY);
      return query == null ? forwardedUri.toString() : forwardedUri.toString() + "?" + query.toString();
    }
  }
}
