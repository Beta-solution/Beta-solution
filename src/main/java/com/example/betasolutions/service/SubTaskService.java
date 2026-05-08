package com.example.betasolutions.service;

import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.repository.SubTaskRepository;
import org.springframework.stereotype.Service;

@Service
public class SubTaskService {
    private final SubTaskRepository subTaskRepository;

    public SubTaskService(SubTaskRepository subTaskRepository){
        this.subTaskRepository=subTaskRepository;
    }

    public void getAllSubTask(){

    }

    public void getSubTaskById(){

    }

    public void getSubTaskByTaskId(){

    }

    public void createSubTask(){

    }

    public void updateSubTask(int id, SubTask subtask){
        subTaskRepository.updateSubTask(id, subtask);
    }

    public void deleteSubTask(){

    }
}
