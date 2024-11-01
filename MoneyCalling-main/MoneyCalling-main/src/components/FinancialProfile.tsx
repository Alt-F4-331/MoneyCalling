import React from 'react';
import { useNavigate } from 'react-router-dom';
import './FinancialProfile.css';

const FinancialProfile: React.FC = () => {
  const navigate = useNavigate();

  const handleBackClick = () => {
    navigate('/homepage');
  };

  return (
    <div className="financial-profile-container">
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
