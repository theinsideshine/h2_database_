package com.theinsideshine.h2_database.model.service;

import com.theinsideshine.h2_database.model.dao.IUsersAuthDao;
import com.theinsideshine.h2_database.model.dao.IUsersDao;
import com.theinsideshine.h2_database.model.entitys.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsersService implements IUsersService {

    @Autowired
    private IUsersDao usersDao;

    @Autowired
    private IUsersAuthDao usersAuthDao;


    @Override
    public List<Users> getUsers() {
        return (List<Users>) usersDao.findAll();
    }

    @Override
    public void deleteUsers(Long id) {
         usersDao.deleteById(id);
    }

    @Override
    public void createUsers(Users users) {
        usersDao.save(users);
    }

    @Override
    public Users  getUsersByCards(Users users) {
        return usersAuthDao.getUsersByCards(users);
    }

    @Override
    public void saveUsers(Users users) {usersDao.save(users);}


    @Override
    public boolean isUsersExist(Users user) {
        return usersAuthDao.isUserExist( user );
    }

    @Override
    public boolean isIdDelete(Long id) {
        return usersAuthDao.isIdDelete(id);
    }


}

