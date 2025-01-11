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
    const [isLoading, setIsLoading] = useState<boolean>(false);

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
        console.log("Fetching data for:", year, month); // Log pentru debug
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
        if (!response.ok) throw new Error("Failed to fetch chart data");

        const data = await response.json();
        console.log("API Response:", data); // Debug răspuns API

        // Venitul total (CONTAINER) preluat din backend
        const totalIncome = data.procenteCheltuieli?.CONTAINER || 0;

        // Calcularea cheltuielilor pe baza procentajelor
        const expenses = Object.keys(data.procenteCheltuieli || {}).reduce((sum: number, key: string) => {
            if (key !== 'CONTAINER') {
                const percentage = data.procenteCheltuieli[key];
                if (typeof percentage === "number") {
                    sum += (percentage / 100) * totalIncome;
                }
            }
            return sum;
        }, 0); // Suma totală a cheltuielilor

        // Calcularea economiilor (ceea ce rămâne după cheltuieli)
        const savings = totalIncome - expenses;

        setChartData({
            savings: savings > 0 ? savings : 0, // Economii pozitive
            expenses,
        });
    } catch (error) {
        console.error("Error fetching chart data:", error);
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
                        ) : (
                            <Pie data={pieData} options={pieOptions} />
                        )}
                    </div>
                    <div className="legend">
                        <div><span className="legend-color yellow"></span> Savings: ${chartData.savings}</div>
                        <div><span className="legend-color blue"></span> Expenses: ${chartData.expenses}</div>
                    </div>
                </div>
            </main>
        </div>
    );
};

export default Dashboard;
