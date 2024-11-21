import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';
import logo from '/public/logo.png'; // Import logo
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

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [error, setError] = useState<string | null>(null);


  const nextStep = () => setStep(step + 1);
  const prevStep = () => setStep(step - 1);

  const submitForm = async () => {
    // Pregătește datele pentru a se potrivi cu CreareContDto
    const contData = {
      nume: firstName,
      prenume: lastName,
      parola: password,
      email: email,
      dataNasterii: {
        zi: parseInt(birthDate.day, 10), // Convertiți ziua la int
        luna: parseInt(birthDate.month, 10), // Convertiți luna la int
        an: parseInt(birthDate.year, 10), // Convertiți anul la int
      },
      sex: sex,
      numarTelefon: phone,
    };

    console.log("Datele trimise:", contData);
    console.log(typeof contData.dataNasterii.an); // Tipul variabilei an (int)

    try {
      // Trimite cererea POST cu Axios
      const response = await axios.post('http://localhost:8080/api/utilizatori/createAccount', contData, {
        headers: {
          'Content-Type': 'application/json', // Specifică tipul de conținut
        },
      });

      if (response.status === 201) {
        console.log("Răspunsul API-ului:", response);
        setSuccessMessage('Cont creat cu succes!'); // Afișează mesajul de succes
      } else {
        // Dacă răspunsul nu este OK, afișează eroarea
        console.error("Eroare la crearea contului:", response.data);
        setError('Eroare la crearea contului. Încearcă din nou.');
      }
    } catch (err) {
      console.error("Eroare la comunicarea cu serverul:", err);
      setError('Eroare la comunicarea cu serverul. Încearcă din nou.');
    }
  };

  return (
    <div className="login-body"> {/* Use the same class for background */}
      <div className="login-container"> {/* Change to login-container */}
        <img src={logo} alt="Logo" className="logo" />
        {successMessage && (
        <div className="overlay" onClick={() => setSuccessMessage(null)}>
          <div className="success-message">
            {successMessage}
          </div>
        </div>
      )}
        {step === 1 && (
          <div className="login-form"> {/* Use login-form */}
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
            <button className="next-button" onClick={nextStep}>Next</button>
            <button className="back-button" onClick={() => navigate('/')}>Cancel</button>
          </div>
        )}
        {step === 2 && (
          <div className="login-form"> {/* Use login-form */}
            <h2>Date of Birth</h2>
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
            <div className="sex-option">
              <select
                value={sex}
                onChange={(e) => setSex(e.target.value)}
              >
                <option value="">
                  Select Gender
                </option>
                <option value="male">Male</option>
                <option value="female">Female</option>
              </select>
            </div>

            <button className="next-button" onClick={nextStep}>Next</button>
            <button className="back-button" onClick={prevStep}>Back</button>
          </div>
        )}
        {step === 3 && (
          <div className="login-form"> {/* Use login-form */}
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
            <button className="next-button" onClick={nextStep}>Next</button>
            <button className="back-button" onClick={prevStep}>Back</button>
          </div>
        )}
        {step === 4 && (
          <div className="login-form"> {/* Use login-form */}
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
            <button className="next-button" onClick={submitForm}>Register</button>
            <button className="back-button" onClick={prevStep}>Back</button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Register;
