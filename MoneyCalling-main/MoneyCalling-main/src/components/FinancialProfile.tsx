import React from 'react';
import { useNavigate } from 'react-router-dom';
import './FinancialProfile.css';
import { Link } from "react-router-dom";
import logo from "/public/logo.png";
import profile_pic from "../assets/profile_pic.jpg";

const FinancialProfile: React.FC = () => {
  const navigate = useNavigate();

  const handleBackClick = () => {
    navigate('/homepage');
  };

  return (
    <div className="financial-profile-container">
      <header className="navbar">
        <Link to="/info-page">
          <img src={logo} alt="Logo" className="logo-image" />
        </Link>
        <nav className="nav-links">
          <Link to="/homepage">Home</Link>
          <Link to="/dashboard">Dashboard</Link>
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
        <form className="financial-profile-form">
          <div className="form-group">
            <input type="text" placeholder="Income:" />
          </div>
          <div className="form-group">
            <input type="text" placeholder="Home Address:" />
          </div>
          <div className="form-group">
            <input type="text" placeholder="Savings:" />
          </div>
          <div className="form-group">
            <input type="text" placeholder="Payment date:" />
          </div>
          <div className="edit-button-container">
            <button type="button" onClick={handleBackClick} className="edit-button">Edit</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default FinancialProfile;
