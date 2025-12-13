package com.example.todo.exception;

public class TodoNotFoundException extends RuntimeException {
    private final Long id;

    public TodoNotFoundException(Long id){
        super("Todo with id "+id+" not found");
        this.id = id;
    }
    public Long getId(){
        return id;
    }

}
