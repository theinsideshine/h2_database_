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
        List<Users> lista = entityManager.createQuery(query)
                .setParameter("email", users.getEmail())
                .getResultList();

        // Null pointer exception.
        if (lista.isEmpty()) {
            return null;
        }

        String passwordHashed = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
        if (argon2.verify(passwordHashed, users.getPassword())) {
            return lista.get(0);
        }
        return null;
    }
}
