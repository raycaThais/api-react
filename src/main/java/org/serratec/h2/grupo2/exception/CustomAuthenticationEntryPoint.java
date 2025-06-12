package org.serratec.h2.grupo2.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("application/json");

        String json = """
        {
            "status": 401,
            "titulo": "Não autenticado",
            "mensagem": "Você precisa estar autenticado para acessar este recurso.",
            "dataHora": "%s"
        }
        """.formatted(LocalDateTime.now());

        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }
}
