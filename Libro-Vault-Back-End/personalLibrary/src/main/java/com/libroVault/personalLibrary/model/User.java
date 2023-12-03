package com.libroVault.personalLibrary.model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

// this serves as the format of all User objects
@Document(collection = "user")
public class User 
{
    // fields for user login interface stuffs
    @Id
    private String id;
    private String username;
    private String password;

    /* 
    /**
     * Pass the input values into this constructor to send
     * into database.
     * @param id To be assigned per user as a unique value.
     * @param username 
     * @param password
     
    public User(String id, String username, String password)
    {
        setID(id);
        setUsername(username);
        setPassword(password);
    }
    */

    // getters and setters
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
