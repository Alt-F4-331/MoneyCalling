import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';
import logo from '/public/logo.png'; // Import logo

const Register: React.FC = () => {
  const [step, setStep] = useState(1);
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [birthDate, setBirthDate] = useState({ day: '', month: '', year: '' });
  const [sex, setSex] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const nextStep = () => setStep(step + 1);
  const prevStep = () => setStep(step - 1);
  
  const submitForm = () => {
    // Logic for account creation
    console.log({ firstName, lastName, birthDate, sex, email, phone, username, password });
    alert('Account created successfully!');
    navigate('/');
  };

  return (
    <div className="login-body"> {/* Use the same class for background */}
      <div className="login-container"> {/* Change to login-container */}
        <img src={logo} alt="Logo" className="logo" />
        <h2>Create Account</h2>
        {step === 1 && (
          <div className="login-form"> {/* Use login-form */}
            <input
              type="text"
              placeholder="First Name"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
            />
            <input
              type="text"
              placeholder="Last Name"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
            />
            <button className="next-button" onClick={nextStep}>Next</button>
            <button className="back-button" onClick={() => navigate('/')}>Cancel</button>
          </div>
        )}
        {step === 2 && (
          <div className="login-form"> {/* Use login-form */}
            <div className="date-inputs">
              <input
                type="text"
                placeholder="Day"
                value={birthDate.day}
                onChange={(e) => setBirthDate({ ...birthDate, day: e.target.value })}
              />
              <input
                type="text"
                placeholder="Month"
                value={birthDate.month}
                onChange={(e) => setBirthDate({ ...birthDate, month: e.target.value })}
              />
              <input
                type="text"
                placeholder="Year"
                value={birthDate.year}
                onChange={(e) => setBirthDate({ ...birthDate, year: e.target.value })}
              />
            </div>
            <select value={sex} onChange={(e) => setSex(e.target.value)}>
              <option value="">Sex</option>
              <option value="male">Male</option>
              <option value="female">Female</option>
            </select>
            <button className="next-button" onClick={nextStep}>Next</button>
            <button className="back-button" onClick={prevStep}>Back</button>
          </div>
        )}
        {step === 3 && (
          <div className="login-form"> {/* Use login-form */}
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <input
              type="tel"
              placeholder="Phone"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
            />
            <button className="next-button" onClick={nextStep}>Next</button>
            <button className="back-button" onClick={prevStep}>Back</button>
          </div>
        )}
        {step === 4 && (
          <div className="login-form"> {/* Use login-form */}
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
            <button className="next-button" onClick={submitForm}>Register</button>
            <button className="back-button" onClick={prevStep}>Back</button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Register;
