package com.example.todo;

import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

import com.example.todo.dto.TodoCreateRequest;
import com.example.todo.dto.TodoPageResponse;
import com.example.todo.dto.TodoResponse;
import com.example.todo.dto.TodoUpdateRequest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/todos")
public class TodoResource {

    @Inject
    TodoService service;

    @Inject
    @ConfigProperty(name = "todoapp.greeting", defaultValue = "Hello from default config")
    String greetingMessage;

    @GET
    @Path("/greeting")
    @Produces(MediaType.TEXT_PLAIN)
    public String greetings() {
        return greetingMessage;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TodoPageResponse getTodos(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("done") Boolean done,
            @QueryParam("sortBy") @DefaultValue("id") String sortBy,
            @QueryParam("direction") @DefaultValue("asc") String direction) {

        return service.getPage(page, size, done, sortBy, direction);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodoById(@PathParam("id") Long id) {
        TodoResponse todo = service.findOne(id);

        return Response.ok(todo).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTodo(@Valid TodoCreateRequest request) {

        TodoResponse created = service.createTodo(request);

        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTodo(@PathParam("id") Long id, @Valid TodoUpdateRequest updated) {

        TodoResponse existing = service.updateById(id, updated);

        return Response.ok(existing).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deleteTodo(@PathParam("id") Long id) {
        service.delete(id);

        return Response.noContent().build();
    }

}
