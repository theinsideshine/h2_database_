package com.theinsideshine.h2_database.model.service;

import com.theinsideshine.h2_database.model.entitys.Users;

import java.util.List;

public interface IUsersService {


    public List<Users> getUsers();

    public void deleteUsers(Long id);

    public void createUsers(Users users);

    public Users getUsersByCards(Users users);

    public void saveUsers(Users users);
}
