package com.vk.restapiproxy.handler;

import com.vk.restapiproxy.database.service.AuditService;
import com.vk.restapiproxy.database.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuditAccessDeniedHandler implements AccessDeniedHandler {

    private final AuditService auditService;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        if (request.getRequestURI().equals("/register") && UserService.getUser() != null) {
            response.sendRedirect("/register-denied");
            return;
        }
        auditService.save(auditService.create(request, false));
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
    }
}
