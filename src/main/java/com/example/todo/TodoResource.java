package com.example.todo;

import java.util.List;

import jakarta.inject.Inject;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Todo> getTodos(){
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodoById(@PathParam("id") Long id){
        Todo todo = service.findById(id);

        if(todo==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(todo).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Todo createTodo(Todo todo){
        return service.createTodo(todo);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTodo(@PathParam("id") Long id,Todo updated){
         //Basic validation

        if(updated.title==null || updated.title.trim().isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity("Title Cannot be empty").build();
        }

        Todo existing = service.updateById(id,updated);

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
