package com.example.betasolutions.service;

import com.example.betasolutions.model.Profile;
import com.example.betasolutions.model.SubTask;
import com.example.betasolutions.repository.SubTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubTaskService {
    private final SubTaskRepository subTaskRepository;

    public SubTaskService(SubTaskRepository subTaskRepository){
        this.subTaskRepository=subTaskRepository;
    }

    public List<SubTask> getAllSubTask(){
        return subTaskRepository.getAllSubTask();
    }

    public SubTask getSubTaskById(int id){
        return subTaskRepository.getSubTaskById(id);
    }

    public List<SubTask> getSubTaskByTaskId(int taskId){
        return subTaskRepository.getSubTaskByTaskId(taskId);
    }

    public boolean createSubTask(SubTask subTask, int taskId){
        return subTaskRepository.createSubTask(subTask, taskId);
    }

    public boolean updateSubTask(int id, SubTask subtask){
        return subTaskRepository.updateSubTask(id, subtask);
    }

    public boolean deleteSubTask(int id){
        return subTaskRepository.deleteSubTask(id);
    }

    public boolean addProfileToSubTask(int profileId, int subTaskId) {
        return subTaskRepository.addProfileToSubTask(profileId, subTaskId);
    }

    public boolean removeProfileFromSubTask(int profileId, int subTaskId) {
        return subTaskRepository.removeProfileFromSubTask(profileId, subTaskId);
    }

}
