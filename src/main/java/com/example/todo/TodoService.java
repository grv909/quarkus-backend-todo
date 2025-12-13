package com.example.todo;

import java.util.List;

import javax.print.DocFlavor.STRING;

import com.example.todo.dto.TodoCreateRequest;
import com.example.todo.dto.TodoPageResponse;
import com.example.todo.dto.TodoResponse;
import com.example.todo.dto.TodoUpdateRequest;
import com.example.todo.exception.TodoNotFoundException;
import com.example.todo.mapper.TodoMapper;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TodoService {

    @Inject
    TodoMapper mapper;

    // Get all at once
    // public List<TodoResponse> getAll() {
    // List<Todo> todos = Todo.listAll();
    // return mapper.toResponseList(todos);
    // }

    // Pagination

    public TodoPageResponse getPage(int page, int size, Boolean done, String sortBy, String direction) {

        // Basic Safety defaults
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "id";
        }
        if (direction == null || direction.isBlank()) {
            direction = "asc";
        }

        // Build Sort

        Sort sort = Sort.by(sortBy);

        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        // Build query: filter by "done" if provided
        PanacheQuery<Todo> query;
        if (done != null) {
            query = Todo.find("done= ?1", sort, done);

        } else {
            query = Todo.findAll(sort);
        }

        // Apply paging
        query = query.page(Page.of(page, size));

        // Fetch current page items
        var todos = query.list();

        // Count total items (ignores paging)
        long totalItems = query.count();
        int totalPages = (int) Math.ceil((double) totalItems / size);

        // Map to DTO
        TodoPageResponse response = new TodoPageResponse();
        response.items = mapper.toResponseList(todos);
        response.page = page;
        response.size = size;
        response.totalItems = totalItems;
        response.totalPages = totalPages;

        return response;

    }

    @Transactional
    public TodoResponse createTodo(TodoCreateRequest request) {
        Todo todo = mapper.toEntity(request);
        todo.id = null;
        todo.persist();
        return mapper.toResponse(todo);
    }

    public TodoResponse findOne(Long id) {
        Todo todo = Todo.findById(id);

        if (todo == null) {
            throw new TodoNotFoundException(id);
        }
        return mapper.toResponse(todo);
    }

    @Transactional
    public TodoResponse updateById(Long id, TodoUpdateRequest updated) {
        Todo existing = Todo.findById(id);

        if (existing == null) {
            throw new TodoNotFoundException(id);
        }

        mapper.updateEntityFromDto(updated, existing);

        return mapper.toResponse(existing);

    }

    @Transactional
    public boolean delete(Long id) {
        Todo existing = Todo.findById(id);

        if (existing != null) {
            existing.delete();
            return true;
        }
        throw new TodoNotFoundException(id);
    }

}
