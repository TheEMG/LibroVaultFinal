import React, { useState, useContext, createContext, useEffect} from 'react';
import '../App.css';
import LibraryContext from './LibraryContext';
import { useLocation } from 'react-router-dom';
import './MainApplication.css';

//This is the App component, the main component
//uses UseStat eo create 2 state variables: libraries (list of libraries) and selectedLibrary(the currently selected library)

//libraryContext.Provider wraps the child components, so they can access the defined values of the values

function MainApplication() {
    const [libraries, setLibraries] = useState([]);
    const [selectedLibrary, setSelectedLibrary] = useState(null);
    const location = useLocation();
    const [userId, setUserId] = useState(null); // State for userId
    const [currentImageIndex, setCurrentImageIndex] = useState(0);

  //placeholder ads for the bottom of the page
  const footerImages = [
    '/images/googlead2.png',
    '/images/googlead1.png',
    '/images/googlead3.png',
    '/images/googlead5.png',
    '/images/googlead4.png',
    '/images/googlead2.png',
    '/images/googlead1.png',
    '/images/googlead3.png',
    '/images/googlead5.png',
    '/images/googlead4.png',
  ];
    
    useEffect(() => {
        console.log('location:', location);
        if (location.state?.userId) {
          console.log('userId:', location.state.userId); // Log userId
          setUserId(location.state.userId);
        }
      }, [location.state?.userId]);

      useEffect(() => {
    const timer = setInterval(() => {
      // Increment the index, and reset to 0 if it exceeds the array length
      setCurrentImageIndex((prevIndex) =>
        prevIndex === footerImages.length - 1 ? 0 : prevIndex + 1
      );
    }, 120000); // 120 seconds interval

    // Cleanup the timer on component unmount
    return () => clearInterval(timer);
  }, [footerImages.length]);
  
    return (
      <div> {/* This is the opening <div> tag */}
        <LibraryContext.Provider value={{ libraries, setLibraries, selectedLibrary, setSelectedLibrary, userId, setUserId }}>
          <header>LibroVault</header>
          <div className="content">
            <LibraryList />
            {selectedLibrary && <Books />}
          </div>
                    <div className="credits">
                    <p>Welcome to LibroVault!</p>
                    <p>Your go-to place for managing your book collection.</p>
                    <p>Need help? Contact us at support@librovault.com</p>
                    <p>App Version: 1.0 (Last Updated: November 30, 2023)</p>
                </div>

      {/* Footer section */}
      <div className="footer">
                {footerImages.map((image, index) => (
                  <img
                    key={index}
                    src={image}
                    alt={`Footer Image ${index + 1}`}
                    className={index === currentImageIndex ? 'visible' : 'hidden'}
                  />
                ))}
          </div>
        </LibraryContext.Provider>
      </div> 
    );
  }
  
  



function LibraryList() {
    const { libraries, setLibraries, selectedLibrary, setSelectedLibrary, userId } = useContext(LibraryContext);
  


    useEffect(() => {
        const fetchLibraries = async () => {
            if (!userId) {
                console.log("User ID not found");
                return; // Or handle the absence of userId as appropriate
            }
            try {
                const response = await fetch(`http://localhost:8080/api/libraries/user/${userId}`);
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const userLibraries = await response.json();
                setLibraries(userLibraries);
            } catch (error) {
                console.error('Error fetching libraries:', error);
            }
        };

        fetchLibraries();
    }, [userId, setLibraries]); // Dependency array ensures this runs when userId or setLibraries changes


    const addLibrary = async (name) => {
        

        // Check if the name is provided
        if (!name) {
            alert('Please enter a library name.');
            return;
        }

        // Check if the name is provided
        if (name.trim === '') {
             alert('Empty Strings are not allowed.');
            return;
        }

        if (!name.trim()) {
            alert('Name requires characters or numbers.');
            return;
        }
        

        // Check if the userId is provided
        if (!userId) {
            alert('User ID is missing.');
        }

        const libraryData = {
            name: name,
            user: userId,
        };
    
        try {
            const response = await fetch('http://localhost:8080/api/libraries', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(libraryData),
            });
    
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
    
            const newLibrary = await response.json();
            setLibraries([...libraries, newLibrary]);
        } catch (error) {
            console.error('Error adding library:', error);
        }
    };

    return (
        <div className="sidebar"> 
           {libraries.map((library) => (
        <button key={library.id} onClick={() => setSelectedLibrary(library)}> 
         {library.name}
       </button>
        ))}
            <button onClick={() => {
                const name = prompt("Enter library name");
                if (name) addLibrary(name);
            }}>
                + Add Library
            </button>
        </div>
    );

}


//This is the Books Component 
//This is the main area content that displays the information and details of each book in a library

//This component defines the following functions: changeLibraryName, deleteLibrary, addBook, deleteBook, editButton, editBook, resetFields

//This also has the algorithms to search books and sort them
function Books() {
    const { libraries, setLibraries, selectedLibrary, setSelectedLibrary } = useContext(LibraryContext);
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [translator, setTranslator] = useState('');
    const [publicationDate, setPublicationDate] = useState('');
    const [edition, setEdition] = useState('');
    const [volumeNumber, setVolumeNumber] = useState('');
    const [genre, setGenre] = useState('');
    const [subgenre, setSubgenre] = useState('');
    const [isbn, setIsbn] = useState('');
    const [searchTerm, setSearchTerm] = useState('');
    const [edit, setEdit] = useState(null);
    const [sortMethod, setSortMethod] = useState
        ('default');
    const [bookId, setBookId] = useState('');
  


  
    const changeLibraryName = (newName) => {
        console.log('Attempting to change library name to:', newName);
        const libraryId = selectedLibrary?.libraryId; // Optional chaining for safety
        console.log('Attempting to change library name for ID:', libraryId);
    
        if (!libraryId) {
            console.error('libraryId is missing');
            return;
        }


        if (!selectedLibrary.libraryId) {
            console.error('libraryId is missing');
            return;
        }
        if(!selectedLibrary){
            console.error('No Library selected');
            return;
        }
        // Check if the name is provided
        if (newName.trim === '') {
             alert('Empty Strings are not allowed.');
            return;
         }
         if (!newName.trim()) {
            alert('Please enter a valid library name.');
            return;
        }
        
        fetch(`http://localhost:8080/api/libraries/update/${libraryId}?newName=${encodeURIComponent(newName)}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            if (response.status === 204) {
                // Handle no content response here
                console.log('Library updated successfully, but no content returned.');
                // You might want to update the state optimistically here
                return null; // Return null to signal that no data is expected
            }
            return response.json();
        })

        .then(updatedLibrary => {
            const updatedLibraries = libraries.map(lib => 
                lib.libraryId === updatedLibrary.libraryId ? updatedLibrary : lib
            );
            setLibraries(updatedLibraries);
              // If the updated library is the currently selected one, also update the selectedLibrary state
    if (selectedLibrary && selectedLibrary.libraryId === updatedLibrary.libraryId) {
        setSelectedLibrary(updatedLibrary);
    }
        })
        .catch(error => {
            console.error('Error updating library name:', error);
        });
    };
    useEffect(() => {
        console.log('Libraries state updated:', libraries);
    }, [libraries]);


 
    const deleteLibrary = () => {
    // [Added] Safety check for selectedLibrary and its libraryId
    const libraryId = selectedLibrary?.libraryId; 

    // [Added] Check if there's a selected library to delete
    if (!libraryId) {
        console.error('No library selected or libraryId is missing');
        return;
    }
    
        fetch(`http://localhost:8080/api/libraries/${libraryId}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.status === 204) {
                // Update the local state to remove the deleted library
                const updatedLibraries = libraries.filter(lib => lib.libraryId !== libraryId);
                setLibraries(updatedLibraries);

                // [Added] Reset the selectedLibrary state if the deleted library was the selected one
            if (selectedLibrary && selectedLibrary.libraryId === libraryId) {
                setSelectedLibrary(null);
            }
            } else {
                console.error('Failed to delete library');
            }
        })
        .catch(error => {
            console.error('Error deleting library:', error);
        });
    };
    

 
    const addBook = (book) => {
        //Book Data to send to API 
        const bookData = {
            title,
            author,
            translator,
            publicationDate,
            edition,
            volumeNumber,
            genre,
            subgenre,
            isbn,
        };

        if (!book.title || !book.author || !book.genre) {
            alert('Please complete the Title, Author, and Genre fields to add the book.');
            return;
        }
    
        fetch('http://localhost:8080/api/books', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(bookData),
        })
        // .then((response) => response.json())
        .then((response) => {
            if (!response.ok) {
                // Handle HTTP errors
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then((addedBook) => {
            if (addedBook.id) {
                console.log('New book added successfully with ID:', addedBook.id);
                setBookId(addedBook.id);
        
                // [Added] Variable to hold the updated selected library
                let updatedSelectedLibrary = null;
        
                // Update the local state to include the new book in the selected library
                const updatedLibraries = libraries.map(lib => {
                    if (lib.libraryId === selectedLibrary.libraryId) {
                        // [Modified] Store the updated library in a variable
                        updatedSelectedLibrary = {
                            ...lib,
                            books: [...lib.books, addedBook],
                        };
                        return updatedSelectedLibrary;
                    }
                    return lib;
                });
        
                // [Modified] Update the selectedLibrary if it's the one being modified
                if (updatedSelectedLibrary) {
                    setSelectedLibrary(updatedSelectedLibrary);
                }
        
                // Set the updated libraries array to the state
                setLibraries(updatedLibraries);
        
    
                // Now add the book's DBRef to the library
                return fetch(`http://localhost:8080/api/libraries/${selectedLibrary.libraryId}/addBook/${addedBook.id}`, {
                    method: 'POST',
                });
            } else {
                console.error('Failed to add book');
                // Handle errors and provide user feedback for failed book addition.
            }
        })
        .then((libraryResponse) => {
            if (libraryResponse.ok) {
                console.log('Book added to library successfully');
                // Update UI or state as needed
            } else {
                console.error('Failed to add book to library');
                // Handle errors and provide user feedback for failed library addition.
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            // Handle network errors or other issues.
        });
    };
    
            
            
            
        

    const deleteBook = (bookToDelete) => {
        console.log(bookToDelete);
        const bookId = bookToDelete.id;

        if (!bookId) {
            console.error('bookId is missing or invalid');
            return; // Exit the function if bookId is not valid
        }
        // First, delete the book from the system
        fetch(`http://localhost:8080/api/books/remove/${bookId}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) {
                console.log('Book deleted successfully from the system');
    
                // Then, remove the book from the specific library
                fetch(`http://localhost:8080/api/libraries/${selectedLibrary.libraryId}/removeBook/${bookId}`, {
                    method: 'DELETE',
                })
                .then(libResponse => {
                    if (libResponse.ok) {
                        console.log('Book removed from the library successfully');

                        let updatedSelectedLibrary = null;
    
                        // Update the local state to reflect these changes
                        const updatedLibraries = libraries.map(lib => {
                            if (lib.libraryId === selectedLibrary.libraryId) {
                                updatedSelectedLibrary = {
                                    ...lib,
                                    books: lib.books.filter(book => book.id !== bookId),
                                };
                                return updatedSelectedLibrary;
                            }
                            return lib;
                        });
                        
                        // Update the selectedLibrary if it's the one being modified
                        if (updatedSelectedLibrary) {
                            setSelectedLibrary(updatedSelectedLibrary);
                        }
    
                        setLibraries(updatedLibraries);
                        setEdit(null);
    
                    } else {
                        console.error('Failed to remove book from the library');
                    }
                })
                .catch(error => {
                    console.error('Error removing book from library:', error);
                });
            } else {
                console.error('Failed to delete book from the system');
            }
        })
        .catch(error => {
            console.error('Error deleting book from system:', error);
        });
    };
    

    const editButton = (book) => {
        setTitle(book.title);
        setAuthor(book.author);
        setTranslator(book.translator);
        setPublicationDate(book.publicationDate);
        setEdition(book.edition);
        setVolumeNumber(book.volumeNumber);
        setGenre(book.genre);
        setSubgenre(book.subgenre);
        setIsbn(book.isbn);
        setEdit(book);
    };

    const editBook = (editedBook) => {
        if (!editedBook.title || !editedBook.author || !editedBook.genre) {
            alert('Please complete the Title, Author, and Genre fields to edit the book.');
            return;
        }

            // Ensure you have a valid bookId stored in the state variable
    if (bookId) {
        // Prepare the updated book data 
        const updatedBookData = {
          title: editedBook.title,
          author: editedBook.author,
          translator: editedBook.translator,
          publicationDate: editedBook.publicationDate,
          edition: editedBook.edition,
          volumeNumber: editedBook.volumeNumber,
          genre: editedBook.genre,
          subgenre: editedBook.subgenre,
          isbn: editedBook.isbn,
        };
    
        // Send a PUT request to update the book with the bookId
        fetch(`http://localhost:8080/api/books/${bookId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(updatedBookData),
        })
          .then((response) => {
            if (response.ok) {
              console.log('Book updated successfully');
               // Update the book in the library
               return fetch(`http://localhost:8080/api/libraries/books/${bookId}`, { // [Added] API request to update the book in the library
               method: 'PUT',
               headers: {
                 'Content-Type': 'application/json',
               },
               body: JSON.stringify(updatedBookData),
             });
            } else {
              console.error('Failed to update book');
              // Handle errors and provide user feedback for failed book update.
            }
          })
          .then((libraryResponse) => {
            if (libraryResponse.ok) {
                console.log('Book updated in library successfully');
                // Update UI or state as needed
            } else {
                console.error('Failed to update book in library');
                // Handle errors and provide user feedback for failed library update.
            }
        })
          .catch((error) => {
            console.error('Error:', error);
            // Handle network errors or other issues.
          });

        const updatedLibraries = libraries.map(lib => {
            if (lib === selectedLibrary) {
                lib.books = lib.books.map(book => {
                    if (book === edit) {
                        return editedBook;
                    }
                    return book;
                });
            }
            return lib;
        });
        setLibraries(updatedLibraries);
        resetFields();
    };
    }; //end of editBook

    const resetFields = () => {
        setTitle('');
        setAuthor('');
        setPublicationDate('');
        setGenre('');
        setTranslator('');
        setEdition('');
        setVolumeNumber('');
        setSubgenre('');
        setIsbn('');
        setEdit(null);
    };

    // let searchedBooks = selectedLibrary.books.filter(book => book.title.toLowerCase().includes(searchTerm.toLowerCase()));
    let searchedBooks = selectedLibrary.books
    .filter(book => book && book.title.toLowerCase().includes(searchTerm.toLowerCase()));//now it will display null Book lists

    if (sortMethod === 'alphabetical') {
        searchedBooks.sort((a, b) => a.title.localeCompare(b.title));
    } else if (sortMethod === 'byAuthor') {
        searchedBooks.sort((a, b) => a.author.localeCompare(b.author));
    } else if (sortMethod === 'byGenre') {
        searchedBooks.sort((a, b) => a.genre.localeCompare(b.genre));
    }

    return (
        <div className="books">
            <h2>{selectedLibrary.name}</h2>
            <button onClick={() => {
                const newName = prompt("Edit library name");
                if (newName) changeLibraryName(newName);
            }}>
                Edit Library Name
            </button>
            <button onClick={deleteLibrary}>Delete Library</button>
            <input type="text" placeholder="Search for a book..." onChange={e => setSearchTerm(e.target.value)} />

            <select value={sortMethod} onChange={(e) => setSortMethod(e.target.value)}>
                <option value="default">Default Order</option>
                <option value="alphabetical">Sort Alphabetically By Title</option>
                <option value="byAuthor">Sort Alphabetically By Author</option>
                <option value="byGenre">Group By Genre</option>
            </select>

            {searchedBooks.map((book, index) => (
                <div key={index}>
                    <h4>{book.title}</h4>
                    <p>{book.author}</p>
                    <p>{book.translator}</p>
                    <p>{book.publicationDate}</p>
                    <p>{book.edition}</p>
                    <p>{book.volumeNumber}</p>
                    <p>{book.genre}</p>
                    <p>{book.subgenre}</p>
                    <p>{book.isbn}</p>
                    <button onClick={() => deleteBook(book)}>Delete book</button>
                    <button onClick={() => editButton(book)}>Edit book</button>
                </div>
            ))}

            <div>
                <input type="text" placeholder="Book Title" value={title} onChange={e => setTitle(e.target.value)} />
                <input type="text" placeholder="Author" value={author} onChange={e => setAuthor(e.target.value)} />
                <input type="text" placeholder="Translator" value={translator} onChange={e => setTranslator(e.target.value)} />
                <input type="text" placeholder="Publication Date" value={publicationDate} onChange={e => setPublicationDate(e.target.value)} />
                <input type="text" placeholder="Edition" value={edition} onChange={e => setEdition(e.target.value)} />
                <input type="text" placeholder="Volume Number" value={volumeNumber} onChange={e => setVolumeNumber(e.target.value)} />
                <input type="text" placeholder="Genre" value={genre} onChange={e => setGenre(e.target.value)} />
                <input type="text" placeholder="Sub-genre" value={subgenre} onChange={e => setSubgenre(e.target.value)} />
                <input type="text" placeholder="ISBN" value={isbn} onChange={e => setIsbn(e.target.value)} />

                {edit ? (
                    <button onClick={() => editBook({ title, author, translator, publicationDate, edition, volumeNumber, genre, subgenre, isbn })}>
                        Update Book
                    </button>
                ) : (
                    <button onClick={() => addBook({ title, author, translator, publicationDate, edition, volumeNumber, genre, subgenre, isbn })}>
                        Add Book
                    </button>
                )}
            </div>
        </div>
    );
}

export default MainApplication;
