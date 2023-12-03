package com.libroVault.personalLibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libroVault.personalLibrary.dto.UserDTO;
import com.libroVault.personalLibrary.model.User;
import com.libroVault.personalLibrary.service.UserService;

@Controller
@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController 
{
    private final UserService userService;
  
    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) 
        {
            String username = userDTO.getUsername();
            // Check if a user with the same username already exists 
            if (userService.userExistsByUsername(username)) 
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with username " + username + " already exists.");
            }

            try 
            { 
                User createdUser = userService.createUser(userDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdUser); 
            }
            catch (IllegalArgumentException e)
                { 
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); 
                }
        }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable String userId) 
    {
    // Check if the user with the given ID exists
        if (!userService.userExistsById(userId)) 
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + userId + " not found.");
        }
    // If the user exists, proceed with user deletion
        try 
        {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } 
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping ("/validate/{username}/{password}")
    public ResponseEntity<?> validateUser(@PathVariable String username, @PathVariable String password)
    {
        // check if user exists
        if (!userService.userExistsByUsername(username))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + username + " not found.");
        }   

        // proceed with validation if user exists
        try
        {
            if (userService.validate(username, password))
            {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Login accepted.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Login failed.");
            }
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

   @GetMapping ("/getUserId/{username}")
   public ResponseEntity<?> getUserId(@PathVariable String username)
   {
        // check if user exists
        if (!userService.userExistsByUsername(username))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + username + " not found.");
        }

        // get ObjectId generated upon account creation using unique username
        try 
        {
            String userId = userService.getUserId(username);
            return ResponseEntity.status(HttpStatus.FOUND).body(userId);
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
   }

}