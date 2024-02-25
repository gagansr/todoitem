package com.todoItemproject.TodoItem.controller;

//import jakarta.validation.Valid;
import com.todoItemproject.TodoItem.entity.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.todoItemproject.TodoItem.entity.Todo;
import com.todoItemproject.TodoItem.service.TodoService;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    
    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<Todo> getAllTodos(@RequestParam(defaultValue = "0") int pageNumber,
                                                  @RequestParam(defaultValue = "5") int pageSize,
                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                  @RequestParam(defaultValue = "asc") String sortType){
        return todoService.getAllTodos(pageNumber, pageSize, sortBy, sortType);
    }


    @GetMapping("/search")
    public List<Todo> searchTodosWithOrWithoutFilter(@RequestParam(defaultValue = "0") int pageNumber,
                                                     @RequestParam(defaultValue = "5") int pageSize,
                                                     @RequestParam(required = false) String title,
                                                     @RequestParam(required = false) String description,
                                                     @RequestParam(required = false) boolean complete){
        List<Todo> searchedList = todoService.searchTodosWithOrWithOutFilter(title,description,complete);
        int start = pageNumber * pageSize;
        int end = Math.min(start + pageSize,searchedList.size());

        return searchedList.subList(start,end);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable int id){
        return new ResponseEntity<Todo>(todoService.getTodoById(id), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo){
        return new ResponseEntity<Todo>(todoService.createTodo(todo), HttpStatus.CREATED);
    }

    @PostMapping("/add-multiple")
    public CustomResponse createMultipleTodo(@Valid @RequestBody List<Todo> todo){
        todo.forEach(td -> todoService.createTodo(td));
        return new CustomResponse(HttpStatus.CREATED, "Successfully Added!", Instant.now());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable int id, @Valid @RequestBody Todo todo){
        return new ResponseEntity<Todo>(todoService.updateTodo(id,todo), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public CustomResponse deleteTodoById(@PathVariable int id){
        todoService.deleteTodoById(id);
        return new CustomResponse(HttpStatus.OK, "Todo Item has been Deleted Successfully!", Instant.now());
    }
}
