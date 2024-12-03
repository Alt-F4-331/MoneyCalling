import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './MyAccount.css';
import { Link } from "react-router-dom";
import logo from "/public/logo.png";
import profile_pic from "../assets/profile_pic.jpg";

const MyAccount: React.FC = () => {
  const [nume, setNume] = useState('');
  const [email, setEmail] = useState('');
  const [telefon, setTelefon] = useState('');
  const [parola, setParola] = useState('');
  const navigate = useNavigate();

  const handleBackClick = () => {
    navigate('/homepage');
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const profilData = {
      nume,
      email,
      telefon,
      parola
    };

    console.log("Profile data submitted:", profilData);
    alert("Profile updated successfully!");
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
        <button onClick={handleBackClick} className="back-button">&lt; Back</button>
        <h2 className="my-account-title">Profile</h2>
        <form className="my-account-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <input
              type="text"
              placeholder="Name"
              value={nume}
              onChange={(e) => setNume(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <input
              type="tel"
              placeholder="Phone Number"
              value={telefon}
              onChange={(e) => setTelefon(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              placeholder="Password"
              value={parola}
              onChange={(e) => setParola(e.target.value)}
              required
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

export default MyAccount;
