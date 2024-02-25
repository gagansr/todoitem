package com.todoItemproject.TodoItem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.todoItemproject.TodoItem.entity.Todo;
import com.todoItemproject.TodoItem.exceptionHandling.TodoItemNotFound;
import com.todoItemproject.TodoItem.repository.TodoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private static final Logger log = LoggerFactory.getLogger(TodoService.class);


    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos(int pageNo, int size, String sortBy, String sortType){
        Pageable pageable = PageRequest.of(pageNo, size, sortType.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
        Page<Todo> pagedResult =  todoRepository.findAll(pageable);
        log.info("Paged result : {}",pagedResult);
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Todo> searchTodosWithOrWithOutFilter(String title, String description){
        log.info("Title is {} & Description is {}",title,description);
        return todoRepository.searchTodo(title,description);
    }
    public Todo getTodoById(int id){
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoItemNotFound("Todo Item not found with id : "+id));
    }

    public Todo createTodo(Todo todo){
        return todoRepository.save(todo);
    }

    public Todo updateTodo(int id, Todo todo){
        if(!todoRepository.existsById(id)){
            throw new TodoItemNotFound("Todo Item not found with id : "+id);
        }
        todo.setId(id);
        return todoRepository.save(todo);
    }

    public void deleteTodoById(int id){
        if(!todoRepository.existsById(id)){
            throw new TodoItemNotFound("Todo Item not found with id : "+id);
        }
        todoRepository.deleteById(id);
    } 
}
