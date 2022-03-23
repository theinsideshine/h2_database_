package com.theinsideshine.h2_database.model.dao;

import com.theinsideshine.h2_database.model.entitys.Users;

public interface IUsersAuthDao {
    Users getUsersByCards(Users users);

    boolean isUserExist( Users users);

    boolean isIdDelete( Long id);
}
