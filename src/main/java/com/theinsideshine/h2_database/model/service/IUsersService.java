package com.theinsideshine.h2_database.model.service;

import com.theinsideshine.h2_database.model.entitys.Users;

import java.util.List;
import java.util.Optional;

public interface IUsersService {


     List<Users> getUsers();

     void deleteUsers(Long id);

     void createUsers(Users users);

     Users getUsersByCards(Users users);

     void saveUsers(Users users);


     boolean isUsersExist( Users user);

     boolean isIdDelete( Long id);


}
