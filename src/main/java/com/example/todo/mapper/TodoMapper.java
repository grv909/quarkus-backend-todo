package com.example.todo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.todo.Todo;
import com.example.todo.dto.TodoCreateRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.dto.TodoUpdateRequest;

@Mapper(componentModel="cdi")
public interface TodoMapper {

    

    //Create:DTO->Entity
    Todo toEntity(TodoCreateRequest request);

    //update: DTO+ existing Entity -> update entity in place
    void updateEntityFromDto(TodoUpdateRequest request,@MappingTarget Todo entity);

    //Entity -> Response DTO
    TodoResponse toResponse(Todo todo);

    // List<Entity> -> List<Response DTO>
    List<TodoResponse> toResponseList(List<Todo> todos);

    
    
}
