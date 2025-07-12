package com.learn.expense_tracker.common.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.*;

import com.learn.expense_tracker.common.annotation.CurrentUser;
import com.learn.expense_tracker.common.dto.ErrorCode;
import com.learn.expense_tracker.common.exception.ApiException;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.user.User;
import com.learn.expense_tracker.user.UserRepository;

@Component
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public CurrentUserResolver(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(@Nullable MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            @Nullable NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory) {
        if (webRequest == null) {
            throw new ApiException(ErrorCode.INVALID_AUTHORIZATION_HEADER);
        }
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtService.isTokenExpired(token)) {
                throw new ApiException(ErrorCode.INVALID_AUTHORIZATION_HEADER);
            }
            Long userId = jwtService.extractUserId(token);
            if (userId == null) {
                throw new ApiException(ErrorCode.INVALID_AUTHORIZATION_HEADER);
            }

            return userRepository.findById(userId)
                    .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        }
        throw new ApiException(ErrorCode.INVALID_AUTHORIZATION_HEADER);
    }   
}