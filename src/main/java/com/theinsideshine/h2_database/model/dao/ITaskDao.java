package com.theinsideshine.h2_database.model.dao;

import com.theinsideshine.h2_database.model.entitys.Task;
import org.springframework.data.repository.CrudRepository;

public interface ITaskDao extends CrudRepository<Task, Long> {

}
