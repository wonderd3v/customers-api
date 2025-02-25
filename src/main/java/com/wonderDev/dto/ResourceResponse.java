package com.wonderDev.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceResponse<T> {

    private String status;
    private String message;
    private T data;
    private String errorCode;
    private Pagination pagination;

    public ResourceResponse(String status, String message, T data, String errorCode, Pagination pagination) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.pagination = pagination;
    }

    public static <T> ResourceResponse<T> success(String message, T data, Pagination pagination) {
        return new ResourceResponse<>("success", message, data, null, pagination);
    }

    public static <T> ResourceResponse<T> success(String message, T data) {
        return new ResourceResponse<>("success", message, data, null, null);
    }

    public static ResourceResponse<Void> success(String message) {
        return new ResourceResponse<>("success", message, null, null, null);
    }

    public static ResourceResponse<Void> error(String message, String errorCode) {
        return new ResourceResponse<>("error", message, null, errorCode, null);
    }

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public String getErrorCode() { return errorCode; }
    public Pagination getPagination() { return pagination; }
}
