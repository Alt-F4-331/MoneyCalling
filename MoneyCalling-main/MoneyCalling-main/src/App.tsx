import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login'; // Importă componenta de login
import Register from './components/Register';
import HomePage from './components/HomePage';
import FinancialProfile from './components/FinancialProfile';

const App: React.FC = () => {
  return (
    <div className='app-container'>
    <Router>
      <Routes>
        <Route path="/" element={<Login />} /> {/* Ruta principală pentru login */}
        <Route path="/register" element={<Register />} /> {/* Ruta pentru creare cont */}
        <Route path="/homepage" element={<HomePage />} /> { /*  Ruta pentru pagina principala */ }
        <Route path="/financial-profile" element={<FinancialProfile />} />
      </Routes>
    </Router>
    </div>
  );
};

export default App;
