package mx.edu.uteq.idgs09.idgs09_01.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    private final String AUTH_VALIDATE_URL = "http://20.119.81.0:8083/api/auth/validate";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        logger.info("Authorization header: {}", authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("No se recibió token o formato incorrecto");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Token requerido\"}");
            return;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            logger.info("Enviando petición a Auth Service: {}", AUTH_VALIDATE_URL);
            ResponseEntity<String> authResponse = restTemplate.exchange(
                    AUTH_VALIDATE_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            logger.info("Respuesta de Auth Service: status={}, body={}", authResponse.getStatusCode(), authResponse.getBody());
            if (authResponse.getStatusCode() == HttpStatus.OK) {
                filterChain.doFilter(request, response);
            } else {
                logger.warn("Token inválido según Auth Service");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"Token inválido\"}");
            }
        } catch (Exception e) {
            logger.error("Error al validar token con Auth Service", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Token inválido o error de autenticación\"}");
        }
    }
}