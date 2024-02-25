package com.todoItemproject.TodoItem.exceptionHandling;

public class TodoItemCustomException extends RuntimeException{

    public TodoItemCustomException(String message){
        super(message);
    }

}