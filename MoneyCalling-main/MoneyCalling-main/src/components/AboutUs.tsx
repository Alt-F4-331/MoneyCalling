import React, { useEffect } from 'react';
import './AboutUs.css';
import { Link } from "react-router-dom";
import logo from  '../assets/logo.png';
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
          <Link to="/dashboard">History</Link>
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
          <p>We believe that good financial management should be accessible to everyone, regardless of background or income level. Whether you're budgeting for the first time, saving for a life goal, or simply looking to improve your spending habits, we're here to help.</p>
        </section>

        <section className="about-section">
          <h2>What We Offer</h2>
          <h3>Personalized Budgeting Tools</h3>
          <p>Set custom budgets, track spending, and get real-time insights. Manage your finances effortlessly with tailored recommendations to help you stay on track and reach your financial goals.</p>
          <h3>Insights and Analytics</h3>
          <p>Understand your spending trends and get customized recommendations. Our easy-to- read reports break down your finances, helping you identify opportunities to save and grow.</p>
        </section>

        <section className="about-section">
          <h2>Our Values</h2>
          <h3>Transparency</h3>
          <p>We provide clear, actionable information so you always know where you stand. Trust and integrity guide our actions, helping you make confident financial choices.</p>
          <h3>Empowerment</h3>
          <p>We're here to make money management simple, providing tools that help you confidently make financial decisions.</p>
          <h3>Privacy and Security</h3>
          <p>Your trust is important to us, and we prioritize data protection in everything we do, maintaining the highest standards of security</p>
          <h3>Continuous Improvement</h3>
          <p>We're committed to evolving with your needs, constantly updating our tools to deliver the best financial solutions.</p>
        </section>

        <section className="about-section">
          <h2>Have Questions?</h2>
          <p>Visit our <a href="/help">Help Center</a>.</p>
          <p>We're always here to help!</p>
        </section>
      </div>
    </div>
  );
};

export default AboutUs;
