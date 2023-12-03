package com.libroVault.personalLibrary.repository;

import java.util.List;
import java.util.Optional;

import com.libroVault.personalLibrary.model.Library;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Library. It is used to provide CRUD operations
 * for the Library.
 */
@Repository
public interface LibraryRepository extends MongoRepository<Library, ObjectId> {
    /**
     * Finds a Library by its name.
     * 
     * This method searches for a library based on its name. It returns an Optional,
     * which will be empty if no library
     * with the given name is found, or will contain the Library object if it
     * exists.
     * 
     * @param name The name of the Library to be searched for.
     * @return An Optional containing the Library if found, or an empty Optional if
     *         not found.
     */
    Optional<Library> findByName(String name);

    /**
     * Finds a Library by its unique library ID.
     * 
     * This method searches for a library based on its unique library ID. It returns
     * an Optional, which will be empty
     * if no library with the given ID is found, or will contain the Library object
     * if it exists.
     * 
     * @param libraryId The unique identifier of the Library to be searched for.
     * @return An Optional containing the Library if found, or an empty Optional if
     *         not found.
     */
    Optional<Library> findByLibraryId(String libraryId);

    /**
     * Deletes a Library by its unique library ID.
     * 
     * This method removes a library from the database based on its unique library
     * ID. It does not return any value.
     * 
     * @param libraryId The unique identifier of the Library to be deleted.
     */
    void deleteByLibraryId(String libraryId);

    /**
     * Finds all Libraries associated with a specific user ID.
     * 
     * This method retrieves a list of libraries that are associated with a given
     * user ID. The list may be empty if no
     * libraries are associated with the user.
     * 
     * @param Id The unique identifier of the User whose libraries are to be
     *           retrieved.
     * @return A List of Libraries associated with the specified user ID.
     */
    List<Library> findByUserId(String Id);
}
