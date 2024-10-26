import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';

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
    // Logica pentru crearea contului
    console.log({ firstName, lastName, birthDate, sex, email, phone, username, password });
    alert('Contul a fost creat cu succes!');
    navigate('/');
  };

  return (
    <div className="register-container">
      <h2>Creare Cont</h2>
      {step === 1 && (
        <div className="register-form">
          <input
            type="text"
            placeholder="Nume"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
          />
          <input
            type="text"
            placeholder="Prenume"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
          />
          <button className="next-button" onClick={nextStep}>Mai departe</button>
          <button className="back-button" onClick={() => navigate('/')}>Anulează</button>
        </div>
      )}
      {step === 2 && (
        <div className="register-form">
          <div className="date-inputs">
            <input
              type="text"
              placeholder="Zi"
              value={birthDate.day}
              onChange={(e) => setBirthDate({ ...birthDate, day: e.target.value })}
            />
            <input
              type="text"
              placeholder="Lună"
              value={birthDate.month}
              onChange={(e) => setBirthDate({ ...birthDate, month: e.target.value })}
            />
            <input
              type="text"
              placeholder="An"
              value={birthDate.year}
              onChange={(e) => setBirthDate({ ...birthDate, year: e.target.value })}
            />
          </div>
          <select value={sex} onChange={(e) => setSex(e.target.value)}>
            <option value="">Sex</option>
            <option value="masculin">Masculin</option>
            <option value="feminin">Feminin</option>
          </select>
          <button className="next-button" onClick={nextStep}>Mai departe</button>
          <button className="back-button" onClick={prevStep}>Înapoi</button>
        </div>
      )}
      {step === 3 && (
        <div className="register-form">
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <input
            type="tel"
            placeholder="Telefon"
            value={phone}
            onChange={(e) => setPhone(e.target.value)}
          />
          <button className="next-button" onClick={nextStep}>Mai departe</button>
          <button className="back-button" onClick={prevStep}>Înapoi</button>
        </div>
      )}
      {step === 4 && (
        <div className="register-form">
          <input
            type="text"
            placeholder="Nume utilizator"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Parola"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button className="next-button" onClick={submitForm}>Înregistrare</button>
          <button className="back-button" onClick={prevStep}>Înapoi</button>
        </div>
      )}
    </div>
  );
};

export default Register;