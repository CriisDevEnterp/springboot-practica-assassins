package com.cristiand.practica.springboot.app.springboot_practica_assassins.enums;

import static com.cristiand.practica.springboot.app.springboot_practica_assassins.constant.ErrorCodes.*;

/**
 * Enumeración que define códigos de error y descripciones correspondientes
 * utilizados en la aplicación.
 * Cada constante enum tiene un código y una descripción asociados.
 */
public enum ErrorCode {

    LIST_EMPTY(LIST_EMPTY_CODE, LIST_EMPTY_DESCRIPTION),
    NOT_FOUND(NOT_FOUND_CODE, NOT_FOUND_DESCRIPTION),
    INVALID_REQUEST(INVALID_REQUEST_CODE, INVALID_REQUEST_DESCRIPTION),
    INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR_DESCRIPTION),
    BAD_REQUEST(BAD_REQUEST_CODE, BAD_REQUEST_DESCRIPTION),
    PAGE_EMPTY(PAGE_EMPTY_CODE, PAGE_EMPTY_DESCRIPTION),
    DATA_PROCESSING(DATA_PROCESSING_ERROR_CODE, DATA_PROCESSING_ERROR_DESCRIPTION),
    NOT_AUTHENTICATED_PERMISSIONS_DENIED(NOT_AUTHENTICATED_PERMISSIONS_DENIED_CODE,
            NOT_AUTHENTICATED_PERMISSIONS_DENIED_DESCRIPTION),
    PERMISSIONS_DENIED(PERMISSIONS_DENIED_CODE, PERMISSIONS_DENIED_DESCRIPTION),
    ;

    private final String code;
    private final String description;

    /**
     * Constructor privado de ErrorCode.
     *
     * @param code        Código asociado al error.
     * @param description Descripción del error.
     */
    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Obtiene el código de error.
     *
     * @return El código de error.
     */
    public String getCode() {
        return code;
    }

    /**
     * Obtiene la descripción del error.
     *
     * @return La descripción del error.
     */
    public String getDescription() {
        return description;
    }

}
