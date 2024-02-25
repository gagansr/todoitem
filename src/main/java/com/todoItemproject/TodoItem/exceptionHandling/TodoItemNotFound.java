package com.todoItemproject.TodoItem.exceptionHandling;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TodoItemNotFound extends RuntimeException{

    public TodoItemNotFound(String message){
        super(message);
    }
    
}
