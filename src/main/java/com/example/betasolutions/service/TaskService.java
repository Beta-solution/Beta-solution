package com.example.betasolutions.service;

import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.model.Task;
import com.example.betasolutions.repository.SubTaskRepository;
import com.example.betasolutions.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final SubTaskRepository subTaskRepository;
    private final SubTaskService subTaskService;
    private final CalculationService calculationService;

    public TaskService(TaskRepository taskRepository, SubTaskRepository subTaskRepository,
                       SubTaskService subTaskService, CalculationService calculationService){
        this.taskRepository=taskRepository;
        this.subTaskRepository = subTaskRepository;
        this.subTaskService = subTaskService;
        this.calculationService = calculationService;
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

    public List<Task> getTasksWithDuration(int projectId) {
        List<Task> tasks = getTaskByProjectId(projectId);

        for(Task ts : tasks) {
            BigDecimal totalDuration = getTaskDuration(ts.getId());
            ts.setTotalDuration(totalDuration);
        }
        return tasks;
    }

    public BigDecimal getTaskDuration(int taskId) {
            List<SubTask> subTasks = subTaskService.getSubTaskByTaskId(taskId);
            return calculationService.calculateTaskDuration(subTasks);
    }
}
