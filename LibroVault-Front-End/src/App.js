// App.js
import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import AppRouter from './components/AppRouter';
import './App.css';

function App() {
  return (
    <Router>
      <div className="app-container">
        <AppRouter />
      </div>
    </Router>
  );
}

export default App;
