package com.theinsideshine.h2_database.model.dao;

import com.theinsideshine.h2_database.model.entitys.Users;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UsersAuthDaoImpl implements IUsersAuthDao {


    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Users getUsersByCards(Users users) {

        String query = "FROM Users WHERE email = :email";
        List<Users> list= entityManager.createQuery(query)
                .setParameter("email", users.getEmail())
                .getResultList();

        // Null pointer exception.
        if (list.isEmpty()) {
            return null;
        }

        String passwordHashed = list.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
        if (argon2.verify(passwordHashed, users.getPassword())) {
            return list.get(0);
        }else if (passwordHashed.equals(users.getPassword())){ //Support without argon
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean isUserExist(Users users) {

        String query = "FROM Users WHERE email = :email";
        List<Users> list= entityManager.createQuery(query)
                .setParameter("email", users.getEmail())
                .getResultList();

        // Null pointer exception.
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isIdDelete(Long id) {
        String query = "FROM Users WHERE id = :id";
        List<Users> list= entityManager.createQuery(query)
                .setParameter("id", id)
                .getResultList();

        // Null pointer exception.
        if (list.isEmpty()) {
            return false;
        }
        if (list.get(0).getEmail().equals("pablo.tavolaro@gmail.com") ||
                list.get(0).getEmail().equals("test@gmail.com")
        ){
            return false;
        }
        return true;
    }
}
