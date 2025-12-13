package com.example.todo.dto;

import java.util.List;

public class TodoPageResponse {
    public List<TodoResponse> items;
    public int page;
    public int size;
    public long totalItems;
    public int totalPages;

}
