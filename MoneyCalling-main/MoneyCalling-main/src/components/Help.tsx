import React, { useEffect } from 'react';
import './Help.css';
import { Link } from "react-router-dom";
import logo from  '../assets/logo.png';
import profile_pic from "../assets/profile_pic.jpg";

const Help: React.FC = () => {
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

    const sections = document.querySelectorAll(".help-section");
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
          <Link to="/help" className="active">Help</Link>
          <Link to="/about-us">About Us</Link>
        </nav>
        <a href="/my-account" className="profile-link">
          <img src={profile_pic} alt="Profile" className="profile-image" />
        </a>
      </header>

      <div className="help">
        <header className="help-header">
          <h1>Help Center</h1>
        </header>
        <section className="help-section">
          <p>Welcome to the Help Center! We're here to assist you in making the most of our money management tools. Browse the topics below or reach out to us if you have questions.</p>
        </section>
        <section className="help-section">
          <h2>Getting Started</h2>
          <h3>What does this platform do?</h3>
          <p>
            Our platform is designed to help you organize, track, and optimize your finances. From budgeting to setting financial goals and monitoring your spending, we provide tools and insights to make money management easier.
          </p>
        </section>

        <section className="help-section">
          <h3>How do I sign up?</h3>
          <p>
            Simply click on the "Sign Up" button on our homepage. Follow the prompts to create an account by entering your email, setting up a password, and confirming your details.
          </p>
        </section>

        <section className="help-section">
          <h2>Using the Platform</h2>
          <h3>How do I set a budget?</h3>
          <p>
            Go to the Dashboard tab. You can set a monthly budget for categories like groceries, entertainment, bills, and more. Track your expenses to see if you’re staying within your budget.
          </p>
          <h3>How can I view my spending habits?</h3>
          <p>Visit the Insights page to see breakdowns of your spending over time. You'll find visual reports, monthly comparisons, and spending trends to help you identify where you can cut costs.</p>
        </section>

        <section className="help-section">
          <h2>Account & Security</h2>
          <h3>Can I change my password?</h3>
          <p>
            Yes. To change your password, go to Account Settings, select “Security,” and follow the instructions to set a new password.
          </p>
          <h3>How do I delete my account?</h3>
          <p>If you wish to close your account, go to Account Settings and select "Delete Account." Please note that this action is irreversible, and all data will be permanently deleted.</p>
        </section>

        <section className="help-section">
          <h2>Troubleshooting</h2>
          <h3>What should I do if I forget my password?</h3>
          <p>
            Yes. To change your password, go to Account Settings, select “Security,” and follow the instructions to set a new password.
          </p>
          <h3>How do I delete my account?</h3>
          <p>On the login page, click "Forgot Password?" Enter the email associated with your account, and we'll send you a link to reset it.</p>
        </section>

        <section className="help-section">
          <h2>Contact Us</h2>
          <p>
            If you can’t find the answer here, our Support Team is available to assist you.
            Email Support: <a href="mailto:altF4sup@gmail.com">altF4sup@gmail.com</a><br />
            Phone Support: 07********
          </p>
        </section>

        <section className="help-section">
          <p>We're committed to helping you take control of your finances. Let us know if you need assistance, and thank you for choosing us as your financial partner!</p>
        </section>
      </div>
    </div>
  );
};

export default Help;
