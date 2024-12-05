import React, {useState} from 'react';
import './HomePage.css';
import logo from "/public/logo.png";
import profile_pic from "../assets/profile_pic.jpg";
import PieChart from './PieChart';
import { Link } from 'react-router-dom';

const HomePage: React.FC = () => {

  const [showRentPopup, setShowRentPopup] = useState(false);
  const [showWarningPopup, setShowWarningPopup] = useState(false);
  const [rentAmount, setRentAmount] = useState<number>(0);
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
        <button>Generate Subscription Report</button>
        <button>Generate Savings Report</button>
        <button onClick={handleOpenHolidayPopup}>Generate Holiday Report</button>
        <button className='add-button'>+</button>
        <div className='savings'>
          <span>Savings:</span>
          <span>0</span>
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
            <form onSubmit={handleSubmit}>
              <div className='form-group'>
                <label htmlFor='amount'>Amount:</label>
                <input type='number' id='amount' name='amount' value={amount} onChange={(e) => setAmount(parseInt(e.target.value,10))} min="0" step="10" required />
              </div>
              <div className='form-group'>
                <label htmlFor='category'>Category:</label>
                <input type='text' id='category' name='category' value={category}
                onChange={(e) => setCategory(e.target.value)} required />
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
                onChange={(e) => setRentAmount(parseInt(e.target.value, 10))}
                min="0"
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
            onChange={(e) => setHolidayDays(parseInt(e.target.value, 10))}
            min="0"
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
            onChange={(e) => setHolidaySum(parseInt(e.target.value, 10))}
            min="0"
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
        <input type='number' placeholder='Total: ' id='installment' name='installment' value={installmentSum} onChange={(e) => setInstallmentSum(parseInt(e.target.value,10))} min="0" step="10" required />
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
          <div onClick={()=>setSelectedInstallment(null)}>
            <input className='custom-option'
              type="number"
              placeholder="Insert a different option"
              value={customInstallment}
              onChange={(e) => {
                setCustomInstallment(parseInt(e.target.value, 10) || '');
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

      
    </div>
  );
};

export default HomePage;
