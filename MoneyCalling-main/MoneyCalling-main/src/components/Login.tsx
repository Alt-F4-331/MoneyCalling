import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';

const Login: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = () => {
    // Logica pentru login
    console.log('Username:', username);
    console.log('Password:', password);

    navigate('/homepage');
  };

  return (
    <div className="login-body">
    <div className="login-container">
    <img src="/src/assets/logo.jpeg" alt="Logo" className="logo" />
      <h2>Autentificare</h2>
      <div className="login-form">
        <input
          type="text"
          placeholder="Nume utilizator"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Parolă"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button onClick={handleLogin}>Autentificare</button>
        <button className="register-button" onClick={() => navigate('/register')}>
          Creare cont
        </button>
      </div>
    </div>
    </div>
  );
};

export default Login;