package com.cristiand.practica.springboot.app.springboot_practica_assassins.exception;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.enums.ErrorCode;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.util.HttpResponse;
import jakarta.validation.ValidationException;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controlador global para manejar excepciones en la aplicación.
 */
@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    // Mensajes de error predefinidos.
    private static final String INTERNAL_SERVER_ERROR_MSG = "Ocurrió un error al procesar la solicitud.";
    private static final String INVALID_REQUEST_MSG = "Solicitud inválida.";
    public static final String NOT_AUTHENTICATED_PERMISSIONS_DENIED_MSG = "No estas autenticado, permisos denegados.";
    public static final String PERMISSIONS_DENIED_MSG = "No tienes los permisos necesarios para acceder al recurso.";
    private static final String NOT_FOUND_MSG = "No se encontraron registros.";
    private static final String INCORRECT_CREDENTIALS = "Nombre de usuario / contraseña incorrectos. Por favor, inténtelo de nuevo.";
    private static final String ERROR_PROCESSING_FILE = "Se produjo un error al procesar el archivo.";

    // Conjuntos de códigos de error para estados HTTP específicos.
    private static final EnumSet<ErrorCode> NOT_FOUND_ERRORS = EnumSet.of(ErrorCode.LIST_EMPTY, ErrorCode.PAGE_EMPTY,
            ErrorCode.NOT_FOUND);
    private static final EnumSet<ErrorCode> UNAUTHORIZED_ERRORS = EnumSet
            .of(ErrorCode.NOT_AUTHENTICATED_PERMISSIONS_DENIED, ErrorCode.PERMISSIONS_DENIED);
    private static final EnumSet<ErrorCode> BAD_REQUEST_ERRORS = EnumSet.of(ErrorCode.INVALID_REQUEST,
            ErrorCode.DATA_PROCESSING);

    /**
     * Maneja las excepciones de tipo CustomAssassinException.
     *
     * @param exception la excepción a manejar.
     * @return ResponseEntity con los detalles del error.
     */
    @ExceptionHandler(CustomAssassinException.class)
    public ResponseEntity<HttpResponse> handleCustomAssassinException(CustomAssassinException exception) {
        HttpStatus status = determineHttpStatus(exception.getCode());
        String message = exception.getMessage();
        Map<String, Object> errorsDetails = exception.getErrorsDetails();

        return errorsDetails.isEmpty() ? createHttpResponseEnums(exception.getCode(), status, message)
                : createHttpResponseEnumsWithDetails(exception.getCode(), status, message, errorsDetails);
    }

    /**
     * Determina el estado HTTP basado en el código de error.
     *
     * @param errorCode el código de error.
     * @return el estado HTTP correspondiente.
     */
    private HttpStatus determineHttpStatus(ErrorCode errorCode) {
        if (NOT_FOUND_ERRORS.contains(errorCode)) {
            return HttpStatus.NOT_FOUND;
        } else if (UNAUTHORIZED_ERRORS.contains(errorCode)) {
            return HttpStatus.UNAUTHORIZED;
        } else if (BAD_REQUEST_ERRORS.contains(errorCode)) {
            return HttpStatus.BAD_REQUEST;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * Maneja las excepciones de tipo AuthenticationException.
     *
     * @return ResponseEntity con los detalles del error.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<HttpResponse> handleGenericAuthenticationException() {
        return createHttpResponseEnums(ErrorCode.NOT_AUTHENTICATED_PERMISSIONS_DENIED, HttpStatus.FORBIDDEN,
                NOT_AUTHENTICATED_PERMISSIONS_DENIED_MSG);
    }

    /**
     * Maneja las excepciones de tipo AccessDeniedException.
     *
     * @return ResponseEntity con los detalles del error.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> handleGenericAccessDeniedException() {
        return createHttpResponseEnums(ErrorCode.PERMISSIONS_DENIED, HttpStatus.FORBIDDEN, PERMISSIONS_DENIED_MSG);
    }

    /**
     * Maneja las excepciones de tipo BadCredentialsException.
     *
     * @return ResponseEntity con los detalles del error.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException() {
        return createHttpResponseOriginal(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    /**
     * Maneja las excepciones de tipo NullPointerException.
     *
     * @param exception la excepción a manejar.
     * @return ResponseEntity con los detalles del error.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<HttpResponse> handleGenericNullPointerException(NullPointerException exception) {
        if (exception.getMessage().isEmpty()) {
            return createHttpResponseOriginal(HttpStatus.NOT_ACCEPTABLE, NOT_FOUND_MSG);
        } else {
            return createHttpResponseOriginal(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        }
    }

    /**
     * Maneja las excepciones de tipo IllegalArgumentException.
     *
     * @param exception la excepción a manejar.
     * @return ResponseEntity con los detalles del error.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpResponse> handleGenericIllegalArgumentException(IllegalArgumentException exception) {
        return createHttpResponseOriginal(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    /**
     * Maneja las excepciones de tipo IllegalStateException.
     *
     * @param exception la excepción a manejar.
     * @return ResponseEntity con los detalles del error.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<HttpResponse> handleIllegalStateException(IllegalStateException exception) {
        return createHttpResponseOriginal(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    /**
     * Maneja las excepciones de tipo ValidationException.
     *
     * @param exception la excepción a manejar.
     * @return ResponseEntity con los detalles del error.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<HttpResponse> handleGenericValidationException(ValidationException exception) {
        if (exception.getMessage().isEmpty()) {
            return createHttpResponseOriginal(HttpStatus.BAD_REQUEST, INVALID_REQUEST_MSG);
        } else {
            return createHttpResponseOriginal(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    /**
     * Maneja cualquier otra excepción genérica.
     *
     * @param exception la excepción a manejar.
     * @return ResponseEntity con los detalles del error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> handleGenericException(Exception exception) {
        if (exception.getMessage().isEmpty()) {
            return createHttpResponseOriginal(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
        } else {
            return createHttpResponseOriginal(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    /**
     * Maneja las excepciones de tipo IOException.
     *
     * @param exception la excepción a manejar.
     * @return ResponseEntity con los detalles del error.
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> handleGenericIOException(IOException exception) {
        if (exception.getMessage().isEmpty()) {
            return createHttpResponseOriginal(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
        } else {
            return createHttpResponseOriginal(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    /**
     * Crea una respuesta HTTP básica.
     *
     * @param httpStatus el estado HTTP.
     * @param message    el mensaje de error.
     * @return ResponseEntity con los detalles del error.
     */
    private ResponseEntity<HttpResponse> createHttpResponseOriginal(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(
                new HttpResponse(
                        httpStatus.value(),
                        httpStatus,
                        httpStatus.getReasonPhrase().toUpperCase(),
                        message),
                httpStatus);
    }

    /**
     * Crea una respuesta HTTP con código de error enum.
     *
     * @param errorCode  el código de error.
     * @param httpStatus el estado HTTP.
     * @param message    el mensaje de error.
     * @return ResponseEntity con los detalles del error.
     */
    public ResponseEntity<HttpResponse> createHttpResponseEnums(ErrorCode errorCode, HttpStatus httpStatus,
            String message) {
        return new ResponseEntity<>(
                new HttpResponse(
                        errorCode.getCode(),
                        httpStatus,
                        errorCode.getDescription(),
                        message),
                httpStatus);
    }

    /**
     * Crea una respuesta HTTP con código de error enum y detalles adicionales.
     *
     * @param errorCode  el código de error.
     * @param httpStatus el estado HTTP.
     * @param message    el mensaje de error.
     * @param details    detalles adicionales del error.
     * @return ResponseEntity con los detalles del error.
     */
    private ResponseEntity<HttpResponse> createHttpResponseEnumsWithDetails(ErrorCode errorCode, HttpStatus httpStatus,
            String message, Map<String, Object> details) {
        return new ResponseEntity<>(
                new HttpResponse(
                        errorCode.getCode(),
                        httpStatus,
                        errorCode.getDescription(),
                        message,
                        details),
                httpStatus);
    }

}
