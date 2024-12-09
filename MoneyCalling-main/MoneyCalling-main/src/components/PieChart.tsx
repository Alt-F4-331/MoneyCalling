import React from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

// Înregistrează componentele necesare Chart.js
ChartJS.register(ArcElement, Tooltip, Legend);

const PieChart: React.FC = () => {
  // Datele pentru Pie Chart
  const data = {
    labels: [
      'Rent', 
      'Transport', 
      'Food', 
      'Health', 
      'Clothing', 
      'Entertainment', 
      'Education', 
      'Savings'
    ], // Etichetele pentru fiecare secțiune
    datasets: [
      {
        label: 'Spendings',
        data: [30, 20, 25, 10, 15, 10, 5, 5], // Exemplu de valori pentru fiecare categorie
        backgroundColor: [
          '#DE80F2', // Locuință
          '#EFDA89', // Transport
          '#3BAEE1', // Alimentație
          '#F28B82', // Sănătate
          '#A7F28B', // Îmbrăcăminte
          '#F3A683', // Divertisment
          '#CAB6F2', // Educație
          '#E0E0E0', // Economii
        ],
        borderWidth: 0,
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        //position: 'top' as const,
        display: false,
      },
      title: {
        display: false
      }
    },
  };

  return (
    <div className="diagram-container">
  <div className="pie-chart">
    <Pie data={data} options={options} />
  </div>
  <div className="categories-card">
    <h4>Categories</h4>
    <div className="category">
          <span style={{ backgroundColor: '#DE80F2' }}></span>Rent
        </div>
        <div className="category">
          <span style={{ backgroundColor: '#EFDA89' }}></span>Transport
        </div>
        <div className="category">
          <span style={{ backgroundColor: '#3BAEE1' }}></span>Food
        </div>
        <div className="category">
          <span style={{ backgroundColor: '#F28B82' }}></span>Health
        </div>
        <div className="category">
          <span style={{ backgroundColor: '#A7F28B' }}></span>Clothing
        </div>
        <div className="category">
          <span style={{ backgroundColor: '#F3A683' }}></span>Entertainment
        </div>
        <div className="category">
          <span style={{ backgroundColor: '#CAB6F2' }}></span>Education
        </div>
        <div className="category">
          <span style={{ backgroundColor: '#E0E0E0' }}></span>Savings
        </div>
  </div>
</div>
  );
};

export default PieChart;
