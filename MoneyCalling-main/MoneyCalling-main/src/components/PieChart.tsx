import React, { useEffect, useState } from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import axios from 'axios';

// Înregistrează componentele necesare Chart.js
ChartJS.register(ArcElement, Tooltip, Legend);

interface PieChartProps {
  onCategoriesFetched: (categories: string[]) => void; // Callback pentru a transmite categoriile
  updateTrigger: number; // Adăugăm updateTrigger ca prop
}

const PieChart: React.FC<PieChartProps> = ({ onCategoriesFetched , updateTrigger}) => {
  // State-uri pentru datele din grafic și categorie
  const [data, setData] = useState({
    labels: [] as string[],
    datasets: [
      {
        label: 'Spendings',
        data: [] as number[],
        backgroundColor: [] as string[],
        borderWidth: 0,
      },
    ],
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          console.error('Token-ul nu este disponibil');
          return;
        }

        const response = await axios.get('http://localhost:8080/api/diagrame/configurare', {
          headers: {
            Authorization: `Bearer ${token}`, // Folosim interpolare corectă
          },
        });

        const backendData = response.data;

        const labels = Object.keys(backendData).filter(label => label !== "CONTAINER");
        const values = labels.map(label => backendData[label]);
        const backgroundColors = [
        '#DE80F2', '#EFDA89', '#3BAEE1', '#F28B82', '#A7F28B', '#F3A683', '#CAB6F2', '#E0E0E0',
        ];

        setData({
          labels,
          datasets: [
            {
              label: 'Spendings',
              data: values,
              backgroundColor: backgroundColors.slice(0, labels.length),
              borderWidth: 0,
            },
          ],
        });

        // Transmite categoriile către părinte prin callback
        onCategoriesFetched(labels);

        setLoading(false);
      } catch (error) {
        console.error('Eroare la preluarea datelor:', error);
        setLoading(false);
      }
    };

    fetchData();
  }, [onCategoriesFetched]);

  const options = {
    responsive: true,
    plugins: {
      legend: {
        display: false,
      },
      title: {
        display: false,
      },
    },
  };

  return (
    <div className="diagram-container">
      <div className="pie-chart">
        {loading ? (
          <p>Se încarcă graficul...</p>
        ) : (
          <Pie data={data} options={options} />
        )}
      </div>
      <div className="categories-card">
        <h4>Categories</h4>
        {data.labels.map((label, index) => (
          <div className="category" key={label}>
            <span style={{ backgroundColor: data.datasets[0].backgroundColor[index] }}></span>
            {label}
          </div>
        ))}
      </div>
    </div>
  );
};

export default PieChart;
