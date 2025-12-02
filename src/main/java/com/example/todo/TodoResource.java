package com.example.todo;

import java.util.List;

import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.example.todo.dto.TodoCreateRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.dto.TodoUpdateRequest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
    public String greetings(){
        return greetingMessage;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TodoResponse> getTodos(){
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodoById(@PathParam("id") Long id){
        TodoResponse todo = service.findOne(id);

        if(todo==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(todo).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTodo(TodoCreateRequest request){
        if(request.title==null || request.title.trim().isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity("Title cannot be empty").build();
        }
        TodoResponse created = service.createTodo(request);

        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTodo(@PathParam("id") Long id,TodoUpdateRequest updated){
         //Basic validation

        if(updated.title==null || updated.title.trim().isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity("Title Cannot be empty").build();
        }

        TodoResponse existing = service.updateById(id,updated);

        if(existing == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(existing).build();

    }

    

    @DELETE
    @Path("/{id}")
    public Response deleteTodo(@PathParam("id") Long id){
        boolean deleted = service.delete(id);

        if(!deleted){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    
   
}
