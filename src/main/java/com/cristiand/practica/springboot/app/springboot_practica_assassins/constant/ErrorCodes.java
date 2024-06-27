package com.cristiand.practica.springboot.app.springboot_practica_assassins.constant;

public class ErrorCodes {

    public static final String LIST_EMPTY_CODE = "E001";
    public static final String LIST_EMPTY_DESCRIPTION = "La lista está vacía.";

    public static final String NOT_FOUND_CODE = "E002";
    public static final String NOT_FOUND_DESCRIPTION = "El registro no existe.";

    public static final String INVALID_REQUEST_CODE = "E003";
    public static final String INVALID_REQUEST_DESCRIPTION = "Parámetros de solicitud inválidos.";

    public static final String INTERNAL_SERVER_ERROR_CODE = "E004";
    public static final String INTERNAL_SERVER_ERROR_DESCRIPTION = "Error interno del servidor.";

    public static final String BAD_REQUEST_CODE = "E005";
    public static final String BAD_REQUEST_DESCRIPTION = "Solicitud incorrecta.";

    public static final String PAGE_EMPTY_CODE = "E006";
    public static final String PAGE_EMPTY_DESCRIPTION = "La página está vacía.";

    public static final String DATA_PROCESSING_ERROR_CODE = "E007";
    public static final String DATA_PROCESSING_ERROR_DESCRIPTION = "Ha ocurrido un error durante el procesamiento.";

    public static final String NOT_AUTHENTICATED_PERMISSIONS_DENIED_CODE = "E008";
    public static final String NOT_AUTHENTICATED_PERMISSIONS_DENIED_DESCRIPTION = "Acceso a recurso protegido sin autenticación.";

    public static final String PERMISSIONS_DENIED_CODE = "E009";
    public static final String PERMISSIONS_DENIED_DESCRIPTION = "Acceso a recurso protegido.";

}
