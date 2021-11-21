package com.theinsideshine.h2_database.model.service;

import com.theinsideshine.h2_database.model.dao.ITaskDao;
import com.theinsideshine.h2_database.model.entitys.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private ITaskDao taskDao;

    /**
     * Method to get all the task
     * @return
     */
    public List<Task> getTask() {
        return (List<Task>) taskDao.findAll();
    }
}