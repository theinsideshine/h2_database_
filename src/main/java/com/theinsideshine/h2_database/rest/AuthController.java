package com.theinsideshine.h2_database.rest;

import com.google.gson.JsonObject;
import com.theinsideshine.h2_database.model.entitys.Users;
import com.theinsideshine.h2_database.model.service.IUsersService;
import com.theinsideshine.h2_database.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




import static java.lang.System.out;


@RestController
@RequestMapping(value="api")
public class AuthController {

    @Autowired
    private IUsersService usersService;

    @Autowired
    private JWTUtil jwtUtil;


    @CrossOrigin(origins = "http://localhost:8081")
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
           return ( new ResponseEntity<String>(json.toString(), HttpStatus.OK));

       }



        json.addProperty("result", "FAIL");

        return  (new ResponseEntity<String>(json.toString(), HttpStatus.BAD_REQUEST));

    }

}

