import React from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

// Înregistrează componentele necesare Chart.js
ChartJS.register(ArcElement, Tooltip, Legend);

const PieChart: React.FC = () => {
  // Datele pentru Pie Chart
  const data = {
    labels: ['Category 1', 'Category 2', 'Category 3'], // Etichetele pentru fiecare secțiune
    datasets: [
      {
        label: 'My First Dataset',
        data: [300, 50, 100], // Valorile pentru fiecare secțiune
        backgroundColor: [
          '#DE80F2',
          '#EFDA89',
          '#3BAEE1',
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
        display: true,
        text: 'Pie Chart Example',
      },
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
      <span style={{ backgroundColor: '#DE80F2' }}></span>Category 1
    </div>
    <div className="category">
      <span style={{ backgroundColor: '#EFDA89' }}></span>Category 2
    </div>
    <div className="category">
      <span style={{ backgroundColor: '#3BAEE1' }}></span>Category 3
    </div>
  </div>
</div>
  );
};

export default PieChart;
