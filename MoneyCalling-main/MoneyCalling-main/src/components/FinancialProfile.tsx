import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './FinancialProfile.css';
import { Link } from "react-router-dom";
import logo from "/public/logo.png";
import profile_pic from "../assets/profile_pic.jpg";

const FinancialProfile: React.FC = () => {
  const [venit, setVenit] = useState('');
  const [domiciliu, setDomiciliu] = useState('');
  const [economii, setEconomii] = useState('');
  const [dataSalar, setDataSalar] = useState('');
  const navigate = useNavigate();

  const handleBackClick = () => {
    navigate('/homepage');
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Obtinem token-ul din localStorage
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("Token-ul nu este disponibil");
      return;
    }

    const profilData = {
      venit: venit,
      domiciliu: domiciliu,
      containerEconomii: economii,
      dataSalar: dataSalar
    };

    // Trimitem datele catre backend
    try {
      const response = await fetch('http://localhost:8080/api/utilizatori/profil-financiar', {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(profilData)
      });

      if (response.ok) {
        alert("Profilul financiar a fost actualizat cu succes!");
        navigate('/dashboard');
      } else {
        alert("A aparut o eroare la actualizarea profilului.");
      }
    } catch (error) {
      console.error("Eroare la trimiterea cererii:", error);
      alert("Eroare la actualizarea profilului.");
    }
  };

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
        <form className="financial-profile-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <input
              type="text"
              placeholder="Income:"
              value={venit}
              onChange={(e) => setVenit(e.target.value)}
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              placeholder="Home Address:"
              value={domiciliu}
              onChange={(e) => setDomiciliu(e.target.value)}
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              placeholder="Savings:"
              value={economii}
              onChange={(e) => setEconomii(e.target.value)}
            />
          </div>
          <div className="form-group">
            <input
              type="text"
              placeholder="Payment date:"
              value={dataSalar}
              onChange={(e) => setDataSalar(e.target.value)}
            />
          </div>
          <div className="edit-button-container">
            <button className="edit-button">Edit</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default FinancialProfile;
