package com.todoItemproject.TodoItem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.todoItemproject.TodoItem.entity.Todo;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer>{

    @Query("SELECT t FROM Todo t WHERE t.title LIKE %?1%"
            + " OR t.description LIKE %?2%"
            + " OR t.complete = %?3%")
    List<Todo> searchTodo(String title, String description, boolean complete);
}
