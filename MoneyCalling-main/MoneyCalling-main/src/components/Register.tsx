/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';
import logo from '../assets/logo.png'; // Import logo
import axios from 'axios';

const Register: React.FC = () => {
  const [step, setStep] = useState(1);
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [birthDate, setBirthDate] = useState({ day: '', month: '', year: '' });
  const [sex, setSex] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  const [password, setPassword] = useState('');
  const [cpassword, setCPassword] = useState('');
  const [successMessage, setSuccessMessage] = useState<string | null>(null); // Mesaj de succes

  const navigate = useNavigate();

  const [error, setError] = useState<string | null>(null);

  const validateField = (field: string, value: string) => {
    switch (field) {
      case 'firstName':
      case 'lastName':
        if (!value) return 'Fields cannot be empty.';
        if (value.length > 50) return 'Fields cannot exceed 50 characters.';
        break;

      case 'password':
        if (!value) return 'Password cannot be empty.';
        if (value.length < 8) return 'Password must have at least 8 characters.';
        break;

      case 'email': {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!value) return 'Email cannot be empty.';
        if (!emailRegex.test(value)) return 'Please enter a valid email.';
        break;
      }

      case 'sex':
        if (!value) return 'Please select a gender.';
        break;

      case 'phone': {
        const phoneRegex = /^\d{10,15}$/;
        if (!value) return 'Phone number cannot be empty.';
        if (!phoneRegex.test(value)) return 'Phone number must have between 10 and 15 digits.';
        break;
      }

      default:
        return '';
    }
    return '';
  };

  const nextStep = () => {
    let errorMessage = '';
    if (step === 1) {
      errorMessage = validateField('firstName', firstName) || validateField('lastName', lastName);
    } else if (step === 2) {
      errorMessage =
        (!birthDate.day || !birthDate.month || !birthDate.year) && 'Please complete the birth date.' ||
        validateField('sex', sex);
    } else if (step === 3) {
      errorMessage = validateField('email', email) || validateField('phone', phone);
    } else if (step === 4) {
      if (!password || !cpassword || password !== cpassword) {
        errorMessage = 'Passwords do not match or are empty.';
      } else {
        errorMessage = validateField('password', password);
      }
    }

    if (errorMessage) {
      setSuccessMessage(errorMessage);
      return;
    }

    setSuccessMessage(null); // Clear error message
    setStep(step + 1);
  };

  const prevStep = () => setStep(step - 1);

  const submitForm = async () => {
    const contData = {
      nume: firstName,
      prenume: lastName,
      parola: password,
      email: email,
      dataNasterii: {
        zi: parseInt(birthDate.day, 10),
        luna: parseInt(birthDate.month, 10),
        an: parseInt(birthDate.year, 10),
      },
      sex: sex,
      numarTelefon: phone,
    };

    try {
      const response = await axios.post('http://localhost:8080/api/utilizatori/createAccount', contData, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.status === 201) {
        setSuccessMessage('Account successfully created!');
        navigate('/login');
      } else {
        setError('Failed to create account. Please try again.');
      }
    } catch (err) {
      setError('Server communication error. Please try again.');
    }
  };

  return (
    <div className="login-body">
      <div className="login-container">
        <img src={logo} alt="Logo" className="logo" />
        {successMessage && (
          <div className="overlay" onClick={() => setSuccessMessage(null)}>
            <div className="success-message">{successMessage}</div>
          </div>
        )}
        {step === 1 && (
          <div className="login-form">
            <h2>Create Account</h2>
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
            {error && <p className="error">{error}</p>}
            <button className="next-button" onClick={nextStep}>Next</button>
            <button className="back-button" onClick={() => navigate('/')}>Cancel</button>
          </div>
        )}
        {step === 2 && (
          <div className="login-form">
            <h2>Date of Birth</h2>
            <div className="date-inputs">
              <input
                type="text"
                placeholder="Day"
                value={birthDate.day}
                onChange={(e) => setBirthDate({ ...birthDate, day: e.target.value })}
              />
              <div className="sex-option">
                <select
                  value={birthDate.month}
                  onChange={(e) => setBirthDate({ ...birthDate, month: e.target.value })}
                >
                  <option value="" disabled>Select Month</option>
                  {Array.from({ length: 12 }, (_, index) => {
                    const month = (index + 1).toString().padStart(2, '0');
                    return (
                      <option key={month} value={month}>
                        {month}
                      </option>
                    );
                  })}
                </select>
              </div>
              <input
                type="text"
                placeholder="Year"
                value={birthDate.year}
                onChange={(e) => setBirthDate({ ...birthDate, year: e.target.value })}
              />
            </div>
            <div className="sex-option opt">
              <select value={sex} onChange={(e) => setSex(e.target.value)}>
                <option value="">Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
              </select>
            </div>
            {error && <p className="error">{error}</p>}
            <button className="next-button" onClick={nextStep}>Next</button>
            <button className="back-button" onClick={prevStep}>Back</button>
          </div>
        )}
        {step === 3 && (
          <div className="login-form">
            <h2>Contact Information</h2>
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <input
              type="tel"
              placeholder="Phone Number"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
            />
            {error && <p className="error">{error}</p>}
            <button className="next-button" onClick={nextStep}>Next</button>
            <button className="back-button" onClick={prevStep}>Back</button>
          </div>
        )}
        {step === 4 && (
          <div className="login-form">
            <h2>Create a Password</h2>
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <input
              type="password"
              placeholder="Confirm Password"
              value={cpassword}
              onChange={(e) => setCPassword(e.target.value)}
            />
            {error && <p className="error">{error}</p>}
            <button className="next-button" onClick={submitForm}>Register</button>
            <button className="back-button" onClick={prevStep}>Back</button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Register;
