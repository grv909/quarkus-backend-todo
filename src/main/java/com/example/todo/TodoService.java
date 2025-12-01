package com.example.todo;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TodoService {


public List<Todo> getAll(){
    return Todo.listAll();
}

@Transactional
public Todo createTodo(Todo todo){
    todo.id = null;
    todo.persist();
    return todo;
}

public Todo findById(Long id){
   return Todo.findById(id);
}


@Transactional
public Todo updateById(Long id,Todo updated){
    Todo existing = Todo.findById(id);

    if(existing==null){
        return null;
    }

    if(updated.title==null || updated.title.trim().isEmpty()){
        return null; // Handle the error in resource
    }

    existing.title = updated.title;
    existing.done = updated.done;

    return existing;


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
