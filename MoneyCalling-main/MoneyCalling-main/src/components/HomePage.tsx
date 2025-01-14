/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useEffect, useRef, useState } from 'react';
import './HomePage.css';
import logo from '../assets/logo.png';
import profile_pic from "../assets/profile_pic.jpg";
import PieChart from './PieChart';
import { Link } from 'react-router-dom';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';



const HomePage: React.FC = () => {
    //categorii pentru expense
    const [categories, setCategories] = useState<string[]>([
        'FOOD',           // Alimentație
        'HOME',           // Locuință
        'EDUCATION',      // Educație
        'HEALTH',         // Sănătate
        'DIVERTISMENT',   // Divertisment
        'TRANSPORT',      // Transport
        'CLOTHING',       // Îmbrăcăminte
        'SAVINGS',        // Economii
    ]);

    // Maparea categoriilor (frontend -> backend)
    const categoryMap = {
        FOOD: 'ALIMENTATIE',
        HOME: 'LOCUINTA',
        EDUCATION: 'EDUCATIE',
        HEALTH: 'SANATATE',
        DIVERTISMENT: 'DIVERTISMENT',
        TRANSPORT: 'TRANSPORT',
        CLOTHING: 'IMBRACAMINTE',
        SAVINGS: 'CONTAINER',
    };

    // Memorizează categoriile pentru a preveni recalculările inutile
    const memorizedCategories = React.useMemo(() => categories, [categories]);
    const [message, setMessage] = useState<string | null>(null);


    // const [showRentPopup, setShowRentPopup] = useState(false);
    // const [showWarningPopup, setShowWarningPopup] = useState(false);
    // const [rentAmount, setRentAmount] = useState<number>(0);
    //puse de david
    const [showRentPopup, setShowRentPopup] = useState(false); // Control pop-up Rent
    const [showWarningPopup, setShowWarningPopup] = useState(false); // Control pop-up Warning
    const [rentAmount, setRentAmount] = useState<number>(0); // Valoare introdusă pentru chirie
    const [rentSuggestion, setRentSuggestion] = useState<number | null>(null); // Valoare chirie sugerată
    const [proposedRent, setProposedRent] = useState<number>(0); // Valoare chirie propusă

    // Starea care va urmări dacă profilul financiar este complet sau nu
    const [isProfileComplete, setIsProfileComplete] = useState(false);
    //
    const [savingsSum, setSavingsSum] = useState<number>(0);
    const [installmentSum, setInstallmentSum] = useState<number>(0);
    const [recommendedSum, setRecommendedSum] = useState<number>(0);
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const [budgetRange, setBudgetRange] = useState({ min: 0, max: 10000 }); // valorile date sunt un exemplu; urmeaza sa fie legat cu backend-ul
    const [amount, setAmount] = useState<number>(0); // State pentru Amount
    const [category, setCategory] = useState<string>(""); // State pentru Category; va trebui sa faca parte din lista de categorii din baza de date
    const [showPopup, setShowPopup] = useState(false);
    const [updateTrigger, setUpdateTrigger] = useState(0); // Adăugăm updateTrigger pentru reîncărcarea graficului
    //Holiday
    const [showHolidayPopup, setShowHolidayPopup] = useState(false);
    const [holidayDays, setHolidayDays] = useState<number>(0);
    const [holidaySum, setHolidaySum] = useState<number>(0);
    const [showInstallmentsPopup, setShowInstallmentsPopup] = useState(false);
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const [installmentOptions, setInstallmentOptions] = useState([6, 12, 24, 36, 48]);
    const [customInstallment, setCustomInstallment] = useState<number | ''>('');
    const [selectedInstallment, setSelectedInstallment] = useState<number | null>(null);
    const [recommendedTravelSum, setRecommendedTravelSum] = useState<number>(0);
    const [recommendedAccommodationSum, setRecommendedAccommodationSum] = useState<number>(0);

    const [showSubscriptionPopup, setShowSubscriptionPopup] = useState(false);
    const [showAddSubscriptionPopup, setShowAddSubscriptionPopup] = useState(false);
    const [subscriptions, setSubscriptions] = useState<any[]>([]);

    const [newSubscriptionName, setNewSubscriptionName] = useState("");
    const [newSubscriptionPrice, setNewSubscriptionPrice] = useState("");
    const [paymentFrequency, setPaymentFrequency] = useState<"monthly" | "yearly">("monthly");

    // Stările pentru ziua și luna plății
    const [paymentDay, setPaymentDay] = useState<number>(1);  // Ziua pentru plata abonamentului
    const [paymentMonth, setPaymentMonth] = useState<number>(1);  // Luna pentru plata abonamentului (valabil doar pentru abonamentele anuale)
    const [showSavingsPopup, setShowSavingsPopup] = useState(false);
    const canvasRef = useRef<HTMLCanvasElement>(null);
    const [selectedMonths, setSelectedMonths] = useState('');

    const [name, setName] = useState<string>("");


    const closeMessage = () => setMessage(null);

    const handleOpenSavingsPopup = () => {
        setShowSavingsPopup(true);
    }

    const handleCloseSavingsPopup = () => {
        setShowSavingsPopup(false);
    };




    const handleMonthSelection = async (months: string) => {
        setSelectedMonths(months); // Actualizează selecția lunilor (pentru styling sau alte utilizări)

        const token = localStorage.getItem("token");
        if (!token) {
            console.error("Token-ul nu este disponibil");
            return;
        }

        try {
            // Realizează cererea către backend
            const response = await fetch(`http://localhost:8080/api/rapoarte/economii?luni=${months}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                throw new Error("Eroare la obținerea datelor economiilor");
            }

            const data = await response.json();
            console.log("Datele economiilor primite:", data); // Debug: verifică răspunsul din backend

            setSelectedMonths(data); // Actualizează starea cu datele primite
        } catch (error) {
            console.error("Eroare la cererea datelor economiilor:", error);
        }
    };


    useEffect(() => {
        // Functie care face cererea pentru a obține datele economiilor
        const fetchSavingsData = async () => {
            const token = localStorage.getItem('token');
            if (!token) {
                console.error('Token-ul nu este disponibil');
                return;
            }

            try {
                const response = await fetch(`http://localhost:8080/api/rapoarte/economii?luni=3`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                });

                if (!response.ok) {
                    throw new Error('Eroare la obținerea datelor economiilor');
                }

                const data = await response.json();
                console.log('Date primite de la API:', data);
                setSelectedMonths(data); // Setează datele economiilor
            } catch (error) {
                console.error('Eroare:', error);
            }
        };

        if (showSavingsPopup && canvasRef.current) {
            fetchSavingsData(); // Apelăm funcția de fetch atunci când pop-up-ul se deschide
        }
    }, [showSavingsPopup]);

    useEffect(() => {
        if (showSavingsPopup && canvasRef.current && Object.keys(selectedMonths).length > 0) {
            const ctx = canvasRef.current.getContext('2d');
            if (ctx) {
                drawChart(ctx, {
                    months: Object.keys(selectedMonths),
                    savings: Object.values(selectedMonths),
                });
            }
        }
    }, [showSavingsPopup, selectedMonths]);



    const drawChart = (ctx, data) => {
        const canvasWidth = ctx.canvas.width;
        const canvasHeight = ctx.canvas.height;

        // Clear the canvas
        ctx.clearRect(0, 0, canvasWidth, canvasHeight);

        const margin = 50;
        const chartWidth = canvasWidth - 2 * margin;
        const chartHeight = canvasHeight - 2 * margin;

        // Prepare data
        const labels = Object.keys(data.months).reverse(); // ["Jan 2023", "Feb 2023", ...] in reverse order
        const values = Object.values(data.savings).reverse(); // [12.34, 45.67, ...] in reverse order

        const maxValue = Math.max(...values);
        const minValue = Math.min(...values);

        const xStep = chartWidth / labels.length;
        const yScale = chartHeight / (maxValue - minValue);

        // Draw axes
        ctx.beginPath();
        ctx.moveTo(margin, margin);
        ctx.lineTo(margin, canvasHeight - margin); // Y-axis
        ctx.lineTo(canvasWidth - margin, canvasHeight - margin); // X-axis
        ctx.strokeStyle = '#ffffff';
        ctx.lineWidth = 2;
        ctx.stroke();

        // Draw arrows
        drawArrow(ctx, canvasWidth - margin, canvasHeight - margin, 10, 0); // X-axis
        drawArrow(ctx, margin, margin, 0, -10); // Y-axis

        // Draw labels and points
        labels.forEach((label, index) => {
            const x = margin + index * xStep + xStep / 2;
            const y = canvasHeight - margin - (values[index] - minValue) * yScale;

            // Draw point
            ctx.beginPath();
            ctx.arc(x, y, 5, 0, 2 * Math.PI);
            ctx.fillStyle = '#00ff00';
            ctx.fill();

            // Draw label
            ctx.fillStyle = '#ffffff';
            ctx.textAlign = 'center';
            ctx.fillText(label, x, canvasHeight - margin + 20);

            // Draw connecting line
            if (index > 0) {
                const prevX = margin + (index - 1) * xStep + xStep / 2;
                const prevY = canvasHeight - margin - (values[index - 1] - minValue) * yScale;

                ctx.beginPath();
                ctx.moveTo(prevX, prevY);
                ctx.lineTo(x, y);
                ctx.strokeStyle = '#00ff00';
                ctx.lineWidth = 2;
                ctx.stroke();
            }
        });
    };

    const drawArrow = (ctx, startX, startY, angleX, angleY) => {
        const arrowSize = 10;
        const angle = Math.atan2(angleY, angleX);
        ctx.beginPath();
        ctx.moveTo(startX, startY);
        ctx.lineTo(
            startX - arrowSize * Math.cos(angle - Math.PI / 6),
            startY - arrowSize * Math.sin(angle - Math.PI / 6)
        );
        ctx.moveTo(startX, startY);
        ctx.lineTo(
            startX - arrowSize * Math.cos(angle + Math.PI / 6),
            startY - arrowSize * Math.sin(angle + Math.PI / 6)
        );
        ctx.strokeStyle = '#fff';
        ctx.lineWidth = 2;
        ctx.stroke();
    };





    // Tipul așteptat pentru obiectul de abonament
    interface SubscriptionDTO {
        nume: string;
        valoare: number;
        tipAbonament: "Lunar" | "Anual";  // Modificat aici
        ziua: number;
        luna: number;
    }

    const handleOpenSubscriptionPopup = async () => {
        setShowSubscriptionPopup(true);

        // Apelăm funcția pentru a obține abonamentele utilizatorului
        await fetchSubscriptions();
    };

    const fetchSubscriptions = async () => {
        const token = localStorage.getItem("token");
        if (!token) {
            console.error("Token-ul nu este disponibil");
            return;
        }

        try {
            const response = await axios.get("http://localhost:8080/api/abonamente/abonamente", {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            // Actualizăm lista de abonamente
            setSubscriptions(response.data);
        } catch (error) {
            console.error("Eroare la obținerea abonamentelor:", error);
        }
    };

    const handleCloseSubscriptionPopup = () => {
        setShowSubscriptionPopup(false);
    };

    const handleOpenAddSubscriptionPopup = () => {
        setShowAddSubscriptionPopup(true);
    };

    const handleCloseAddSubscriptionPopup = () => {
        setShowAddSubscriptionPopup(false);
    };

    const handleAddSubscription = async (e: React.FormEvent) => {
        e.preventDefault();

        // Validarea câmpurilor
        if (!newSubscriptionName || !newSubscriptionPrice || !paymentDay || (paymentFrequency === "yearly" && !paymentMonth)) {
            console.error("Toate câmpurile sunt obligatorii!");
            return;
        }

        // Modifică paymentFrequency pentru a trimite "Lunar" sau "Anual"
        const formattedFrequency = paymentFrequency === "monthly" ? "Lunar" : "Anual";

        // Pregătirea datelor pentru backend
        const newSubscription: SubscriptionDTO = {
            nume: newSubscriptionName, // Numele abonamentului
            valoare: parseFloat(newSubscriptionPrice), // Prețul abonamentului
            tipAbonament: formattedFrequency, // Trimitem "Lunar" sau "Anual"
            ziua: paymentDay, // Ziua pentru plata abonamentului
            luna: paymentFrequency === "yearly" ? paymentMonth : 1, // Luna pentru plata abonamentului (1 pentru monthly, luna selectată pentru yearly)
        };

        const token = localStorage.getItem("token");
        if (!token) {
            console.error("Token-ul nu este disponibil");
            return;
        }

        try {
            const response = await axios.post("http://localhost:8080/api/abonamente", newSubscription, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            console.log("Abonament adăugat:", response.data);

            // Actualizarea listei de abonamente
            setSubscriptions([...subscriptions, newSubscription]);

            // Resetarea câmpurilor
            setNewSubscriptionName("");
            setNewSubscriptionPrice("");
            setPaymentDay(1);
            setPaymentMonth(1);
            setShowAddSubscriptionPopup(false);
        } catch (error) {
            console.error("Eroare la adăugarea abonamentului:", error);
        }
    };

    const handleDeleteSubscription = async (subscriptionId: number) => {
        const token = localStorage.getItem("token");

        if (!token) {
            console.error("Token-ul nu este disponibil");
            return;
        }

        try {
            // Apelul pentru a șterge abonamentul
            await axios.delete(`http://localhost:8080/api/abonamente/${subscriptionId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            // Actualizarea listei de abonamente după ștergere
            const updatedSubscriptions = subscriptions.filter(subscription => subscription.id !== subscriptionId);
            setSubscriptions(updatedSubscriptions);

        } catch (error) {
            console.error("Eroare la ștergerea abonamentului:", error);
        }
    };


    // Funcții pentru deschiderea și închiderea pop-up-ului pentru Holiday Report
    const handleOpenHolidayPopup = () => {
        setShowHolidayPopup(true);
    };

    const handleCloseHolidayPopup = () => {
        setShowHolidayPopup(false);
        setRecommendedAccommodationSum(0);
        setRecommendedTravelSum(0);
        setHolidayDays(0); // Resetare număr de zile
        setHolidaySum(0); // Resetare sumă pentru vacanță
    };

    // Funcție pentru apelul automat al endpoint-ului /sugereaza-vacanta
    const fetchHolidaySuggestion = async (nrZile: number, bugetTotal: number) => {
        const token = localStorage.getItem("token");

        if (!token) {
            console.error("Token-ul nu este disponibil. Vă rugăm să vă autentificați.");
            return;
        }

        try {
            const response = await fetch(
                `http://localhost:8080/api/rapoarte/sugereaza-vacanta?nrZile=${nrZile}&bugetTotal=${bugetTotal}`,
                {
                    method: "GET",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                    },
                }
            );

            const contentType = response.headers.get("Content-Type");

            if (contentType && contentType.includes("application/json")) {
                const data = await response.json();
                console.log("Răspuns JSON:", data);

                // Afișează sumele recomandate
                setRecommendedTravelSum(data.bugetDistribuit.Transport);
                setRecommendedAccommodationSum(data.bugetDistribuit.Cazare);
            } else {
                const text = await response.text();
                console.log("Răspuns text:", text);
                setMessage(text);
            }
        } catch (error) {
            console.error("Eroare la obținerea bugetului propus:", error);
            setMessage("A apărut o eroare la obținerea bugetului propus.");
        }
    };

    // Monitorizează schimbările la numărul de zile și buget pentru a apela automat endpoint-ul
    useEffect(() => {
        if (holidayDays > 0 && holidaySum > 0) {
            fetchHolidaySuggestion(holidayDays, holidaySum);
        }
    }, [holidayDays, holidaySum]);

    const handleHolidaySubmit = async (e: React.FormEvent) => {
        const token = localStorage.getItem("token");

        if (!token) {
            setMessage("Token-ul nu este disponibil. Vă rugăm să vă autentificați.");
            return;
        }

        try {
            const response = await fetch(
                `http://localhost:8080/api/rapoarte/confirma-vacanta?confirm=${true}`,
                {
                    method: "POST",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                    },
                }
            );

            console.log(`Status: ${response.status} (${response.statusText})`);

            if (response.ok) {
                const message = await response.text();
                setMessage(message); // Mesaj de succes
                localStorage.setItem('savings', String(savingsSum));
                setTriggerReload((prev) => !prev);
            } else {
                const errorText = await response.text();
                setMessage(`Eroare: ${errorText}`); // Mesaj de eroare
            }
        } catch (error) {
            console.error("Eroare la confirmarea vacanței:", error);
            setMessage("A apărut o eroare la confirmarea vacanței.");
        }
    };

    //inceput david

    // Deschidere și închidere popups
    const handleOpenRentPopup = () => {
        setShowRentPopup(true); // Deschide pop-up-ul
    };

    const handleCloseRentPopup = () => {
        setShowRentPopup(false); // Închide pop-up-ul
        setRentAmount(0); // Resetare sumă chirie introdusă
        setRentSuggestion(null); // Resetare valoare chirie sugerată
        setProposedRent(0); // Resetare chirie propusă
    };

    // Funcție pentru a trimite chiria propusă
    const submitProposedRent = async () => {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('Vă rugăm să vă autentificați pentru a continua.');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/rapoarte/initiaza-chirie?chiriePropusa=${rentAmount}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ chiriePropusa: rentAmount }), // Trimite chiria propusă
            });

            if (response.ok) {
                alert('Chiria propusă a fost trimisă cu succes.');


                setProposedRent(rentAmount)

                // Verifică dacă suma introdusă este mai mare decât suma sugerată
                if (rentAmount > rentSuggestion) {
                    setShowWarningPopup(true); // Afișează pop-up-ul de avertizare
                } else {
                    setShowRentPopup(false); // Închide pop-up-ul după trimitere
                }

            } else {
                alert('A apărut o eroare la trimiterea chiriei propuse. Vă rugăm să încercați din nou.');
            }
        } catch {
            alert('Eroare de rețea. Vă rugăm să verificați conexiunea.');
        }
    };

    // Funcție pentru a confirma chiria propusă
    const handleCloseWarningPopup = async (isConfirmed: boolean) => {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('Vă rugăm să vă autentificați pentru a continua.');
            return;
        }

        if (isConfirmed) {
            // Dacă utilizatorul confirmă
            try {
                const response = await fetch(`http://localhost:8080/api/rapoarte/confirma-chirie?chiriePropusa=${rentAmount}`, {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {
                    alert('Chiria a fost confirmată cu succes.');
                    setShowWarningPopup(false); // Închide warning pop-up
                    setShowRentPopup(false); // Închide toate pop-up-urile
                } else {
                    const errorText = await response.text();
                    alert(`A apărut o eroare la confirmarea chiriei: ${errorText}`);
                }
            } catch (error) {
                alert('Eroare de rețea. Vă rugăm să verificați conexiunea.');
            }
        } else {
            // Dacă utilizatorul refuză
            setShowWarningPopup(false); // Închide warning pop-up
            setShowRentPopup(true); // Revină la pop-up-ul anterior
        }
    };

    // Funcție pentru a calcula chiria sugerată
    const fetchRentSuggestion = async () => {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('Vă rugăm să vă autentificați pentru a continua.');
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/api/rapoarte/sugereaza-chirie', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });

            if (response.ok) {
                const suggestedRent = await response.json();
                setRentSuggestion(suggestedRent); // Actualizează valoarea chiriei sugerate
            } else {
                alert('A apărut o eroare la obținerea chiriei sugerate. Vă rugăm să încercați din nou.');
            }
        } catch {
            alert('Eroare de rețea. Vă rugăm să verificați conexiunea.');
        }
    };

    // Apel automat al funcției fetchRentSuggestion la deschiderea pop-up-ului
    useEffect(() => {
        if (showRentPopup) {
            fetchRentSuggestion(); // Apelează API-ul când pop-up-ul se deschide
        }
    }, [showRentPopup]); // Trigger la modificarea valorii showRentPopup

    // Deschidere și închidere pop-up general
    const handleOpenPopup = () => {
        setShowPopup(true); // Deschide un alt pop-up (dacă este cazul)
    };

    const handleClosePopup = () => {
        setShowPopup(false); // Închide pop-up-ul corespunzător
    };

    // Funcție pentru a trimite chiria propusă
    const handleRentSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        // Verificăm condiția pentru pop-up-ul de avertizare
        if (rentAmount > rentSuggestion) {
            setShowWarningPopup(true); // Afișează pop-up-ul de avertizare
        } else {
            // Apelăm funcția pentru a trimite chiria propusă
            submitProposedRent();
        }
    };

    //final david

    const [suggestedInstallment, setSuggestedInstallment] = useState<number | null>(null);
    const [calculatedInstallment, setCalculatedInstallment] = useState<number | null>(null);


    const fetchInstallmentSuggestion = async () => {
        const token = localStorage.getItem('token'); // Preluare token din localStorage
        if (!token) {
            alert('Vă rugăm să vă autentificați pentru a continua.');
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/api/rapoarte/sugerseaza-rata', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`, // Adaugă token-ul în header
                },
            });

            if (response.ok) {
                const suggestedInstallment = await response.json(); // Preia valoarea returnată din răspuns
                setSuggestedInstallment(suggestedInstallment); // Salvează valoarea în state
            } else {
                const errorText = await response.text(); // Obține mesajul de eroare
                alert(`A apărut o eroare: ${errorText}`);
            }
        } catch (error) {
            alert('Eroare de rețea. Vă rugăm să verificați conexiunea.');
        }
    };

    const calculateInstallment = async (sum: number, months: number) => {
        try {
            const response = await fetch(
                `http://localhost:8080/api/rapoarte/calculeaza-rata?sumaPropusa=${sum}&luni=${months}`,
                {
                    method: 'GET',
                }
            );

            if (response.ok) {
                const installment = await response.json(); // Obține valoarea ratei
                setCalculatedInstallment(installment); // Salvează rata în state
            } else {
                const errorText = await response.text(); // Obține mesajul de eroare
                alert(`A apărut o eroare: ${errorText}`);
            }
        } catch (error) {
            alert('Eroare de rețea. Vă rugăm să verificați conexiunea.');
        }
    };

    useEffect(() => {
        if (installmentSum && selectedInstallment) {
            calculateInstallment(installmentSum, selectedInstallment);
        }
    }, [installmentSum, selectedInstallment]);

    const handleOpenInstallmentsPopup = () => {
        setShowInstallmentsPopup(true); // Deschide popup-ul
        fetchInstallmentSuggestion();  // Apelează API-ul pentru a sugera rata
    };
    const handleCloseInstallmentsPopup = () => {
        setRecommendedSum(0);
        //setInstallmentSum(0);
        setShowInstallmentsPopup(false);
        setCustomInstallment('');
        setSelectedInstallment(null);
    };

    const handleInstallmentSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        // Valoarea ratei care va fi trimisă la backend
        const rateToSubmit = calculatedInstallment;

        const token = localStorage.getItem('token'); // Preluare token din localStorage
        if (!token) {
            alert('Vă rugăm să vă autentificați pentru a continua.');
            return;
        }

        if (rateToSubmit) {

            try {
                const response = await fetch(`http://localhost:8080/api/rapoarte/adauga-rata?rataPropusa=${calculatedInstallment}`, {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ rataPropusa: rateToSubmit }),
                });

                // Logare informații despre răspuns
                console.log("Status cod:", response.status);  // Vei vedea codul statusului (ex: 200, 400, etc.)
                console.log("Status text:", response.statusText);  // Descrierea statusului (ex: "OK", "Bad Request", etc.)

                if (response.status === 200) {
                    const result = await response.json();
                    console.log("Răspuns complet API:", result);
                    alert(result); // Poți înlocui alert cu un mesaj de succes sau altceva
                } else {
                    const errorText = await response.text();
                    alert(`A apărut o eroare: ${errorText}`);
                }
            } catch (error) {
                alert("Installment added succesful");
            }
        } else {
            alert("Vă rugăm să selectați o rată.");
        }

        handleCloseInstallmentsPopup(); // Închide popup-ul după trimiterea formularului
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        // Logica pentru a trimite datele introduse (de ex: adaugare expense)
        console.log("Expense submitted!");
        handleClosePopup();
    };

    const handleExpenseSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        // Mapăm categoria aleasă din frontend la categoria corespunzătoare din backend
        const mappedCategory = categoryMap[category];

        // Construim obiectul care va fi trimis către back-end
        const expenseData = {
            nume: name, // "nume" trebuie să corespundă cu DTO-ul
            suma: parseFloat(amount.toFixed(2)), // "suma"
            tipCheltuiala: mappedCategory, // Folosim categoria mapată în română
        };

        try {
            const token = localStorage.getItem("token");

            if (!token) {
                setMessage("Autentificarea este necesară.");
                return;
            }

            const serverResponse = await fetch("http://localhost:8080/api/cheltuieli", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(expenseData),
            });

            if (serverResponse.ok) {
                let responseData;
                try {
                    responseData = await serverResponse.text();
                } catch (error) {
                    console.error("Eroare la citirea răspunsului:", error);
                    setMessage("Eroare la procesarea răspunsului.");
                    return;
                }

                console.log("Răspuns server:", responseData);
                setMessage(responseData);

                // Resetăm valorile formularului
                setName("");
                setAmount(0);
                setCategory("");
                handleClosePopup();
            } else {
                const errorText = await serverResponse.text();
                console.error("Eroare la server:", serverResponse.status, errorText);
                setMessage(`Eroare: Cererea nu a fost procesată corect. Status code: ${serverResponse.status}`);
            }
        } catch (error) {
            console.error("Eroare la conectarea cu serverul:", error);
            setMessage("A apărut o eroare. Te rugăm să încerci din nou.");
        }
    };



    const handleExpenseClosePopup = () => {
        setName(""); // Resetează numele
        setAmount(0); // Resetează suma
        setCategory(""); // Resetează categoria
        setShowPopup(false);
    };


    const [triggerReload, setTriggerReload] = useState(false); // Variabilă pentru trigger

    // useEffect pentru a actualiza savings din localStorage
    useEffect(() => {
        const savedSavings = localStorage.getItem("savings");
        if (savedSavings) {
            setSavingsSum(Number(savedSavings)); // Setează savings din localStorage
        }
    }, [triggerReload]);

    // Starea pentru a gestiona vizibilitatea popup-ului de avertizare
    const [showWarningResetPopup, setShowWarningResetPopup] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    // Funcția pentru deschiderea popup-ului de resetare
    const handleOpenWarningResetPopup = () => {
        setShowWarningResetPopup(true);
    };

    // Funcția pentru închiderea popup-ului de resetare
    const handleCloseWarningResetPopup = () => {
        setShowWarningResetPopup(false);
    };

    // Funcția pentru confirmarea resetării diagramei
    const handleConfirmReset = async () => {
        setIsLoading(true); // Activăm starea de încărcare

        const token = localStorage.getItem('token');
        if (!token) {
            alert('Vă rugăm să vă autentificați pentru a continua.');
            setIsLoading(false);
            return;
        }

        try {
            // Apelăm endpoint-ul pentru finalizarea diagramei
            const response = await fetch('http://localhost:8080/api/diagrame/finalizare', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                alert('The diagram has been successfully reset, and the saved money has been added to your savings!');
                setShowWarningResetPopup(false); // Închide popup-ul de avertizare
            } else {
                alert('A apărut o eroare la resetarea diagramei. Vă rugăm să încercați din nou.');
            }
        } catch (error) {
            alert('Eroare de rețea. Vă rugăm să verificați conexiunea.');
        }

        setIsLoading(false); // Deactivăm starea de încărcare
    };

    // Funcția pentru anularea resetării diagramei
    const handleCancelReset = () => {
        setShowWarningResetPopup(false); // Închide popup-ul de avertizare fără a face nimic
    };

    useEffect(() => {
        const isComplete = JSON.parse(localStorage.getItem('isProfileComplete') || 'false');
        setIsProfileComplete(isComplete); // Actualizează starea locală cu valoarea citită din localStorage
    }, []);



    return (

        <div className='home-page'>
            {message && (
                <div className="overlay" onClick={closeMessage}>
                    <div className="success-message">{message}</div>
                </div>
            )}
            {/* Bara de navigare */}
            <header className='navbar'>
                <Link to="/info-page">
                    <img src={logo} alt="Logo" className="logo-image" />
                </Link>
                <nav className='nav-links'>
                    <Link to='/homepage' className="active">Home</Link> {/* Link către welcome page */}
                    <Link to='/dashboard'>History</Link>
                    <Link to='/financial-profile' className={`nav-link ${!isProfileComplete ? 'pulse-button' : ''}`}>Financial Profile</Link>
                    <Link to='/help'>Help</Link>
                    <Link to='/about-us'>About Us</Link>
                </nav>
                <Link to='/my-account' className='profile-link'>
                    <img src={profile_pic} alt="Profile" className="profile-image" />
                </Link>
            </header>

            {/* Meniu lateral */}
            <aside className='sidebar'>
                <button onClick={handleOpenRentPopup}>Generate Rent Budget Report</button>
                <button onClick={handleOpenInstallmentsPopup}>Generate Installments Report</button>
                <button onClick={handleOpenSubscriptionPopup}>Generate Subscription Report</button>
                <button onClick={handleOpenSavingsPopup}>Generate Savings Report</button>
                <button onClick={handleOpenHolidayPopup}>Generate Holiday Report</button>
                <button onClick={handleOpenWarningResetPopup}>Reset Diagram(Admin property)</button>
                {/*<button className='add-button'>+</button>*/}
                <div className='savings'>
                    <span>Savings:</span>
                    <span>{savingsSum}</span>
                </div>
            </aside>

            {/* Popup-ul de confirmare pentru reset diagram */}
            {showWarningResetPopup && (
                <div className="popup-overlay">
                    <div className="reset-popup">
                        <h2>Are you sure you want to reset the diagram?</h2>
                        <p>All the money that you have not spent will be added to your savings.</p>

                        <div className="reset-popup-buttons">
                            <button onClick={handleConfirmReset} disabled={isLoading}>
                                {isLoading ? 'Processing...' : 'Yes'}
                            </button>
                            <button onClick={handleCancelReset} disabled={isLoading}>
                                No
                            </button>
                        </div>

                    </div>
                    <button className="x-button" onClick={handleCloseWarningResetPopup}>×</button>
                </div>
            )}


            {/* Conținut principal */}
            <main className='main-contents'>
                <div className='diagram'>
                    {/*Quick fix se poate sterge daca nu merge*/}
                    <PieChart onCategoriesFetched={function (categories: string[]): void {
                        throw new Error('Function not implemented.');
                    }} updateTrigger={0} />
                </div>
                <div className='expense-button'>
                    <button onClick={handleOpenPopup}>+ Expense</button>
                </div>
            </main>

            {/* Pop-up pentru Cheltuieli */}
            {showPopup && (
                <div className='popup-overlay' onClick={handleClosePopup}>
                    <div className='popup-container' onClick={(e) => e.stopPropagation()}>
                        <h2>Expense</h2>
                        <form onSubmit={handleExpenseSubmit}>
                            <div className='form-group'>
                                <label htmlFor='name'>Name:</label>
                                <input type='text' id='name' name='name' value={name} onChange={(e) => setName(e.target.value)} placeholder="Enter name" required />
                            </div>
                            <div className='form-group'>
                                <label htmlFor='amount'>Amount:</label>
                                <input type='number' id='amount' name='amount' value={amount} onChange={(e) => setAmount(parseFloat(e.target.value))} min="0" step="any" required />
                            </div>
                            <div className='form-group'>
                                <label htmlFor='category'>Category:</label>
                                <select
                                    id='category'
                                    name='category'
                                    value={category}
                                    onChange={(e) => setCategory(e.target.value)}
                                    required
                                    className="category-select"
                                >
                                    <option value="" disabled>Select a category</option>
                                    {memorizedCategories.map((cat) => (
                                        <option key={cat} value={cat}> {
                                            cat.charAt(0).toUpperCase() + cat.slice(1)
                                        }
                                        </option>
                                    ))}
                                </select>
                            </div>

                            <div className='parent-container'>
                                <button type='submit' className='submit-sub-button'>Add</button>
                            </div>
                        </form>
                        <button className="x-button" onClick={handleClosePopup}>×</button>
                    </div>
                </div>
            )}



            {/* Pop-up pentru Rent Budget */}
            {showRentPopup && (
                <div className='popup-overlay' onClick={handleCloseRentPopup}>
                    <div className='popup-container' onClick={(e) => e.stopPropagation()}>
                        <h2>Rent Budget Report</h2>
                        <form onSubmit={handleRentSubmit}>
                            <div className='budget-box'>
                                {rentSuggestion ? `Maximum suggested: ${rentSuggestion}` : 'Loading suggested rent...'}
                            </div>
                            <p>Please insert the amount and consider our suggestion:</p>
                            <input
                                type="number"
                                value={rentAmount}
                                onChange={(e) => setRentAmount(parseFloat(e.target.value))}
                                min="0"
                                step="any"
                                required
                            />
                            <div className='form-actions'>
                                <button type="submit">Submit</button>
                            </div>
                        </form>
                        <button className="x-button" onClick={handleCloseRentPopup}>×</button>
                    </div>
                </div>
            )}

            {/* Pop-up pentru avertizare */}
            {showWarningPopup && (
                <div className='popup-overlay' onClick={() => handleCloseWarningPopup(false)}>
                    <div className='popup-container' onClick={(e) => e.stopPropagation()}>
                        <p>The amount you introduced is not in the budget range. Want to continue?</p>
                        <div className="popup-actions">
                            <button className="submit-sub-button" onClick={() => handleCloseWarningPopup(false)}>No</button>
                            <button
                                className="submit-sub-button"
                                onClick={() => {
                                    handleCloseWarningPopup(true); // Apelăm funcția corect pentru a confirma
                                }}
                            >
                                Yes
                            </button>
                        </div>
                    </div>
                </div>
            )}


            {showHolidayPopup && (
                <div className='popup-overlay-holiday' onClick={handleCloseHolidayPopup}>
                    <div className='popup-container-holiday' onClick={(e) => e.stopPropagation()}>
                        <h2>Holiday Report</h2>
                        <form onSubmit={handleHolidaySubmit}>
                            <div className='form-group-holiday'>
                                <label htmlFor='holidayDays'>Insert number of days:</label>
                                <input
                                    type="number"
                                    id="holidayDays"
                                    name="holidayDays"
                                    value={holidayDays}
                                    onChange={(e) => setHolidayDays(parseFloat(e.target.value))}
                                    min="0"
                                    step="any"
                                    required
                                />
                            </div>
                            <div className='form-group-holiday'>
                                <label htmlFor='holidaySum'>Insert sum for holiday:</label>
                                <input
                                    type="number"
                                    id="holidaySum"
                                    name="holidaySum"
                                    value={holidaySum}
                                    onChange={(e) => setHolidaySum(parseFloat(e.target.value))}
                                    min="0"
                                    step="any"
                                    required
                                />
                            </div>
                            {/* Recomandare pentru sumă travel */}
                            <div className="recommended-sum-holiday">
                                <label>Recommended sum for travel (round trip):</label>
                                <div className="recommended-value-holiday">{recommendedTravelSum}</div>
                            </div>
                            {/* Recomandare pentru sumă cazare */}
                            <div className="recommended-sum-holiday">
                                <label>Recommended sum for accommodation ($/day):</label>
                                <div className="recommended-value-holiday">{recommendedAccommodationSum}</div>
                            </div>
                            <div className='form-actions'>
                                <button type="submit">Submit</button>
                            </div>
                        </form>
                        <button className="x-button" onClick={handleCloseHolidayPopup}>×</button>
                    </div>
                </div>
            )}

            {/* Pop-up pentru Installments Report */}
            {showInstallmentsPopup && (
                <div className="popup-overlay" onClick={handleCloseInstallmentsPopup}>
                    <div className="popup-container installments-popup" onClick={(e) => e.stopPropagation()}>
                        <h2>Installments Report</h2>
                        <form onSubmit={handleInstallmentSubmit}>
                            <div className="installment-sum">
                                <input type='number' placeholder='Total: ' id='installment' name='installment' value={installmentSum} onChange={(e) => setInstallmentSum(parseFloat(e.target.value))} min="0" step="10" required />
                            </div>

                            <div className="info-box">Maximum suggested: {suggestedInstallment}</div>
                            <p>Please choose in how many months you want to pay the installment:</p>
                            <div className="installment-options">
                                {installmentOptions.map((option) => (
                                    <button
                                        type="button"
                                        key={option}
                                        value={installmentSum}
                                        className={`installment-option ${selectedInstallment === option ? 'active' : ''}`}
                                        onClick={() => {
                                            setSelectedInstallment(option);
                                            setCustomInstallment('');
                                        }}
                                    >
                                        {option}
                                    </button>
                                ))}
                                <div onClick={() => setSelectedInstallment(null)}>
                                    <input className='custom-option'
                                        type="number"
                                        placeholder="Insert a different option"
                                        value={customInstallment}
                                        onChange={(e) => {
                                            const customValue = parseInt(e.target.value, 10) || 0; // Parsează valoarea ca număr
                                            if (customValue > 0) {
                                                setCustomInstallment(customValue); // Actualizează valoarea custom
                                                setSelectedInstallment(customValue); // Actualizează starea selectedInstallment
                                            } else {
                                                setCustomInstallment('');
                                                setSelectedInstallment(null); // Resetează dacă valoarea nu este validă
                                            }
                                        }}
                                        min="1"
                                    />
                                </div>
                            </div>
                            <div className="recommended-sum">Monthly sum: {calculatedInstallment}</div>
                            <div className='form-actions'>
                                <button type="submit">Submit</button>
                            </div>
                        </form>
                        <button className="x-button" onClick={handleCloseInstallmentsPopup}>×</button>
                    </div>
                </div>
            )}

            {showSavingsPopup && (
                <div className="popup-overlay" onClick={handleCloseSavingsPopup}>
                    <div
                        className="popup-container-savings"
                        onClick={(e) => e.stopPropagation()}
                    >
                        <h2>Report - Savings</h2>

                        {/* Canvas Area */}
                        <div className="canvas-container">
                            <canvas
                                ref={canvasRef}
                                width="350"
                                height="300"
                                style={{ border: "1px solid black" }}
                            ></canvas>
                        </div>

                        {/* Instruction Text */}
                        <div className="select-months-display">
                            Select the amount of months:
                        </div>

                        {/* Month Selector */}
                        <div className="installment-options">
                            {[3, 6, 12, 24, 60, "All"].map((months) => (
                                <button
                                    key={months}
                                    className="month-option"
                                    onClick={() =>
                                        handleMonthSelection(months === "All" ? "" : String(months))
                                    }
                                >
                                    {months}
                                </button>
                            ))}
                        </div>

                        {/* Close Button */}
                        <button className="x-button" onClick={handleCloseSavingsPopup}>×</button>

                        {/* Percentage Display */}
                        <div className="percentage-display">
                            Your savings have changed by: <strong>15%</strong>{" "}
                            {selectedMonths && `in the last ${selectedMonths} months`}
                        </div>
                    </div>
                </div>
            )}



            {showSubscriptionPopup && (
                <div className="popup-overlay" onClick={handleCloseSubscriptionPopup}>
                    <div className="popup-container-subscription" onClick={(e) => e.stopPropagation()}>
                        <h2>Subscription Report</h2>
                        <div className="subscription-list">
                            {subscriptions.length === 0 ? (
                                <p>No subscriptions added yet.</p>
                            ) : (
                                subscriptions.map((subscription) => (
                                    <div key={subscription.id} className="subscription-item">
                                        <span>
                                            <strong>{subscription.nume}</strong> - {subscription.valoare}€ / {subscription.tipAbonament}
                                        </span>
                                        <p>
                                            Payment Due:{" "}
                                            {subscription.tipAbonament === "Lunar"
                                                ? `Day ${subscription.ziua}`
                                                : `Day ${subscription.ziua}, Month ${subscription.luna}`}
                                        </p>
                                        <button
                                            onClick={() => handleDeleteSubscription(subscription.id)}  // Trimiterea ID-ului
                                            className="delete-sub-button"
                                        >
                                            Delete
                                        </button>
                                    </div>
                                ))
                            )}
                        </div>
                        <button onClick={handleOpenAddSubscriptionPopup} className="submit-sub-button">
                            Add Subscription
                        </button>
                        <button className="x-button" onClick={handleCloseSubscriptionPopup}>×</button>
                    </div>
                </div>
            )}

            {showAddSubscriptionPopup && (
                <div className="popup-overlay" onClick={handleCloseAddSubscriptionPopup}>
                    <div className="popup-container-add-sub" onClick={(e) => e.stopPropagation()}>
                        <h2>Add Subscription</h2>
                        <form onSubmit={handleAddSubscription} className="add-subscription-form">
                            <div className="form-group">
                                <input
                                    type="text"
                                    value={newSubscriptionName}
                                    onChange={(e) => setNewSubscriptionName(e.target.value)}
                                    placeholder="Subscription Name"
                                    required
                                />
                            </div>
                            <div className="yearmonth-options">
                                <button
                                    type="button"
                                    onClick={() => setPaymentFrequency("monthly")}
                                    className={`payment-frequency-btn ${paymentFrequency === "monthly" ? "active" : ""}`}
                                >
                                    Monthly
                                </button>
                                <button
                                    type="button"
                                    onClick={() => setPaymentFrequency("yearly")}
                                    className={`payment-frequency-btn ${paymentFrequency === "yearly" ? "active" : ""}`}
                                >
                                    Yearly
                                </button>
                            </div>
                            <div className="form-group">
                                <input
                                    type="number"
                                    value={newSubscriptionPrice}
                                    onChange={(e) => setNewSubscriptionPrice(e.target.value)}
                                    placeholder={`Subscription Value (€/${paymentFrequency === "monthly" ? "month" : "year"})`}
                                    required
                                />
                            </div>
                            <div className="form-group">
                                <input
                                    type="number"
                                    value={paymentDay}
                                    onChange={(e) => setPaymentDay(parseInt(e.target.value, 10))}
                                    placeholder="Day"
                                    required
                                />
                            </div>
                            {paymentFrequency === "yearly" && (
                                <div className="form-group">
                                    <input
                                        type="number"
                                        value={paymentMonth}
                                        onChange={(e) => setPaymentMonth(parseInt(e.target.value, 10))}
                                        placeholder="Month"
                                        required
                                    />
                                </div>
                            )}
                            <button type="submit" className="submit-sub-button">
                                Add Subscription
                            </button>
                        </form>
                        <button className="x-button" onClick={handleCloseAddSubscriptionPopup}>×</button>
                    </div>
                </div>
            )}

        </div>
    );
};
export default HomePage