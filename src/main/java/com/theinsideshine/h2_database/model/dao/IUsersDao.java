package com.theinsideshine.h2_database.model.dao;

import com.theinsideshine.h2_database.model.entitys.Users;
import org.springframework.data.repository.CrudRepository;

public interface IUsersDao extends CrudRepository<Users, Long> {

}