package com.theinsideshine.h2_database.rest;

import com.google.gson.JsonObject;
import com.theinsideshine.h2_database.model.entitys.Users;
import com.theinsideshine.h2_database.model.service.IUsersService;
import com.theinsideshine.h2_database.util.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import net.bytebuddy.dynamic.DynamicType;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value="api")
public class UsersController {

    private final static Logger LOGGER = Logger.getLogger("bitacora.rest.userscontroller");

    @Autowired
    private IUsersService usersService;

    @Autowired
    private JWTUtil jwtUtil;

    private boolean tokenValidate(String token) {
        boolean ret_val = false;
        String userId = jwtUtil.getKey(token);
        if ( userId != null ){
            ret_val =  true;
        }

        return ret_val;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="users/list", method = RequestMethod.GET)
    public List<Users> getUsers( @RequestHeader(value="Authorization") String token) {

        if (!tokenValidate(token)) {
            LOGGER.log(Level.INFO, "ERROR EN TOKEN");
            return null;
        }
        LOGGER.log(Level.INFO, "LIST USERS OK");
        return usersService.getUsers();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="users/delete/{id}" , method = RequestMethod.DELETE )
    public ResponseEntity<String> deleteUsers(@RequestHeader(value="Authorization") String token, @PathVariable Long id ) {
        JsonObject json = new JsonObject();

        if (!usersService.isIdDelete(id)){ // Evita el borrado de los id de prueba
            json.addProperty("result", "FAIL");
            json.addProperty("message", "El Id no pudo borrarse");
            LOGGER.log(Level.INFO, "EL ID NO PUDO BORRARSE");
            return ( new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST));

        }else {
            if (!tokenValidate(token)) {
                json.addProperty("result", "FAIL");
                json.addProperty("message", "Error en token");
                LOGGER.log(Level.INFO, "ERROR EN TOKEN");
                return ( new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST));
            }

            usersService.deleteUsers( id ) ;
            json.addProperty("result", "OK");
            json.addProperty("message", "El Id se borro");
            LOGGER.log(Level.INFO, "SE BORRO EL ID:"+id);
            return ( new ResponseEntity<>(json.toString(), HttpStatus.OK));
        }

    }

    /*
        Update a record in the database, finish using .save idem create
       the difference here is that the id already exists and is part of the users
     */

    @CrossOrigin(origins = "*")
    @RequestMapping(value="users/update" , method = RequestMethod.PUT )
    public ResponseEntity<String> updateUsers(@RequestHeader(value="Authorization") String token, @RequestBody Users users ) {
        JsonObject json = new JsonObject();

        if (!tokenValidate(token)) {
            json.addProperty("result", "FAIL");
            json.addProperty("message", "Error en token");
            LOGGER.log(Level.INFO, "UPDATE_BAD");
            return ( new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST));
        }
        usersService.saveUsers( users );
        json.addProperty("result", "OK");
        json.addProperty("message", "El Id se actualizo");
        LOGGER.log(Level.INFO, "UPDATE Usuario:"+ users.getName());
        return ( new ResponseEntity<>(json.toString(), HttpStatus.OK));

    }


    /*
     Register a user in the database, encrypt the password with argon2 if it is not in heroku
     Json
     {
     "email": "zoe.tavolaro@gmail.com",
      "password":"123456"
      }

     */

    @CrossOrigin(origins = "*")
    @RequestMapping(value="users/register" , method = RequestMethod.POST )
    public ResponseEntity<String> createUsers(@RequestBody Users users ) {
        // Create Json Object
        JsonObject json = new JsonObject();


        Date date = new Date();
        users.setCreationDate(date);
        // Se guarda en Bd el password encriptado.

        //https://crypto.stackexchange.com/questions/72416/when-to-use-argon2i-vs-argon2d-vs-argon2id
/*
        It was taken out to deploy on heroku, it crashed due to lack of memory

        The encrypted password is saved in Bd.
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
        String hash = argon2.hash(1, 1024, 1, users.getPassword());
        users.setPassword(hash);

*/
        // Does not allow duplicate emails.

        if ( usersService.isUsersExist( users)){
            System.out.println("El usuario existe: "+users.getEmail());
            json.addProperty("result", "FAIL");
            json.addProperty("message", "El usuario existe");
            LOGGER.log(Level.INFO, "REGISTER_BAD Usuario existe:"+ users.getEmail());
            return ( new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST));

        }else {
            usersService.createUsers(users);
            System.out.println("Se creo el usuario " + users.getEmail());
            json.addProperty("email", users.getEmail());
            json.addProperty("result", "OK");
            json.addProperty("message", "El Id se registro");
            LOGGER.log(Level.INFO, "REGISTER_OK Usuario:" + users.getEmail());
            return (new ResponseEntity<>(json.toString(), HttpStatus.OK));
        }

    }

    public void log_register_ok(Users users){

    }



}