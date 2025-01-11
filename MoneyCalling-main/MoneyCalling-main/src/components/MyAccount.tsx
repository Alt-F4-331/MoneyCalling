import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './MyAccount.css';
import { Link } from "react-router-dom";
import logo from  '../assets/logo.png';
import profile_pic from "../assets/profile_pic.jpg";
import axios from 'axios';
import { jwtDecode } from 'jwt-decode'; 

const MyAccount: React.FC = () => {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const [userData, setUserData] = useState<any>(null);  // State pentru datele utilizatorului
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);  // State pentru a arăta încărcarea

   // Funcție pentru a obține ID-ul utilizatorului din token
   const getUserIdFromToken = (token: string) => {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const decodedToken = jwtDecode(token);  // Decodăm token-ul
    return decodedToken.sub;  // Presupunem că ID-ul utilizatorului este în câmpul 'id' al token-ului
  };
  // Funcție pentru a obține datele utilizatorului
  const fetchUserData = async () => {
    const token = localStorage.getItem('token');  // Preluăm token-ul din localStorage

    if (token) {
      const userId = getUserIdFromToken(token);  // Obținem ID-ul utilizatorului din token
      try {
        const response = await axios.get(`http://localhost:8080/api/utilizatori/${userId}`, {
          headers: {
            Authorization: `Bearer ${token}`  // Transmitem token-ul în header
          }
        });

        if (response.status === 200) {
          setUserData(response.data);  // Setăm datele utilizatorului în state
          //console.log(userData);
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

  // Folosim useEffect pentru a obține datele utilizatorului când componenta este montată
  useEffect(() => {
    fetchUserData();
  }, []);

  const handleBackClick = () => {
    navigate('/homepage');
  };

  const handleSignOut = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  return (
    <div className="my-account-container">
      <header className="navbar">
        <Link to="/info-page">
          <img src={logo} alt="Logo" className="logo-image" />
        </Link>
        <nav className="nav-links">
          <Link to="/homepage">Home</Link>
          <Link to="/dashboard">History</Link>
          <Link to="/financial-profile">Financial Profile</Link>
          <Link to="/help">Help</Link>
          <Link to="/about-us">About Us</Link>
        </nav>
        <a href="/my-account" className="profile-link">
          <img src={profile_pic} alt="Profile" className="profile-image" />
        </a>
      </header>
      
      <div className="my-account-card">
      <button onClick={handleBackClick} className="back-button"></button>
        <h2 className="my-account-title">Profile</h2>

        {loading ? (
          <p>Loading...</p>  // Arătăm un mesaj de încărcare până când datele sunt obținute
        ) : userData ? (
          <div className="user-profile-info">
            <div className="form-row">
            <div className="form-group">
            <label>First Name:</label>
              <input
                type="text"
                placeholder="Name"
                value={userData.prenume}
                readOnly
              />
            </div>
            <div className="form-group last-name">
              <label>Last Name:</label>
              <input
                type="text"
                value={userData.nume}
                readOnly
              />
            </div>
            </div>
            <div className="form-group">
            <label className="other-labels">Email:</label>
              <input
                type="email"
                placeholder="Email"
                value={userData.email}
                readOnly
              />
            </div>
            <div className="form-group">
            <label className="other-labels">Phone Number:</label>
              <input
                type="tel"
                placeholder="Phone Number"
                value={userData.numarTelefon}
                readOnly
              />
            </div>
            <div className="form-group">
            <label className="other-labels">Password:</label>
              <input
                type="password"
                placeholder="Password"
                value={userData.parola}
                readOnly
              />
            </div>
            <div className="edit-button-container">
            <button className="edit-button edit">Edit</button>
            <button className="sign-out-button" onClick={handleSignOut}>Sign Out</button>
          </div>
          </div>
        ) : (
          <p>No user data found. Please log in.</p>  // Mesaj dacă nu există date pentru utilizator
        )}
      </div>
    </div>
  );
};

export default MyAccount;
