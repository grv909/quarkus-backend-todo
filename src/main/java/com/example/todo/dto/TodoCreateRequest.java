package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;

public class TodoCreateRequest {

    @NotBlank(message = "Title is required")
    public String title;
    public boolean done;

    
}
