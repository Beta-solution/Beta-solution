package com.example.betasolutions.service;

import com.example.betasolutions.model.Task;
import com.example.betasolutions.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    public List<Task> getAllTask(){
        return taskRepository.getAllTask();
    }//abfa

    public Task getTaskById(int id){
        return taskRepository.getTaskById(id);
    }//abfa

    public List<Task> getTaskByProjectId(int projectId){
        return taskRepository.getTaskByProjectId(projectId);
    }//abfa

    public void createTask(Task task, int projectId){
        taskRepository.createTask(task, projectId);
    } //abfa

    public void updateTask(){

    }

    public void deleteTask(int id){
        taskRepository.deleteTask(id);
    } //abfa
}
