import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login'; // Importă componenta de login
import Register from './components/Register';
import HomePage from './components/HomePage';
import WelcomePage from './components/WelcomePage'
import FinancialProfile from './components/FinancialProfile';
import AboutUs from './components/AboutUs';
import Help from './components/Help';
import Dashboard from './components/Dashboard';
import InfoPage from './components/InfoPage';
import MyAccount from './components/MyAccount';

const App: React.FC = () => {
  return (
    <div className='app-container'>
    <Router>
      <Routes>
        <Route path="/" element={<WelcomePage />} /> {/* Ruta pentru welcome page */}
        <Route path="/login" element={<Login />} /> {/* Ruta principală pentru login */}
        <Route path="/register" element={<Register />} /> {/* Ruta pentru creare cont */}
        <Route path="/homepage" element={<HomePage />} /> { /*  Ruta pentru pagina principala */ }
        <Route path="/financial-profile" element={<FinancialProfile />} />
        <Route path="/about-us" element={<AboutUs />} />
        <Route path="/help" element={<Help />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/info-page" element={<InfoPage />} />
        <Route path="/my-account" element={<MyAccount />} />
      </Routes>
    </Router>
    </div>
  );
};

export default App;
