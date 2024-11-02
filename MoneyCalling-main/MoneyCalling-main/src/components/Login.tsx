import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';
import logo from '/public/logo.png'; // Import logo

const Login: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = () => {
    // Logic for login
    console.log('Username:', username);
    console.log('Password:', password);

    navigate('/homepage'); // Redirect to homepage after login
  };

  return (
    <div className="login-body">
      <div className="login-container">
        <img src={logo} alt="Logo" className="logo" />
        <h2>Log In</h2>
        <div className="login-form">
          <input
            type="text"
            placeholder="Username"
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
