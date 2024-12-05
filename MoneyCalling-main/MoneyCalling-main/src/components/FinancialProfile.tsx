import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './FinancialProfile.css';
import { Link } from "react-router-dom";
import logo from "/public/logo.png";
import profile_pic from "../assets/profile_pic.jpg";
import { jwtDecode } from 'jwt-decode';
import axios from 'axios';

// Definim tipul pentru payload-ul decodat din token
interface DecodedToken {
  sub: string; // Sau userId, în funcție de structura token-ului
}

const FinancialProfile: React.FC = () => {
  const [financialData, setFinancialData] = useState({
    venit: '',
    domiciliu: '',
    economii: '',
    dataSalar: ''
  });
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  const handleBackClick = () => {
    navigate('/homepage');
  };

  // Funcție pentru a obține userId din token
  const getUserIdFromToken = (token: string): string | null => {
    try {
      const decoded: DecodedToken = jwtDecode(token);
      return decoded.sub; // Returnăm câmpul 'sub' sau 'userId', în funcție de schema token-ului
    } catch (error) {
      console.error('Invalid token:', error);
      return null;
    }
  };
  // Funcție pentru a obține datele profilului financiar al utilizatorului logat
  const fetchFinancialData = async () => {
    const token = localStorage.getItem('token'); // Preluăm token-ul din localStorage

    if (token) {
      const userId = getUserIdFromToken(token); // Obținem userId din token
      try {
        const response = await axios.get(`http://localhost:8080/api/utilizatori/${userId}`, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });

        if (response.status === 200) {
          const profil = response.data.profil;
          console.log(profil);
          setFinancialData({
            ...response.data.profil,
            venit: profil.venit,
            domiciliu: profil.domiciliu,
            economii: profil.containerEconomii,
            dataSalar: profil.dataSalar,
        });
          console.log(response.data.profil.venit);
          }
        } catch (error) {
          console.error('Error fetching user data:', error);
          alert('Failed to fetch user data');
        } finally {
          setLoading(false);  // Încheiem încărcarea
        }
      } else {
        // Dacă nu există token, redirectăm utilizatorul către pagina de login
        alert('Please log in first.');
        navigate('/login');
      }

  };

  // Folosim useEffect pentru a obține datele financiare la montarea componentei
  useEffect(() => {;
    fetchFinancialData();
  }, []);

  return (
    <div className="financial-profile-container">
      <header className="navbar">
        <Link to="/info-page">
          <img src={logo} alt="Logo" className="logo-image" />
        </Link>
        <nav className="nav-links">
          <Link to="/homepage">Home</Link>
          <Link to="/dashboard">History</Link>
          <Link to="/financial-profile" className="active">Financial Profile</Link>
          <Link to="/help">Help</Link>
          <Link to="/about-us">About Us</Link>
        </nav>
        <a href="/my-account" className="profile-link">
          <img src={profile_pic} alt="Profile" className="profile-image" />
        </a>
      </header>
      <div className="financial-profile-card">
        <button onClick={handleBackClick} className="back-button-fp">&lt; Back</button>
        <h2 className="financial-profile-title">Financial Profile</h2>
        {loading ? (
          <p>Loading...</p>
        ) : (
          <div className="financial-profile-info">
            <div className="form-group">
              <input
                type="text"
                placeholder="Income:"
                value={financialData.venit}
                readOnly
              />
            </div>
            <div className="form-group">
              <input
                type="text"
                placeholder="Home Address:"
                value={financialData.domiciliu}
                readOnly
              />
            </div>
            <div className="form-group">
              <input
                type="text"
                placeholder="Savings:"
                value={financialData.economii}
                readOnly
              />
            </div>
            <div className="form-group">
              <input
                type="text"
                placeholder="Payment date:"
                value={financialData.dataSalar}
                readOnly
              />
            </div>
            <div className="edit-button-container">
            <button className="edit-button">Edit</button>
          </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default FinancialProfile;
