import React, { createContext } from 'react';

const LibraryContext = createContext({
  libraries: [],
  setLibraries: () => {},
  selectedLibrary: null,
  setSelectedLibrary: () => {},
  userId: null, // Include userId in the context
  setUserId: () => {} // And a function to update it
});

export default LibraryContext;
