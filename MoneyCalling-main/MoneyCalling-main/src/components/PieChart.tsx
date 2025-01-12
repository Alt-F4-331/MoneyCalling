/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useEffect, useState } from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode'; // Decodare JWT
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
  const [expenses, setExpenses] = useState<any[]>([]); // Lista cheltuielilor
  const [filteredExpenses, setFilteredExpenses] = useState<any[]>([]); // Cheltuieli filtrate

  // Extrage ID-ul utilizatorului din token
  const getUserIdFromToken = (token: string) => {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const decodedToken: any = jwtDecode(token); // Decodăm token-ul
    return decodedToken.sub; // Presupunem că ID-ul utilizatorului este în câmpul 'sub' al token-ului
  };

  useEffect(() => {
    const fetchDiagramData = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          console.error('Token-ul nu a fost găsit.');
          setLoading(false);
          return;
        }

        const userId = getUserIdFromToken(token);
        if (!userId) {
          console.error('Id-ul utilizatorului nu a fost găsit.');
          setLoading(false);
          return;
        }

        // Obține lista diagramelor utilizatorului
        const diagramResponse = await axios.get(`http://localhost:8080/api/diagrame/utilizator/${userId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        const diagrams = diagramResponse.data;
        if (diagrams.length === 0) {
          console.error('Utilizatorul nu are diagrame asociate.');
          setLoading(false);
          return;
        }

        const activeDiagram = diagrams[0]; // Prima diagramă
        const diagramId = activeDiagram.id;

        // Obține datele pentru cheltuieli asociate diagramei
        const expensesResponse = await axios.get(`http://localhost:8080/api/cheltuieli/diagrama/${diagramId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        setExpenses(expensesResponse.data);
        setFilteredExpenses(expensesResponse.data);

        // Obține datele pentru grafic
        const diagramDataResponse = await axios.get(`http://localhost:8080/api/diagrame/configurare`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        const backendData = diagramDataResponse.data;
        const labels = Object.keys(backendData).filter(label => label !== 'CONTAINER');
        const values = labels.map(label => backendData[label]);
        const backgroundColors = ['#DE80F2', '#EFDA89', '#3BAEE1', '#F28B82', '#A7F28B', '#F3A683', '#CAB6F2', '#E0E0E0'];

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

        onCategoriesFetched(labels);
        setLoading(false);
      } catch (error) {
        console.error('Eroare la preluarea datelor:', error);
        setLoading(false);
      }
    };

    fetchDiagramData();
  }, [onCategoriesFetched, updateTrigger]);

  const total = data.datasets[0].data.reduce((sum, value) => sum + value, 0);

  const filterExpenses = (category: string) => {
    if (category === 'All') {
      setFilteredExpenses(expenses);
    } else {
      setFilteredExpenses(expenses.filter(expense => expense.category === category));
    }
  };

  return (
    <div className="diagram-container">
      <div className="pie-chart">
        {loading ? (
          <p>Se încarcă graficul...</p>
        ) : (
          <Pie
            data={data}
            options={{
              responsive: true,
              plugins: {
                legend: { display: false },
                tooltip: {
                  callbacks: {
                    label: (tooltipItem: any) => {
                      const dataset = tooltipItem.dataset;
                      const currentValue = dataset.data[tooltipItem.dataIndex];
                      const total = dataset.data.reduce((sum: number, value: number) => sum + value, 0);
                      const percentage = ((currentValue / total) * 100).toFixed(2);
                      return `${tooltipItem.label}: ${currentValue} (${percentage}%)`;
                    },
                  },
                },
                title: { display: false },
              },
            }}
          />
        )}
      </div>

      {/* Categories card */}
      <div className="dashboard-container">
      <div className="categories-card">
        <h4>Categories</h4>
        {data.labels.map((label, index) => {
          const value = data.datasets[0]?.data?.[index] || 0;
          const percentage = total > 0 ? ((value / total) * 100).toFixed(2) : '0.00';
          return (
            <div className="category" key={label}>
              <span style={{ backgroundColor: data.datasets[0].backgroundColor[index] }}></span>
              {label} | {percentage}% -
              <span style={{ color: value > 0 ? '#02FF3C' : 'red' ,
                marginTop: '-10px'
              }}>{value}$</span>
            </div>
          );
        })}
      </div>

      {/* Expenses card */}
      <div className="expenses-card">
  <div className="filter-header">
    <div className="filter-container">
      <select
        className="filter-dropdown"
        onChange={(e) => filterExpenses(e.target.value)}
        defaultValue="Filter"
      >
        <option value="Filter" >Filter</option>
        {data.labels.map((label) => (
          <option key={label} value={label}>
            {label}
          </option>
        ))}
      </select>
    </div>
  </div>
  <ul>
    {filteredExpenses.map((expense) => (
      <li key={expense.id}>
        {expense.name} -{' '}
        <span style={{ color: expense.amount > 0 ? '#02FF3C' : 'red' }}>
          {expense.amount}$
        </span>
      </li>
    ))}
  </ul>
</div>
</div>
    </div>
  );
};

export default PieChart;