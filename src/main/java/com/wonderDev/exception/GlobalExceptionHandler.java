package com.wonderDev.exception;

import com.wonderDev.dto.ResourceResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.validation.ConstraintViolationException;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof CustomerNotfoundException) {
            return handleCustomerNotFoundException((CustomerNotfoundException) exception);
        } else if (exception instanceof ConstraintViolationException) {
            return handleValidationException((ConstraintViolationException) exception);
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ResourceResponse.error("An unexpected error occurred", "INTERNAL_SERVER_ERROR"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private Response handleCustomerNotFoundException(CustomerNotfoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ResourceResponse.error(ex.getMessage(), "CUSTOMER_NOT_FOUND"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private Response handleValidationException(ConstraintViolationException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ResourceResponse.error("Validation failed: " + ex.getMessage(), "VALIDATION_ERROR"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
