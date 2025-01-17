import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Pie } from "react-chartjs-2";
import "chart.js/auto";
import "./Dashboard.css";
import logo from "../assets/logo.png";
import profile_pic from "../assets/profile_pic.jpg";
 
const Dashboard: React.FC = () => {
    const currentYear = new Date().getFullYear(); // Anul actual
    const [selectedYear, setSelectedYear] = useState<number>(currentYear);
    const [selectedMonth, setSelectedMonth] = useState<number | null>(null);
    const [chartData, setChartData] = useState({ income: 0, savings: 0, expenses: 0 });
    const [sumaContainer, setSumaContainer] = useState<number>(0);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [message, setMessage] = useState<string | null>(null);
 
    const token = localStorage.getItem("token"); // Token-ul utilizatorului
 
    const handleYearChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedYear(Number(event.target.value));
    };
 
    const handleMonthClick = (month: number) => {
        setSelectedMonth(month);
    };
 
    useEffect(() => {
        if (selectedMonth !== null) {
            fetchChartData(selectedYear, selectedMonth);
        }
    }, [selectedYear, selectedMonth]);
 
    const fetchChartData = async (year: number, month: number) => {
        setIsLoading(true);
        try {
            const response = await fetch(
                `http://localhost:8080/api/diagrame/getByData?luna=${month}&an=${year}`,
                {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                }
            );
 
            if (response.status === 404) {
                // Nu există date pentru luna și anul selectat
                setChartData({ savings: 0, expenses: 0 });
                setSumaContainer(0);
                setMessage("No data available for this month.")
                return;
            }
 
            if (!response.ok) throw new Error("Failed to fetch chart data");
 
            const data = await response.json();
 
            // Actualizează datele pentru grafic și sumaContainer
            setChartData({
                savings: data.economii,
                expenses: data.sumaCh,
            });
            setSumaContainer(data.sumaContainer);
        } catch (error) {
            console.error("Error fetching chart data:", error);
            alert("An error occurred while fetching the data.");
        } finally {
            setIsLoading(false);
        }
    };
 
    const pieOptions = {
        plugins: {
            legend: {
                display: false, // Ascunde legenda automată
            },
        },
    };
 
    const pieData = {
        labels: ["Savings", "Expenses"],
        datasets: [
            {
                label: "Financial Data",
                data: [chartData.savings, chartData.expenses],
                backgroundColor: ["#FFCD56", "#36A2EB"],
                hoverBackgroundColor: ["#FFCD56", "#36A2EB"],
            },
        ],
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
                    <div className="select-year">Select Month:</div>
                    <div className="months-grid">
                        {["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"].map(
                            (month, index) => (
                                <button
                                    key={month}
                                    onClick={() => handleMonthClick(index + 1)}
                                    className={selectedMonth === index + 1 ? "selected-month" : ""}
                                >
                                    {month}
                                </button>
                            )
                        )}
                    </div>
                </div>
                <div className="chart-section">
                    <div className="pie-chart-dash">
                        {isLoading ? (
                            <p>Loading...</p>
                        ) : chartData.savings === 0 && chartData.expenses === 0 && sumaContainer === 0 ? (
                            <p>No data available for the selected month and year.</p>
                        ) : (
                            <Pie data={pieData} options={pieOptions} />
                        )}
                    </div>
                    {chartData.savings !== 0 || chartData.expenses !== 0 ? (
                        <div className="legend">
                            <div><span className="legend-color yellow"></span> Savings: ${chartData.savings}</div>
                            <div><span className="legend-color blue"></span> Expenses: ${chartData.expenses}</div>
                        </div>
                    ) : null}
                    {sumaContainer > 0 && (
                        <div className="container-sum">
                            <p>Spent directly from Savings : ${sumaContainer}</p>
                        </div>
                    )}
                </div>
            </main>
        </div>
    );
};
 
export default Dashboard;
 