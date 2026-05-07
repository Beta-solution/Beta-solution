package com.example.betasolutions.service;

import com.example.betasolutions.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    public void getAllTask(){

    }

    public void getTaskById(){

    }

    public void getTaskByProjectId(){

    }

    public void createTask(){

    }

    public void updateTask(){

    }

    public void deleteTask(int id){
        taskRepository.deleteTask(id);
    } //abfa
}
