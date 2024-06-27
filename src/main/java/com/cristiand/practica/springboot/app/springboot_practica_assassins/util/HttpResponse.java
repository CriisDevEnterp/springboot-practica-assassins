package com.cristiand.practica.springboot.app.springboot_practica_assassins.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.Map;
import org.springframework.http.HttpStatus;

// Esta anotación evita que se incluyan campos null en la respuesta JSON
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss", timezone = "America/Mexico_City")
    private Date timeStamp;
    private Integer httpStatusCodeOriginal;
    private String httpStatusCodeEnums;
    private HttpStatus httpStatus;
    private String reason;
    private String message;
    private Map<String, Object> details;

    // Constructor para httpStatusCodeOriginal
    public HttpResponse(Integer httpStatusCodeOriginal, HttpStatus httpStatus, String reason, String message) {
        this.timeStamp = new Date();
        this.httpStatusCodeOriginal = httpStatusCodeOriginal;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }

    // Constructor para httpStatusCodeEnums
    public HttpResponse(String httpStatusCodeEnums, HttpStatus httpStatus, String reason, String message) {
        this.timeStamp = new Date();
        this.httpStatusCodeEnums = httpStatusCodeEnums;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }

    // Constructor para httpStatusCodeEnumsWithDetails
    public HttpResponse(String httpStatusCodeEnums, HttpStatus httpStatus, String reason, String message,
            Map<String, Object> details) {
        this.timeStamp = new Date();
        this.httpStatusCodeEnums = httpStatusCodeEnums;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
        this.details = details;
    }

    // Getters para timeStamp, httpStatus, reason, message, details (no se cambian)

    public Date getTimeStamp() {
        return timeStamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    // Getter específico para httpStatusCodeOriginal
    public Integer getHttpStatusCodeOriginal() {
        return httpStatusCodeOriginal;
    }

    // Getter específico para httpStatusCodeEnums
    public String getHttpStatusCodeEnums() {
        return httpStatusCodeEnums;
    }

}
