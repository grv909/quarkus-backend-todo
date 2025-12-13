package com.example.todo.exception;

import java.util.stream.Collectors;

import com.example.todo.dto.ErrorResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        String message = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        ErrorResponse body = new ErrorResponse(
                Response.Status.BAD_REQUEST.getStatusCode(),
                "Bad Request",
                message,
                uriInfo != null ? uriInfo.getPath() : null);

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }

}
