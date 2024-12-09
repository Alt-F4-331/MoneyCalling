import React from 'react';
import { useNavigate } from 'react-router-dom'; // Importă useNavigate din react-router-dom
import './WelcomePage.css';
import logo from '../assets/logo.png'; // Assuming you're in the src/components directory
import fundalImage from '../assets/preview.svg'; // Same for background image


const WelcomePage: React.FC = () => {
    const navigate = useNavigate(); // Creează o instanță a funcției de navigare

    // Inline style for background
    const containerStyle = {
        backgroundImage: `url(${fundalImage})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        height: '100vh',
    };

    return (
        <div className="container" style={containerStyle}>
            <div className="logo">
                <img src={logo} alt="Logo" style={{ maxWidth: '120%', maxHeight: '120%' }} />
            </div>
            <div className="text">
                <h1>Where financial goals become reality</h1>
                <p>Get personalized money management tips to help you take control of your finances! From budgeting and saving to investing and money management, our site offers practical advice to boost your financial health. Start making informed financial decisions and secure your financial future today!</p>
                <div className="buttons">
                    <button className="login-button" onClick={() => navigate('/login')}>Log In</button> {/* Modifică pentru a naviga la pagina de logare */}
                    <button className="signup-button" onClick={() => navigate('/register')}>Sign Up</button> {/* Modifică pentru a naviga la pagina de înregistrare */}
                </div>
            </div>
        </div>
    );
};

export default WelcomePage;
