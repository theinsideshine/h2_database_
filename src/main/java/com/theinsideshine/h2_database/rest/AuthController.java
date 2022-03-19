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

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Users users) {

        JsonObject json = new JsonObject();

        // put some value pairs into the JSON object .
        Users userlog = usersService.getUsersByCards(users);

        if (userlog != null) {
            String tokenJwt = jwtUtil.create(String.valueOf(userlog.getId()), userlog.getEmail());
            json.addProperty("token", tokenJwt);
            json.addProperty("name", userlog.getName());
            json.addProperty("result", "OK");
            json.addProperty("message", "Login Ok");
            LOGGER.log(Level.INFO, "LOGIN_OK Usuario:" + userlog.getName());
            return (new ResponseEntity<>(json.toString(), HttpStatus.OK));

        }

        json.addProperty("result", "FAIL");
        json.addProperty("message", "El usuario no pudo loguearse");
        if (userlog == null) {
            LOGGER.log(Level.INFO, "LOGIN_BAD Usuario: NN");
        } else {

            LOGGER.log(Level.INFO, "LOGIN_BAD Usuario:" + users.getName());

        }
        return (new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST));

    }
}




