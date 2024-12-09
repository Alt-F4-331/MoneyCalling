/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useEffect, useRef, useState } from 'react';
import './HomePage.css';
import logo from '../assets/logo.png';
import profile_pic from "../assets/profile_pic.jpg";
import PieChart from './PieChart';
import { Link } from 'react-router-dom';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';





const HomePage: React.FC = () => {

  const [showRentPopup, setShowRentPopup] = useState(false);
  const [showWarningPopup, setShowWarningPopup] = useState(false);
  const [rentAmount, setRentAmount] = useState<number>(0);
  const [savingsSum, setSavingsSum] = useState<number>(0);
  const [installmentSum, setInstallmentSum] = useState<number>(0);
  const [recommendedSum, setRecommendedSum] = useState<number>(0);
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [budgetRange, setBudgetRange] = useState({ min: 0, max: 10000 }); // valorile date sunt un exemplu; urmeaza sa fie legat cu backend-ul
  const [amount, setAmount] = useState<number>(0); // State pentru Amount
  const [category, setCategory] = useState<string>(""); // State pentru Category; va trebui sa faca parte din lista de categorii din baza de date
  const [showPopup, setShowPopup] = useState(false);
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
  const [newSubscriptionName, setNewSubscriptionName] = useState('');
  const [newSubscriptionPrice, setNewSubscriptionPrice] = useState('');
  const [paymentFrequency, setPaymentFrequency] = useState<'monthly' | 'yearly'>('monthly');
  const [paymentDueDate, setPaymentDueDate] = useState('');
  const [paymentMonth, setPaymentMonth] = useState('');
  const [paymentDay, setPaymentDay] = useState('');
  const [subscriptions, setSubscriptions] = useState<{ name: string, price: number, frequency: string, paymentDueDate: string }[]>([]);
  const [paymentMonthDayInput, setPaymentMonthDayInput] = useState('');  // State to handle the input value


  const [showSavingsPopup, setShowSavingsPopup] = useState(false);
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const [selectedMonths, setSelectedMonths] = useState('');

  const [name, setName] = useState<string>("");


  const handleOpenSavingsPopup = () => {
    setShowSavingsPopup(true);
  }

  const handleCloseSavingsPopup = () => {
    setShowSavingsPopup(false);
  };

  const handleMonthSelection = (months: string) => {
    setSelectedMonths(months); // Update the selected months
  };


  useEffect(() => {
    if (showSavingsPopup && canvasRef.current) {
      const ctx = canvasRef.current.getContext('2d');
      if (ctx) {
        // Dummy data for chart (X: months, Y: savings)
        const selectedMonths = 3;
        const data = {
          months: Array.from({ length: selectedMonths }, (_, i) => i - Math.floor(selectedMonths / 2)), // To include negative X values
          savings: Array.from({ length: selectedMonths }, () => Math.floor(Math.random() * 2000) - 1000), // Random savings, including negative values
        };

        // Draw the chart on canvas
        drawChart(ctx, data);
      }
    }
  }, [showSavingsPopup]);

  const drawChart = (ctx: CanvasRenderingContext2D, data: { months: number[]; savings: number[] }) => {
    const canvasWidth = ctx.canvas.width;
    const canvasHeight = ctx.canvas.height;

    // Clear the previous canvas
    ctx.clearRect(0, 0, canvasWidth, canvasHeight);

    // Set the margins and center origin
    const margin = 20;
    const centerX = canvasWidth / 2;
    const centerY = canvasHeight / 2;

    // Adjusting scale
    const xMin = Math.min(...data.months);
    const xMax = Math.max(...data.months);
    const yMin = Math.min(...data.savings);
    const yMax = Math.max(...data.savings);

    const xRange = xMax - xMin;
    const yRange = yMax - yMin;

    const xScale = (canvasWidth - 2 * margin) / xRange;
    const yScale = (canvasHeight - 2 * margin) / yRange;

    // Draw X and Y axis
    ctx.beginPath();
    ctx.moveTo(margin, centerY); // X-axis start
    ctx.lineTo(canvasWidth - margin, centerY); // X-axis end
    ctx.moveTo(centerX, margin); // Y-axis start
    ctx.lineTo(centerX, canvasHeight - margin); // Y-axis end
    ctx.strokeStyle = '#fff'; // White color for the axes
    ctx.lineWidth = 2;
    ctx.stroke();

    // Draw arrows at the end of the axes
    drawArrow(ctx, canvasWidth - margin, centerY, 10, 0); // X-axis positive arrow
    drawArrow(ctx, centerX, margin, 0, -10); // Y-axis positive arrow (pointing up)

  

 
    ctx.strokeStyle = 'white'; // White line for the graph
    ctx.lineWidth = 2;
    ctx.stroke();
  };

  // Function to draw an arrow at the end of an axis
  const drawArrow = (
    ctx: CanvasRenderingContext2D,
    startX: number,
    startY: number,
    angleX: number,
    angleY: number
  ) => {
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



  
  const handleOpenSubscriptionPopup = () => {
    setShowSubscriptionPopup(true);
  };

  const handleCloseSubscriptionPopup = () => {
    setShowSubscriptionPopup(false);
  };




  const handleAddSubscription = (e: React.FormEvent) => {
    e.preventDefault();

    // Prepare the subscription data based on the form state
    const newSubscription = {
      name: newSubscriptionName,
      price: parseFloat(newSubscriptionPrice),
      frequency: paymentFrequency,
      paymentDueDate: paymentFrequency === 'monthly' ? paymentDueDate : `${paymentMonth} ${paymentDay}`,
    };

    // Add the new subscription to the subscriptions array
    setSubscriptions([...subscriptions, newSubscription]);

    // Reset form fields after adding
    setNewSubscriptionName('');
    setNewSubscriptionPrice('');
    setPaymentDueDate('');
    setPaymentMonth('');
    setPaymentDay('');
  };

 


  // Handle deleting a subscription
  const handleDeleteSubscription = (index: number) => {
    const updatedSubscriptions = subscriptions.filter((_, i) => i !== index);
    setSubscriptions(updatedSubscriptions);
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

  const handleHolidaySubmit = (e: React.FormEvent) => {
    e.preventDefault();
    //alert("Holiday Report generated successfully!");
    setShowHolidayPopup(false);
  };

  // Deschidere și închidere popups
  const handleOpenRentPopup = () => {
    setShowRentPopup(true);
  };

  const handleCloseRentPopup = () => {
    setShowRentPopup(false);
    setRentAmount(0); // Resetare sumă
  };

  const handleCloseWarningPopup = () => {
    setShowWarningPopup(false);
  };

  const handleRentSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (rentAmount < budgetRange.min || rentAmount > budgetRange.max) {
      setShowWarningPopup(true);
    } else {
      //alert("Rent amount is within the budget range!");
      setShowRentPopup(false);
    }
  };

  const handleOpenPopup = () => {
    setShowPopup(true);
  };

  const handleClosePopup = () => {
    setShowPopup(false);
  };

  const handleOpenInstallmentsPopup = () => setShowInstallmentsPopup(true);
  const handleCloseInstallmentsPopup = () => {
    setRecommendedSum(0);
    //setInstallmentSum(0);
    setShowInstallmentsPopup(false);
    setCustomInstallment('');
    setSelectedInstallment(null);
  };

  const handleInstallmentSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log(`Selected Installment: ${selectedInstallment || customInstallment}`);
    handleCloseInstallmentsPopup();
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Logica pentru a trimite datele introduse (de ex: adaugare expense)
    console.log("Expense submitted!");
    handleClosePopup();
  };

  const handleExpenseSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Expense submitted!");
    console.log(`Name: ${name}, Amount: ${amount}, Category: ${category}`);
    handleClosePopup();
  };


  const handleExpenseClosePopup = () => {
    setName(""); // Resetează numele
    setAmount(0); // Resetează suma
    setCategory(""); // Resetează categoria
    setShowPopup(false);
  };

  return (
    <div className='home-page'>
      {/* Bara de navigare */}
      <header className='navbar'>
        <Link to="/info-page">
          <img src={logo} alt="Logo" className="logo-image" />
        </Link>
        <nav className='nav-links'>
          <Link to='/homepage' className="active">Home</Link> {/* Link către welcome page */}
          <Link to='/dashboard'>History</Link>
          <Link to='/financial-profile'>Financial Profile</Link>
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
        {/*<button className='add-button'>+</button>*/}
        <div className='savings'>
          <span>Savings:</span>
          <span>{savingsSum}</span>
        </div>
      </aside>

      {/* Conținut principal */}
      <main className='main-contents'>
        <div className='diagram'>
          <PieChart />
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
                  <option value="locuinta">Rent</option>
                  <option value="transport">Transport</option>
                  <option value="alimentatie">Food</option>
                  <option value="sanatate">Health</option>
                  <option value="imbracaminte">Clothing</option>
                  <option value="divertisment">Entertainment</option>
                  <option value="educatie">Education</option>
                  <option value="economii">Savings</option>
                </select>
              </div>
              <div className='form-actions'>
                <button type='submit'>Add</button>
                <button type='button' onClick={handleClosePopup}>Cancel</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Pop-up pentru Rent Budget */}
      {showRentPopup && (
        <div className='popup-overlay' onClick={handleCloseRentPopup}>
          <div className='popup-container' onClick={(e) => e.stopPropagation()}>
            <h2>Rent Budget Report</h2>
            <form onSubmit={handleRentSubmit}>
              <div className='budget-box'>Budget range: {budgetRange.min} - {budgetRange.max}</div>
              <p>If you already are paying rent, please insert the amount:</p>
              <input
                type="number"
                value={rentAmount}
                onChange={(e) => setRentAmount(parseFloat(e.target.value))}
                min="0"
                step="any"
                required
              />
              <button type="submit">Submit</button>
            </form>
          </div>
        </div>
      )}

      {/* Pop-up pentru avertizare */}
      {showWarningPopup && (
        <div className='popup-overlay' onClick={handleCloseWarningPopup}>
          <div className='popup-container' onClick={(e) => e.stopPropagation()}>
            <p>The amount you introduced is not in the budget range. Want to continue?</p>
            <div className="popup-actions">
              <button onClick={handleCloseWarningPopup}>No</button>
              <button onClick={() => { setShowWarningPopup(false); setShowRentPopup(false); }}>Yes</button>
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
                <label>Recommended sum for travel:</label>
                <div className="recommended-value-holiday">{recommendedTravelSum}</div>
              </div>
              {/* Recomandare pentru sumă cazare */}
              <div className="recommended-sum-holiday">
                <label>Recommended sum for accommodation:</label>
                <div className="recommended-value-holiday">{recommendedAccommodationSum}</div>
              </div>
              <div className='form-actions'>
                <button type="submit">Submit</button>
              </div>
            </form>
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

              <div className="info-box">Budget range: {budgetRange.min} - {budgetRange.max}</div>
              <p>Please choose one of the following payment options:</p>
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
                      setCustomInstallment(parseInt(e.target.value) || 0);
                      //setSelectedInstallment(null);
                    }}
                    min="1"
                  />
                </div>
              </div>
              <div className="recommended-sum">Recommended sum: {recommendedSum}</div>
               <button type="submit" className="submit-button">Submit</button>
              
            </form>
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

            {/* Percentage Display */}
            <div className="percentage-display">
              Your savings have changed by: <strong>15%</strong>{" "}
              {selectedMonths && `in the last ${selectedMonths} months`}
            </div>

            {/* Close Button */}
            <div className="parent-container">
              <button
                className="month-close"
                onClick={handleCloseSavingsPopup}
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}


{showSubscriptionPopup && (
  <div className="popup-overlay" onClick={handleCloseSubscriptionPopup}>
    <div className="popup-container-subscription" onClick={(e) => e.stopPropagation()}>
      <h2>Subscription Report</h2>

      <form onSubmit={handleAddSubscription} className="add-subscription-form">
        <div className="form-group">
          <input
            type="text"
            id="serviceName"
            value={newSubscriptionName}
            onChange={(e) => setNewSubscriptionName(e.target.value)}
            placeholder="Subscription Name"
            required
          />
        </div>

        <div className="yearmonth-options">
          <button
            type="button"
            onClick={() => setPaymentFrequency('monthly')}
            className={`payment-frequency-btn ${paymentFrequency === 'monthly' ? 'active' : ''}`}
          >
            Monthly
          </button>
          <button
            type="button"
            onClick={() => setPaymentFrequency('yearly')}
            className={`payment-frequency-btn ${paymentFrequency === 'yearly' ? 'active' : ''}`}
          >
            Yearly
          </button>
        </div>

        {paymentFrequency === 'monthly' && (
          <>
            <div className="form-group">
              <input
                type="number"
                id="servicePriceMonthly"
                value={newSubscriptionPrice}
                onChange={(e) => setNewSubscriptionPrice(e.target.value)}
                placeholder="Subscription Value (€/month)"
                required
              />
            </div>
            <div className="form-group">
              <input
                type="text"
                id="paymentDueDate"
                value={paymentDueDate}
                onChange={(e) => setPaymentDueDate(e.target.value)}
                placeholder="(DD)"
                required
              />
            </div>
          </>
        )}

        {paymentFrequency === 'yearly' && (
          <>
            <div className="form-group">
              <input
                type="number"
                id="servicePriceYearly"
                value={newSubscriptionPrice}
                onChange={(e) => setNewSubscriptionPrice(e.target.value)}
                placeholder="Subscription Value (€/year)"
                required
              />
            </div>
            <div className="form-group">
              <input
                type="text"
                id="paymentMonthDay"
                value={paymentMonthDayInput} // Using the local state to display the value
                onChange={(e) => {
                  const value = e.target.value;
                  // Only update month and day if the input is not empty
                  if (value) {
                    const [day, month] = value.split('/');
                    if (day && month) {
                      setPaymentMonth(day);  // Set day as month
                      setPaymentDay(month);  // Set month as day
                    }
                  } else {
                    setPaymentMonth('');  // Reset month if input is cleared
                    setPaymentDay('');    // Reset day if input is cleared
                  }
                  setPaymentMonthDayInput(value);  // Update the input value
                }}
                placeholder="DD/MM"  // Placeholder text
                pattern="\d{2}/\d{2}"  // Enforces DD/MM format
                required
              />
            </div>
          </>
        )}
        <div className='parent-container'>
        <button type="submit" className="submit-sub-button">Add Subscription</button>
        </div>
        
      </form>

      <div className="subscription-list">
        {subscriptions.length === 0 ? (
          <p>No subscriptions added yet.</p>
        ) : (
          subscriptions.map((subscription, index) => (
            <div key={index} className="subscription-item">
              <span>
                <strong>{subscription.name}</strong> - {subscription.price}€ / {subscription.frequency}
              </span>
              <p>
                {subscription.frequency === 'monthly'
                  ? `Payment Due Day: ${subscription.paymentDueDate}th`
                  : `Payment Due Day: ${subscription.paymentDueDate.split(' ').join('/')}`}
              </p>
              <button onClick={() => handleDeleteSubscription(index)} className="delete-sub-button">
                <FontAwesomeIcon icon={faTrash} />
              </button>
            </div>
          ))
        )}
      </div>

      <div className="subscription-actions">
        <button className="close-sub-button" onClick={handleCloseSubscriptionPopup}>Close</button>
      </div>
    </div>
  </div>
)}




    </div>
  );
};

export default HomePage;
