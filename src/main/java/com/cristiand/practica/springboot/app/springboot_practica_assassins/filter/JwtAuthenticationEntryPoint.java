package com.cristiand.practica.springboot.app.springboot_practica_assassins.filter;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.enums.ErrorCode;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.ExceptionHandling;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.util.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import static com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.ExceptionHandling.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Componente que implementa AuthenticationEntryPoint para manejar la entrada de
 * autenticación JWT.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ExceptionHandling exceptionHandling;

    /**
     * Método que se invoca cuando se necesita autenticación pero no se proporciona
     * ninguna.
     * Crea y envía una respuesta HTTP con el código de error y mensaje adecuados.
     *
     * @param request       La solicitud HTTP que originó la excepción.
     * @param response      La respuesta HTTP donde se escribirá la respuesta de
     *                      error.
     * @param authException La excepción de autenticación que desencadenó la
     *                      entrada.
     * @throws IOException      Si ocurre un error al escribir la respuesta.
     * @throws ServletException Si ocurre un error relacionado con el servlet al
     *                          manejar la solicitud.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        // Crea el ResponseEntity usando el método existente de manejo de excepciones
        ResponseEntity<HttpResponse> responseEntity = exceptionHandling.createHttpResponseEnums(
                ErrorCode.NOT_AUTHENTICATED_PERMISSIONS_DENIED,
                HttpStatus.FORBIDDEN, NOT_AUTHENTICATED_PERMISSIONS_DENIED_MSG);

        // Extrae el cuerpo del ResponseEntity
        HttpResponse httpResponse = responseEntity.getBody();

        // Configura el contenido de la respuesta
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        // Escribe el cuerpo de la respuesta JSON en el OutputStream de la respuesta
        // HTTP
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }

}
