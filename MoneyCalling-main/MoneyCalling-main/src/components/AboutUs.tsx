import React from 'react';
import './AboutUs.css';
import { useNavigate } from 'react-router-dom';

const AboutUs: React.FC = () => {

    const navigate = useNavigate();
    const handleBackClick = () => {
        navigate('/homepage');
      };
      
  return (
    <div>
    <button onClick={handleBackClick} className="back-button-fp">&lt; Back</button>

    <div className="about-us">
      <header className="about-header">
        <h1>About Us</h1>
      </header>
        <p></p>
      <section className="about-section">
        <h2>Empowering You to Take Control of your Financial Future</h2>
        <p>
        Welcome to Money Calling! Our mission is simple: to help you manage your money with ease and confidence. We know that financial wellness is about more than just numbers – it’s about giving you the tools and insights to make informed decisions and achieve your goals, one step at a time.
        </p>

        <p>
        We believe that good financial management should be accessible to everyone, regardless of background or income level. Whether you’re budgeting for the first time, saving for a life goal, or simply looking to improve your spending habits, we’re here to help. 
        </p>
      </section>

      <section className="about-section">
        <h2>What We Offer</h2>
        <h3>Personalized Budgeting Tools</h3>
        <p>
        Set custom budgets, track spending, and get real-time insights to see where your money is going and how you can save more.
        </p>
        
        <h3>Insights and Analytics</h3>
        <p>
        Understand your spending trends and get customized recommendations. Our easy-to-read reports break down your finances, helping you identify opportunities to save and grow.
        </p>
      </section>

      <section className="about-section">
        <h2>Our Values</h2>
        <h3>Transparency:</h3>
        <p>We provide clear, actionable information so you always know where you stand.</p>
        <h3>Empowerement:</h3>
        <p> We’re here to make money management simple, providing tools that help you confidently make financial decisions.</p>
        <h3>Privacy and Security:</h3>
        <p>Your trust is important to us, and we prioritize data protection in everything we do.</p>
        <h3>Continuous Improvement:</h3>
        <p>We’re committed to evolving with your needs, constantly updating our tools to deliver the best financial solutions.
        </p>
      </section>

      <section className="about-section">
        <h2>Have Questions?</h2>
        <p> Visit our <a href="/help-us">Help Center</a> or reach out to us directly at <a href="mailto:altF4@gmail.com">altF4@gmail.com</a>. We're always here to help!</p>
      </section>
    </div>
    </div>
  );
};

export default AboutUs;
