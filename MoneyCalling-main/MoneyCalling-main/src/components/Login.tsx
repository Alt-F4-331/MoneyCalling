import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';
import axios from 'axios';
import logo from '/public/logo.png'; // Import logo

const Login: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const [successMessage, setSuccessMessage] = useState<string | null>(null); // Mesaj de succes


  const handleLogin = async () => {
      try {
          const response = await axios.post('http://localhost:8080/api/utilizatori/login', {
            parola: password,  
            email: username
               
          });
          if (response.status === 200) {
              console.log('Login successful:', response.data);
              const token = response.data.token; // Verific? dac? r?spunsul de la backend con?ine un câmp 'token'
              if (token) {
                localStorage.setItem('token', token);  // Salveaz? token-ul în localStorage
              } else {
                console.error("Token-ul nu a fost g?sit în r?spunsul serverului.");
              }
              navigate('/homepage');
          }
      } catch (error) {
          console.error('Login failed:', error);
          setSuccessMessage('Invalid email or password');
      }

  };

  return (
    <div className="login-body">
      <div className="login-container">
        <img src={logo} alt="Logo" className="logo" />
        {successMessage && (
        <div className="overlay" onClick={() => setSuccessMessage(null)}>
          <div className="success-message">
            {successMessage}
          </div>
        </div>
      )}
        <h2>Log In</h2>
        <div className="login-form">
          <input
            type="text"
            placeholder="Email or Phone Number"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button className="login-button1" onClick={handleLogin}>Log In</button>
          <div className="button-container"> {/* New div for button alignment */}
            <button className="back-button" onClick={() => navigate('/')}>Back</button>
            <button className="signup-button" onClick={() => navigate('/register')}>Sign Up</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
