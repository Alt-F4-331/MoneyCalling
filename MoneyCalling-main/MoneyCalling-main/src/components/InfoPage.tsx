import React from 'react';
import { useNavigate } from 'react-router-dom'; // Importă useNavigate din react-router-dom
import './InfoPage.css';
import logo from '/public/logo.png'; 

const InfoPage: React.FC = () => {
    const navigate = useNavigate(); // Creează o instanță a funcției de navigare
    const handleHome = () => {
        navigate("/homepage");
    }
    return (
        <div className="container">
            <button className="home-button" onClick={handleHome}>Home</button>
            <div className="logo">
                <img src={logo} alt="Logo" style={{ maxWidth: '120%', maxHeight: '120%' }} /> {/* Ajustează dimensiunea după necesități */}
            </div>
            <div className="text">
                <h1>Where financial goals become reality</h1>
                <p>Get personalized money management tips to help you take control of your finances! From budgeting and saving to investing and money management, our site offers practical advice to boost your financial health. Start making informed financial decisions and secure your financial future today!</p>
            </div>
        </div>
    );
};

export default InfoPage;
