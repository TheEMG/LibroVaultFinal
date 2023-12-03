package com.libroVault.personalLibrary.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libroVault.personalLibrary.dto.UserDTO;
import com.libroVault.personalLibrary.model.User;
import com.libroVault.personalLibrary.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDTO userDTO) {
        // can modify later
        if (userDTO.getUsername() == null || userDTO.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        // create new User object
        User newUser = new User();

        // set required User attributes
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());

        // save into MongoDB repo
        return userRepository.save(newUser);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
    
    public boolean validate(String username, String password) 
    {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Check if the provided password matches the stored password
            return password.equals(user.getPassword());
        }

        // If user is not found or password doesn't match
        return false;
    }
    
    public boolean userExistsByUsername(String username)
    {
        return userRepository.existsByUsername(username); // check later
    }

    public boolean userExistsById(String userId) {
        return userRepository.existsById(userId);
    }

    public void getUserById(String userId) {
        userRepository.findById(userId); // check if it works
    }

    // get userId given username
    public String getUserId(String username)
    {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent())
        {
            User user = userOpt.get();
            return user.getId();
        }
        else
        {
            return null;
        }
    }

    public void getAllUsers()
    {
        userRepository.findAll(); // also check if works
    }
    
    /**
     * Mehtod that retrieves the User's ID/
     * By: Jose Barrera
     * 
     * @param id The id to retrieve
     * @return The User's id.
     */
    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }
}
