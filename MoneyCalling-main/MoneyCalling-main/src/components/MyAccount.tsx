/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './MyAccount.css';
import { Link } from "react-router-dom";
import logo from  '../assets/logo.png';
import profile_pic from "../assets/profile_pic.jpg";
import axios from 'axios';
import { jwtDecode } from 'jwt-decode'; 

const MyAccount: React.FC = () => {
  const [userData, setUserData] = useState<any>(null);  
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);  
  const [message, setMessage] = useState<string | null>(null);
  const [isEditing, setIsEditing] = useState(false);  // State pentru a controla dacă utilizatorul editează datele
  const [updatedData, setUpdatedData] = useState({
    nume: '',
    prenume: '',
    email: '',
    numarTelefon: '',
    parola: '',
  });  // State pentru datele actualizate ale utilizatorului

  const closeMessage = () => setMessage(null);

  const getUserIdFromToken = (token: string) => {
    const decodedToken = jwtDecode(token);  
    return decodedToken.sub;  
  };

  const fetchUserData = async () => {
    const token = localStorage.getItem('token');  
    if (token) {
      const userId = getUserIdFromToken(token);  
      try {
        const response = await axios.get(`http://localhost:8080/api/utilizatori/${userId}`, {
          headers: {
            Authorization: `Bearer ${token}`  
          }
        });
        if (response.status === 200) {
          setUserData(response.data);  
        }
      } catch (error) {
        console.error('Error fetching user data:', error);
        setMessage('Failed to fetch user data');
      } finally {
        setLoading(false);  
      }
    } else {
      setMessage('Please log in first.');
      navigate('/login');
    }
  };

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

  // Funcția de actualizare a contului utilizatorului
  const handleUpdate = async () => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const response = await axios.post('http://localhost:8080/api/utilizatori/updateAccount', updatedData, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.status === 201) {
          setUserData(response.data);  
          setIsEditing(false);  // După actualizare, revenim la vizualizarea datelor
          setMessage('Profile updated successfully.');
        }
      } catch (error) {
        console.error('Error updating user data:', error);
        setMessage('Failed to update profile');
      }
    }
  };

  const handleEditClick = () => {
    setIsEditing(true);
    setUpdatedData({
      nume: userData.nume,
      prenume: userData.prenume,
      email: userData.email,
      numarTelefon: userData.numarTelefon,
      parola: userData.parola,
    });
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setUpdatedData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  return (
    <div className="my-account-container">
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
          <p>Loading...</p>  
        ) : userData ? (
          <div className="user-profile-info">
            <div className="form-row">
              <div className="form-group">
                <label>First Name:</label>
                <input
                  type="text"
                  name="prenume"
                  value={isEditing ? updatedData.prenume : userData.prenume}
                  onChange={handleChange}
                  readOnly={!isEditing}
                />
              </div>
              <div className="form-group last-name">
                <label>Last Name:</label>
                <input
                  type="text"
                  name="nume"
                  value={isEditing ? updatedData.nume : userData.nume}
                  onChange={handleChange}
                  readOnly={!isEditing}
                />
              </div>
            </div>
            <div className="form-group">
              <label className="other-labels">Email:</label>
              <input
                type="email"
                name="email"
                value={isEditing ? updatedData.email : userData.email}
                onChange={handleChange}
                readOnly={!isEditing}
              />
            </div>
            <div className="form-group">
              <label className="other-labels">Phone Number:</label>
              <input
                type="tel"
                name="numarTelefon"
                value={isEditing ? updatedData.numarTelefon : userData.numarTelefon}
                onChange={handleChange}
                readOnly={!isEditing}
              />
            </div>
            <div className="form-group">
              <label className="other-labels">Password:</label>
              <input
                type="password"
                name="parola"
                value={isEditing ? updatedData.parola : userData.parola}
                onChange={handleChange}
                readOnly={!isEditing}
              />
            </div>
            <div className="edit-button-container">
              {isEditing ? (
                <button className="save-button" onClick={handleUpdate}>Save</button>
              ) : (
                <button className="edit-button edit" onClick={handleEditClick}>Edit</button>
              )}
              <button className="sign-out-button" onClick={handleSignOut}>Sign Out</button>
            </div>
          </div>
        ) : (
          <p>No user data found. Please log in.</p>  
        )}
      </div>
    </div>
  );
};

export default MyAccount;