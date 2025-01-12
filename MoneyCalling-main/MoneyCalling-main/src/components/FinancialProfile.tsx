import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './FinancialProfile.css';
import { Link } from "react-router-dom";
import logo from  '../assets/logo.png';
import profile_pic from "../assets/profile_pic.jpg";
import { jwtDecode } from 'jwt-decode';
import axios from 'axios';

// Definim tipul pentru payload-ul decodat din token
interface DecodedToken {
  sub: string; // Sau userId, în funcție de structura token-ului
}

const FinancialProfile: React.FC = () => {
  const [financialData, setFinancialData] = useState({
    venit: 0,
    domiciliu: '',
    containerEconomii: 0,
    dataSalar: 0
  });
  const [isEditing, setIsEditing] = useState(false);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const [message, setMessage] = useState<string | null>(null);

  const handleBackClick = () => {
    navigate('/homepage');
  };

  const closeMessage = () => setMessage(null);

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
          setFinancialData({
            ...response.data.profil,
            venit: profil.venit || 0,
            domiciliu: profil.domiciliu || '-',
            containerEconomii: profil.containerEconomii || 0,
            dataSalar: profil.dataSalar || 0,
        });
          const savings = response.data.profil.containerEconomii;
          localStorage.setItem('savings', savings); // Stocăm savings în localStorage
          }
        } catch (error) {
          console.error('Error fetching user data:', error);
          setMessage('Failed to fetch user data');
        } finally {
          setLoading(false);  // Încheiem încărcarea
        }
      } else {
        // Dacă nu există token, redirectăm utilizatorul către pagina de login
        setMessage('Please log in first.');
        navigate('/login');
      }

  };

  const updateFinancialData = async () => {
    const token = localStorage.getItem('token');
    if (!token) return;

    const userId = getUserIdFromToken(token);
    if (!userId) return;

    try {
      const response = await axios.put(
        'http://localhost:8080/api/utilizatori/profil-financiar',
        financialData, // Trimitem noul profil
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        }
      );

      if (response.status === 200) {
        setMessage('Profile updated successfully!');
        setIsEditing(false); // Ieșim din modul de editare
      }
    } catch (error) {
      console.error('Error updating user data:', error);
      setMessage('Failed to update profile');
    }

  };

  // Folosim useEffect pentru a obține datele financiare la montarea componentei
  useEffect(() => {;
    fetchFinancialData();
  }, []);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFinancialData({ ...financialData, [name]: value });
  };

  return (
    <div className="financial-profile-container">
      {message && (
        <div className="overlay" onClick={closeMessage}>
          <div className="success-message">{message}</div>
        </div>
      )}
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
            <h3 className='subtitle'>Income:</h3>
            <div className="form-group">
              <input
                 type="number"
                 name="venit"
                 placeholder="Income:"
                 value={financialData.venit}
                 onChange={handleInputChange}
                 readOnly={!isEditing}
              />
            </div>
            <h3 className='subtitle'>Home Address:</h3>
            <div className="form-group">
              <input
                 type="text"
                 name="domiciliu"
                 placeholder="Home Address:"
                 value={financialData.domiciliu}
                 onChange={handleInputChange}
                 readOnly={!isEditing}
              />
            </div>
            <h3 className='subtitle'>Savings:</h3>
            <div className="form-group">
              <input
                type="number"
                name="containerEconomii"
                placeholder="Savings:"
                value={financialData.containerEconomii}
                onChange={handleInputChange}
                readOnly={!isEditing}
              />
            </div>
            <h3 className='subtitle'>Payment Date:</h3>
            <div className="form-group">
              <input
                type="number"
                name="dataSalar"
                placeholder="Payment date:"
                value={financialData.dataSalar}
                onChange={handleInputChange}
                readOnly={!isEditing}
              />
            </div>
            <div className="edit-button-container">
            {isEditing ? (
                <button className="edit-button" onClick={updateFinancialData}>Save</button>
              ) : (
                <button className="edit-button" onClick={() => setIsEditing(true)}>Edit</button>
              )}
          </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default FinancialProfile;
