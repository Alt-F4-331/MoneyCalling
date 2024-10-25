import React from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

// Înregistrează componentele necesare Chart.js
ChartJS.register(ArcElement, Tooltip, Legend);

const PieChart: React.FC = () => {
  // Datele pentru Pie Chart
  const data = {
    labels: ['A', 'B', 'C', 'D'], // Etichetele pentru fiecare secțiune
    datasets: [
      {
        label: 'My First Dataset',
        data: [300, 50, 100, 150], // Valorile pentru fiecare secțiune
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
        ],
        borderWidth: 1,
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top' as const,
      },
      title: {
        display: true,
        text: 'Pie Chart Example',
      },
    },
  };

  return (
    <div style={{ width: '600px', height: '600px' }}>

  <Pie data={data} options={options} />
  </div>
  );
};

export default PieChart;
