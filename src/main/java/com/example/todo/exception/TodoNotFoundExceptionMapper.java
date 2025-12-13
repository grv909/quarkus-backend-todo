package com.example.todo.exception;

import com.example.todo.dto.ErrorResponse;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TodoNotFoundExceptionMapper implements ExceptionMapper<TodoNotFoundException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(TodoNotFoundException exception) {
        ErrorResponse body = new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), "Not Found",
                exception.getMessage(), uriInfo != null ? uriInfo.getPath() : null);

        return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(body).build();
    }

}
