package com.libroVault.personalLibrary.dto;

import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that represents a Data Transfer Object (DTO) for the Library. Used to
 * transfer data between the LibraryService and LibraryController layers.
 * It excludes DB annotations to NOT expose DB details.
 * 
 * Annotations:
 * Data - generates getters and setters for the attributes.
 * AllArgsConstructor - generates a constructor that takes all attributes into
 * the parameters.
 * NoArgsConstructor - generates a constructor that has no parameters.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryDTO {

    // Fields
    /** The ObjectId of the Library. */
    private ObjectId id;
    /** The custom ID for the Library. (Might not need this) */
    private String libraryId;
    /** The name of the Library. */
    private String name;
    /** The User associated with the Library. */
    private String user; // Updated to be a string
    /** The books associated with the Library. */
    private List<String> books; // updated to be a list of strings

}
