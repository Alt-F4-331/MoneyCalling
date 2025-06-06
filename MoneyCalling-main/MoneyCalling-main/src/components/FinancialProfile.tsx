import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './FinancialProfile.css';
import { Link } from "react-router-dom";
import logo from  '../assets/logo.png';
import profile_pic from "../assets/profile_pic.jpg";
import { jwtDecode } from 'jwt-decode';
import axios, { AxiosError } from 'axios';
 
// Definim tipul pentru payload-ul decodat din token
type DecodedToken = {
  sub: string;
};
 
const getUserIdFromToken = (token: string): string | null => {
  try {
    const decoded: DecodedToken = jwtDecode(token);
    return decoded.sub; // Returnăm câmpul 'sub' sau 'userId', în funcție de schema token-ului
  } catch (error) {
    console.error('Invalid token:', error);
    return null;
  }
};
 
export const fetchFinancialData = async (
  setFinancialData: Function,
  setIsProfileComplete: Function,
  setMessage: Function,
  setLoading: Function,
  navigate: Function,
  setSavings: Function, // Noul parametru pentru setarea savings
  setDataFetched: Function // Parametru pentru trigger
) => {
  const token = localStorage.getItem('token'); // Preluăm token-ul din localStorage
 
  if (token) {
    const userId = getUserIdFromToken(token); // Obținem userId din token
    try {
      const response = await axios.get(`http://localhost:8080/api/utilizatori/${userId}`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });
 
      if (response.status === 200) {
        const profil = response.data.profil;
        setFinancialData({
          ...response.data.profil,
          venit: profil.venit || 0,
          domiciliu: profil.domiciliu || '-',
          containerEconomii: profil.containerEconomii || 0,
          dataSalar: profil.dataSalar || 0,
        });
 
        const savings = response.data.profil.containerEconomii;
        localStorage.setItem('savings', savings); // Stocăm savings în localStorage
        setSavings(savings); // Setăm savings folosind noul parametru
 
        // Verificăm dacă profilul este complet
        const isComplete =
          profil.venit > 0 &&
          profil.domiciliu !== '' &&
          profil.containerEconomii > 0 &&
          profil.dataSalar > 0;
        setIsProfileComplete(isComplete);
        localStorage.setItem('isProfileComplete', JSON.stringify(isComplete));
 
        setDataFetched(true); // Setăm trigger-ul pentru actualizare
      }
    } catch (error) {
      console.error('Error fetching user data:', error);
      setMessage('Failed to fetch user data');
      setDataFetched(false); // Setăm trigger-ul pentru actualizare
    } finally {
      setLoading(false); // Încheiem încărcarea
    }
  } else {
    // Dacă nu există token, redirectăm utilizatorul către pagina de login
    setMessage('Please log in first.');
    navigate('/login');
    setDataFetched(false); // Setăm trigger-ul pentru actualizare
  }
};
 
const FinancialProfile: React.FC = () => {
  const [financialData, setFinancialData] = useState({
    venit: 0,
    domiciliu: '',
    containerEconomii: 0,
    dataSalar: 0
  });
 
  const [isProfileComplete, setIsProfileComplete] = useState(false); // Noua stare
  const [isEditing, setIsEditing] = useState(false);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const [message, setMessage] = useState<string | null>(null);
  const [savings, setSavings] = useState(0); // Noua stare pentru savings
  const [dataFetched, setDataFetched] = useState(false); // Trigger pentru actualizare
 
 
  const handleBackClick = () => {
    navigate('/homepage');
  };
 
  const closeMessage = () => setMessage(null);
 
  // Funcție pentru a obține userId din token
 
 
  const updateFinancialData = async () => {
    const token = localStorage.getItem('token');
    if (!token) return;
 
    const userId = getUserIdFromToken(token);
    if (!userId) return;
 
    try {
      const response = await axios.put(
        'http://localhost:8080/api/utilizatori/profil-financiar',
        financialData, // Trimitem noul profil
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        }
      );
 
      if (response.status === 200) {
        setMessage('Profile updated successfully!');
        setIsEditing(false); // Ieșim din modul de editare
        fetchFinancialData(
          setFinancialData,
          setIsProfileComplete,
          setMessage,
          setLoading,
          navigate,
          setSavings,
          setDataFetched);
      }
    } catch (error) {
      const axiosError = error as AxiosError;
  if (axiosError.response && axiosError.response.status === 400) {
    console.log('Validation Errors:', axiosError.response.data); // Adaugă acest log
    const validationErrors = axiosError.response.data as { [key: string]: string };
    const errorMessages = Object.values(validationErrors);
    setMessage(errorMessages.join(", "));
  } else {
    console.error('Error updating user data:', axiosError);
    setMessage('Failed to update profile');
  }
    }
  };
 
  // Folosim useEffect pentru a obține datele financiare la montarea componentei
  useEffect(() => {
    fetchFinancialData(
      setFinancialData,
      setIsProfileComplete,
      setMessage,
      setLoading,
      navigate,
      setSavings,
      setDataFetched
    );
  }, []);
 
 
 
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFinancialData({ ...financialData, [name]: value });
  };
 
  return (
    <div className="financial-profile-container">
      {message && (
        <div className="overlay" onClick={closeMessage}>
          <div className="success-message">{message}</div>
        </div>
      )}
      <header className="navbar">
        <Link to="/info-page">
          <img src={logo} alt="Logo" className="logo-image" />
        </Link>
        <nav className="nav-links">
          <Link to="/homepage">Home</Link>
          <Link to="/dashboard">History</Link>
          <Link to="/financial-profile" className="active">Financial Profile</Link>
          <Link to="/help">Help</Link>
          <Link to="/about-us">About Us</Link>
        </nav>
        <a href="/my-account" className="profile-link">
          <img src={profile_pic} alt="Profile" className="profile-image" />
        </a>
      </header>
      <div className="financial-profile-card">
        <button onClick={handleBackClick} className="back-button-fp">&lt; Back</button>
        <h2 className="financial-profile-title">Financial Profile</h2>
        {loading ? (
          <p>Loading...</p>
        ) : (
          <div className="financial-profile-info">
            <h3 className='subtitle'>Income:</h3>
            <div className="form-group">
              <input
                type="number"
                name="venit"
                placeholder="Income:"
                value={financialData.venit}
                onChange={handleInputChange}
                readOnly={!isEditing}
              />
            </div>
            <h3 className='subtitle'>Home Address:</h3>
            <div className="form-group">
              <input
                type="text"
                name="domiciliu"
                placeholder="Home Address:"
                value={financialData.domiciliu}
                onChange={handleInputChange}
                readOnly={!isEditing}
              />
            </div>
            <h3 className='subtitle'>Savings:</h3>
            <div className="form-group">
              <input
                type="number"
                name="containerEconomii"
                placeholder="Savings:"
                value={financialData.containerEconomii}
                onChange={handleInputChange}
                readOnly={!isEditing}
              />
            </div>
            <h3 className='subtitle'>Payment Date:</h3>
            <div className="form-group">
              <input
                type="number"
                name="dataSalar"
                placeholder="Payment date:"
                value={financialData.dataSalar}
                onChange={handleInputChange}
                readOnly={!isEditing}
              />
            </div>
            <div className="edit-button-container">
              {isEditing ? (
                <button className="edit-button" onClick={updateFinancialData}>Save</button>
              ) : (
                <button className="edit-button" onClick={() => setIsEditing(true)}>Edit</button>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
};
export default FinancialProfile;