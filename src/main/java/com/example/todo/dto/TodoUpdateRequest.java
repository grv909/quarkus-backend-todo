package com.example.todo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class TodoUpdateRequest {

    @NotBlank(message = "Title is required")
    public String title;
    public boolean done;
}
