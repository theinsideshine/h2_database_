package com.theinsideshine.h2_database.rest;

import com.google.gson.JsonObject;
import com.theinsideshine.h2_database.model.entitys.Users;
import com.theinsideshine.h2_database.model.service.IUsersService;
import com.theinsideshine.h2_database.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.out;


@RestController
@RequestMapping(value="api")
public class AuthController {

    private final static Logger LOGGER = Logger.getLogger("bitacora.rest.authcontroller");

    @Autowired
    private IUsersService usersService;

    @Autowired
    private JWTUtil jwtUtil;


    //  @CrossOrigin(origins = "http://localhost:8081")
    @CrossOrigin(origins = "*")
    @RequestMapping(value="login" , method = RequestMethod.POST )
    public ResponseEntity<String> login(@RequestBody Users users ) {
        //create Json Object
        JsonObject json = new JsonObject();

        // put some value pairs into the JSON object .
        Users userlog = usersService.getUsersByCards( users );

       if (userlog != null ){
           String tokenJwt = jwtUtil.create(String.valueOf(userlog.getId()), userlog.getEmail());
           json.addProperty("token", tokenJwt);
           json.addProperty("name", userlog.getName());
           json.addProperty("result", "OK");
           log_login_ok(userlog);
           return ( new ResponseEntity<>(json.toString(), HttpStatus.OK));

       }



        json.addProperty("result", "FAIL");
        log_login_fail(userlog);
        return  (new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST));

    }


    public void log_login_ok(Users users){
        LOGGER.log(Level.INFO, "LOGIN_OK Usuario:"+ users.getName());
    }

    public void log_login_fail(Users users){
        if (users==null){
            LOGGER.log(Level.INFO, "LOGIN_BAD Usuario: NN");
        }else {
            LOGGER.log(Level.INFO, "LOGIN_BAD Usuario:"+ users.getName());

        }
    }
}

