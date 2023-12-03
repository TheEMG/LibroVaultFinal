package com.libroVault.personalLibrary.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represents a Library the system. Each Library
 * has a unique id, a name, an associated user, and a list of books.
 * 
 * Annotations:
 * Document - references the collection within the MongoDB that will be used.
 * Data - generates getters and setters for the attributes.
 * AllArgsConstructor - generates a constructor that takes all attributes into
 * the parameters.
 * NoArgsConstructor - generates a constructor that has no parameters.
 * Id - marks the atribute that is the main identifier.
 * DBRef - used to reference other collections within the MongoDB.
 */
@Document(collection = "libraries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Library {

    // fields
    /** The main identifier used the the DB. */
    @Id
    private ObjectId id;
    /**
     * The user firendly identifier that is used to keep the ObjectId hidden and for
     * functionality.
     */
    private String libraryId;
    /** The name of the Library. */
    private String name;
    /** The User associated with the Library. */
    @DBRef
    private User user;
    /** The books associated with the Library. */
    @DBRef
    private List<Book> books = new ArrayList<>(); // Initialize with an empty list

}
