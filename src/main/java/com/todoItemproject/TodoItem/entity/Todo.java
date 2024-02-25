package com.todoItemproject.TodoItem.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "todo")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    @Column(name = "title", nullable = false)
    private String title;
    private String description;
    private boolean complete;
}
