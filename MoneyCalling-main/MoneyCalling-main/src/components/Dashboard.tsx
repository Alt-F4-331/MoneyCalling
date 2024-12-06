import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Dashboard.css";
import logo from  '../assets/logo.png';
import profile_pic from "../assets/profile_pic.jpg";

const Dashboard: React.FC = () => {
    const currentYear = new Date().getFullYear(); // Anul actual
    const [selectedYear, setSelectedYear] = useState<number>(currentYear);

    const handleYearChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedYear(Number(event.target.value));
    };

    return (
        <div className="dashboard-page">
            {/* Bara de navigare */}
            <header className="navbar">
                <Link to="/info-page">
                    <img src={logo} alt="Logo" className="logo-image" />
                </Link>
                <nav className="nav-links">
                    <Link to="/homepage">Home</Link>
                    <Link to="/dashboard" className="active">History</Link>
                    <Link to="/financial-profile">Financial Profile</Link>
                    <Link to="/help">Help</Link>
                    <Link to="/about-us">About Us</Link>
                </nav>
                <a href="/my-account" className="profile-link">
                    <img src={profile_pic} alt="Profile" className="profile-image" />
                </a>
            </header>

            {/* Secțiunea principală */}
            <main className="main-content">
                <div className="calendar-section">
                    {/* Selectare An */}
                    <div className="year-selector">
                        <select value={selectedYear} onChange={handleYearChange}>
                            {Array.from({ length: currentYear - 1920 + 1 }, (_, i) => 1920 + i).map((year) => (
                                <option key={year} value={year}>
                                    {year}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="months-grid">
                        {["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"].map((month) => (
                            <button key={month}>{month}</button>
                        ))}
                    </div>
                </div>
                <div className="chart-section">
                    <div className="pie-chart-dash"></div> {/* Placeholder pentru grafic */}
                    <div className="legend">
                        <div><span className="legend-color pink"></span> Income</div>
                        <div><span className="legend-color yellow"></span> Savings</div>
                        <div><span className="legend-color blue"></span> Expenses</div>
                    </div>
                </div>
            </main>
        </div>
    );
};

export default Dashboard;
