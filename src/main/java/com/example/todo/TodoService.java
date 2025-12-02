package com.example.todo;

import java.util.List;

import com.example.todo.dto.TodoCreateRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.dto.TodoUpdateRequest;
import com.example.todo.mapper.TodoMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TodoService {

    @Inject
    TodoMapper mapper;


public List<TodoResponse> getAll(){
    List<Todo> todos= Todo.listAll();
    return mapper.toResponseList(todos);
}

@Transactional
public TodoResponse createTodo(TodoCreateRequest request){
    Todo todo = mapper.toEntity(request);
    todo.id = null;
    todo.persist();
    return mapper.toResponse(todo);
}

public TodoResponse findOne(Long id){
   Todo todo = Todo.findById(id);

   return (todo==null)?null:mapper.toResponse(todo);
}


@Transactional
public TodoResponse updateById(Long id,TodoUpdateRequest updated){
    Todo existing = Todo.findById(id);

    if(existing==null){
        return null;
    }

    if(updated.title==null || updated.title.trim().isEmpty()){
        return null; // Handle the error in resource
    }

    mapper.updateEntityFromDto(updated, existing);

    return mapper.toResponse(existing);


}
@Transactional
public boolean delete(Long id){
    Todo existing = Todo.findById(id);

    if(existing!=null){
        existing.delete();
        return true;
    }

    return false;
}
    
}
