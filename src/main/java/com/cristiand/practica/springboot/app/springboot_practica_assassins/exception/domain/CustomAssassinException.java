package com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.enums.ErrorCode;
import java.util.HashMap;
import java.util.Map;

/**
 * Excepción personalizada utilizada para manejar errores específicos dentro de
 * la aplicación.
 * Esta excepción puede contener un código de error {@link ErrorCode}, detalles
 * adicionales de errores y un mensaje descriptivo del error.
 */
public class CustomAssassinException extends RuntimeException {

    private ErrorCode code;
    private Map<String, Object> errorsDetails = new HashMap<>();

    /**
     * Constructor que acepta un mensaje de error, una causa raíz y un código de
     * error.
     *
     * @param message Mensaje descriptivo del error.
     * @param cause   Causa raíz de la excepción.
     * @param code    Código de error {@link ErrorCode}.
     */
    public CustomAssassinException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    /**
     * Constructor que acepta un mensaje de error y un código de error.
     *
     * @param message Mensaje descriptivo del error.
     * @param code    Código de error {@link ErrorCode}.
     */
    public CustomAssassinException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    /**
     * Constructor que acepta un mensaje de error y una causa raíz.
     *
     * @param message Mensaje descriptivo del error.
     * @param cause   Causa raíz de la excepción.
     */
    public CustomAssassinException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param message Mensaje descriptivo del error.
     */
    public CustomAssassinException(String message) {
        super(message);
    }

    /**
     * Constructor que acepta un mensaje de error, un código de error y detalles
     * adicionales de errores.
     *
     * @param message       Mensaje descriptivo del error.
     * @param code          Código de error {@link ErrorCode}.
     * @param errorsDetails Detalles adicionales de errores.
     */
    public CustomAssassinException(String message, ErrorCode code, Map<String, Object> errorsDetails) {
        super(message);
        this.code = code;
        this.errorsDetails = errorsDetails;
    }

    /**
     * Obtiene el código de error asociado a esta excepción.
     *
     * @return Código de error {@link ErrorCode}.
     */
    public ErrorCode getCode() {
        return code;
    }

    /**
     * Obtiene los detalles adicionales de errores asociados a esta excepción.
     *
     * @return Mapa de detalles adicionales de errores.
     */
    public Map<String, Object> getErrorsDetails() {
        return errorsDetails;
    }

    /**
     * Obtiene el mensaje descriptivo del error.
     *
     * @return Mensaje descriptivo del error.
     */
    public String getErrorMessage() {
        return super.getMessage();
    }

}
