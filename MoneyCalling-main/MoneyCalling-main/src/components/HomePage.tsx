import React from 'react';
import './HomePage.css';
import logo from "/public/logo.png";
import profile_pic from "../assets/profile_pic.jpg";
import PieChart from './PieChart';
import { Link } from 'react-router-dom';

const HomePage: React.FC = () => {
  return (
    <div className='home-page'>
      {/* Bara de navigare */}
      <header className='navbar'>
        <div className='logo'>
            <img src={logo} alt="Logo" className='logo-image' />
        </div>
        <nav className='nav-links'>
        <Link to='/'>Home</Link> {/* Link către welcome page */}
        <Link to='/dashboard'>Dashboard</Link>
        <Link to='/financial-profile'>Financial Profile</Link>
        <Link to='/help'>Help</Link>
        <Link to='/about-us'>About Us</Link>
        </nav>
        <a href='/my-account' className='profile-link'>
            <img src={profile_pic} alt="Profile" className="profile-image" />
        </a>
      </header>

      {/* Meniu lateral */}
      <aside className='sidebar'>
        <button>Generare Raport chirie</button>
        <button>Generare Raport leasing/rata</button>
        <button>Generare Raport investitii</button>
        <button>Generare Raport cheltuieli personale</button>
        <button>Generare Raport transport</button>
        <button>Generare Raport divertisment</button>
        <button className='add-button'>+</button>
        <div className='savings'>
          <span>Savings:</span>
          <span>44448282</span>
        </div>
      </aside>

      {/* Conținut principal */}
      <main className='main-content'>
        <div className='diagram'>
          <PieChart />
        </div>
      </main>
    </div>
  );
};

export default HomePage;
