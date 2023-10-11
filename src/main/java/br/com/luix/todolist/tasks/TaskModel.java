package br.com.luix.todolist.tasks;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(length = 50)
    private String title;
    private String description;
    private LocalDateTime start_At;
    private LocalDateTime end_At;
    private String priority;
    @CreationTimestamp
    private LocalDateTime created_At;
    private UUID user_id;

}
