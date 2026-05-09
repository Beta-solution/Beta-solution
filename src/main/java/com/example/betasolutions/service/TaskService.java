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
    }

    public Task getTaskById(int id){
        return taskRepository.getTaskById(id);
    }

    public List<Task> getTaskByProjectId(int projectId){
        return taskRepository.getTaskByProjectId(projectId);
    }

    public void createTask(Task task, int projectId){
        taskRepository.createTask(task, projectId);
    }

    public boolean updateTask(int id, Task task){
        return taskRepository.updateTask(id, task);
    }

    public boolean deleteTask(int id){
        return taskRepository.deleteTask(id);
    }
}
