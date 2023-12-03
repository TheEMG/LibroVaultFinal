import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from './Home';
import MainApplication from './MainApplication';
import CreateAccount from './CreateAccount'; // Import the CreateAccount component

function AppRouter() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/main-application" element={<MainApplication />} />
      <Route path="/create-account" element={<CreateAccount />} /> {/* Include the route for CreateAccount */}
    </Routes>
  );
}

export default AppRouter;
