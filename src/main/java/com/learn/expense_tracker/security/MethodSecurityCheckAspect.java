package com.learn.expense_tracker.security;

import com.learn.expense_tracker.common.annotation.Public;
import com.learn.expense_tracker.common.annotation.RequireRoles;
import com.learn.expense_tracker.common.dto.ErrorCode;
import com.learn.expense_tracker.common.exception.ApiException;
import jakarta.annotation.security.RolesAllowed;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodSecurityCheckAspect {

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  public void restControllerMethods() {}

  @Before("restControllerMethods()")
  public void checkSecurityAnnotation(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    // Skip Swagger / SpringDoc controllers
    String path = signature.getMethod().getDeclaringClass().getName();
    if (path.contains("springdoc") || path.contains("swagger") || path.contains("OpenApi")) {
      return;
    }

    // If method or class is annotated with @Public, skip security checks
    if (method.isAnnotationPresent(Public.class)
        || method.getDeclaringClass().isAnnotationPresent(Public.class)) {
      return;
    }

    // If method is annotated with @RequireRoles, check user roles
    if (method.isAnnotationPresent(RequireRoles.class)) {
      RequireRoles requireRoles = method.getAnnotation(RequireRoles.class);
      checkUserHasRoles(requireRoles.value());
      return;
    }

    // If method is not annotated with any security annotations, block access
    boolean hasSpringSecurity =
        method.isAnnotationPresent(PreAuthorize.class)
            || method.isAnnotationPresent(Secured.class)
            || method.isAnnotationPresent(RolesAllowed.class);

    if (!hasSpringSecurity) {
      throw new ApiException(ErrorCode.FORBIDDEN);
    }
  }

  private void checkUserHasRoles(String[] rolesRequired) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !auth.isAuthenticated()) {
      throw new ApiException(ErrorCode.UNAUTHORIZED);
    }

    List<String> userRoles =
        auth.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    boolean hasRole =
        Arrays.stream(rolesRequired).map(role -> "ROLE_" + role).anyMatch(userRoles::contains);

    if (!hasRole) {
      throw new ApiException(ErrorCode.FORBIDDEN);
    }
  }
}
