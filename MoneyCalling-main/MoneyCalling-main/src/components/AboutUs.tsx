import React, { useEffect } from 'react';
import './AboutUs.css';
import { Link } from "react-router-dom";
import logo from "/public/logo.png";
import profile_pic from "../assets/profile_pic.jpg";

const AboutUs: React.FC = () => {
  useEffect(() => {
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add("fade-in-scroll");
          }
        });
      },
      { threshold: 0.2 }
    );

    const sections = document.querySelectorAll(".about-section");
    sections.forEach((section) => observer.observe(section));

    return () => observer.disconnect();
  }, []);

  return (
    <div>
      <header className="navbar">
        <Link to="/info-page">
          <img src={logo} alt="Logo" className="logo-image" />
        </Link>
        <nav className="nav-links">
          <Link to="/homepage">Home</Link>
          <Link to="/dashboard">Dashboard</Link>
          <Link to="/financial-profile">Financial Profile</Link>
          <Link to="/help">Help</Link>
          <Link to="/about-us" className="active">About Us</Link>
        </nav>
        <a href="/my-account" className="profile-link">
          <img src={profile_pic} alt="Profile" className="profile-image" />
        </a>
      </header>
      <div className="about-us">
        <header className="about-header">
          <h1>About Us</h1>
        </header>
        <section className="about-section">
          <h2>Empowering You to Take Control of your Financial Future</h2>
          <p>Welcome to Money Calling! Our mission is simple: to help you manage your money with ease and confidence.</p>
        </section>

        <section className="about-section">
          <h2>What We Offer</h2>
          <h3>Personalized Budgeting Tools</h3>
          <p>Set custom budgets, track spending, and get real-time insights.</p>
        </section>

        <section className="about-section">
          <h2>Our Values</h2>
          <h3>Transparency:</h3>
          <p>We provide clear, actionable information so you always know where you stand.</p>
        </section>

        <section className="about-section">
          <h2>Have Questions?</h2>
          <p>Visit our <a href="/help">Help Center</a>.</p>
        </section>
      </div>
    </div>
  );
};

export default AboutUs;
