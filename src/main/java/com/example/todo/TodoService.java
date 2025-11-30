package com.example.todo;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TodoService {

private final List<Todo> todos= new ArrayList<>();
private Long counter = 1L;

public List<Todo> getAll(){
    return todos;
}

public Todo createTodo(Todo todo){
    todo.id = counter++;
    todos.add(todo);
    return todo;
}

public Todo findById(Long id){
    for(Todo t: todos){
        if(t.id.equals(id)){
            return t;
        }
    }
        return null;

}

public Todo updateById(Long id,Todo updated){
    Todo existing = findById(id);

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

public boolean delete(Long id){
    Todo existing = findById(id);

    if(existing!=null){
        todos.remove(existing);
        return true;
    }

    return false;
}
    
}
