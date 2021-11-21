package com.theinsideshine.h2_database.rest;

import com.google.gson.JsonObject;
import com.theinsideshine.h2_database.model.entitys.Task;
import com.theinsideshine.h2_database.model.entitys.Users;
import com.theinsideshine.h2_database.model.service.ITaskService;
import com.theinsideshine.h2_database.model.service.IUsersService;
import com.theinsideshine.h2_database.util.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value="api")
public class UsersController {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping(value="tasks")
    public List<Task> getTasks() {
        return taskService.getTask();
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value="users/list", method = RequestMethod.GET)
    public List<Users> getUsers( @RequestHeader(value="Authorization") String token) {

        if (!validarToken(token)) { return null; }

        return usersService.getUsers();
    }

    /* sin token
    @RequestMapping(value="users", method = RequestMethod.GET)
    public List<Users> getUsers() {
        return usersService.getUsers();
    }
    */

   private boolean validarToken(String token) {
       boolean ret_val = false;
        String userId = jwtUtil.getKey(token);
        if ( userId != null ){
            ret_val =  true;
        }

        return ret_val;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value="users/delete/{id}" , method = RequestMethod.DELETE )
    public ResponseEntity<String> deleteUsers(@RequestHeader(value="Authorization") String token, @PathVariable Long id ) {
        JsonObject json = new JsonObject();

        if (!validarToken(token)) { return null; }
        usersService.deleteUsers( id ) ;
        json.addProperty("result", "OK");
        return ( new ResponseEntity<>(json.toString(), HttpStatus.OK));

    }

    /*
     Actualiza un registro en al base de datos, termina usando .save idem create
     la diferencia que aca el id ya existe y es parte users
     */

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value="users/update" , method = RequestMethod.PUT )
    public ResponseEntity<String> updateUsers(@RequestHeader(value="Authorization") String token, @RequestBody Users users ) {
        JsonObject json = new JsonObject();

        if (!validarToken(token)) { return null; }
        usersService.saveUsers( users );
        json.addProperty("result", "OK");
        return ( new ResponseEntity<>(json.toString(), HttpStatus.OK));

    }


    /*
     Da de alta en la base de datos un usuario, encripta la password con argon2,
     el cliente envia en formato JSON url.localhost:8080/api/users
     Json
     {
     "email": "zoe.tavolaro@gmail.com",
      "password":"123456"
      }
       Recibe  : un Json.
       Devuelve: un String formateado como JSON.
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value="users/register" , method = RequestMethod.POST )
    public ResponseEntity<String> createUsers(@RequestBody Users users ) {
        //create Json Object
        JsonObject json = new JsonObject();

        // Se toma hora actual para el alta de id.
        Date date = new Date();
        users.setCreationDate(date);
        // Se guarda en Bd el password encriptado.

        //https://crypto.stackexchange.com/questions/72416/when-to-use-argon2i-vs-argon2d-vs-argon2id

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
        String hash = argon2.hash(1, 1024, 1, users.getPassword());
        users.setPassword(hash);

        usersService.createUsers( users ) ;
        System.out.println("Se creo el usuario "+users.getEmail());


        json.addProperty("email", users.getEmail());
        json.addProperty("result", "OK");
        return ( new ResponseEntity<>(json.toString(), HttpStatus.OK));


    }



}