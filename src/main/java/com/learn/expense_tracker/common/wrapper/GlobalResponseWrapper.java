package com.learn.expense_tracker.common.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.expense_tracker.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class GlobalResponseWrapper implements ResponseBodyAdvice<Object> {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private ObjectMapper objectMapper;

  @Override
  public boolean supports(
      @NonNull MethodParameter returnType,
      @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(
      @Nullable Object body,
      @NonNull MethodParameter returnType,
      @NonNull MediaType selectedContentType,
      @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
      @NonNull ServerHttpRequest request,
      @NonNull ServerHttpResponse response) {

    try {
      String path = httpServletRequest.getRequestURI();

      // Skip wrapping for specific paths like Swagger or OpenAPI documentation
      if (path.startsWith("/v3") || path.startsWith("/swagger") || path.contains("/docs")) {
        return body;
      }

      if (returnType.getParameterType().equals(String.class)) {
        return objectMapper.writeValueAsString(ApiResponse.success(body));
      }

      if (body instanceof ApiResponse && "error".equals(((ApiResponse) body).getStatus())) {
        return body;
      }

      return ApiResponse.success(body);
    } catch (Exception e) {
      throw new RuntimeException("Failed to serialize ApiResponse", e);
    }
  }
}
