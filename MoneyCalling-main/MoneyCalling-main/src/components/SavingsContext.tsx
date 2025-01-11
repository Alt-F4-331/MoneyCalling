import React, { createContext, useContext, useState } from 'react';

interface SavingsContextType {
  savings: number;
  setSavings: React.Dispatch<React.SetStateAction<number>>;
}

const SavingsContext = createContext<SavingsContextType | undefined>(undefined);

export const SavingsProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [savings, setSavings] = useState(0);

  return (
    <SavingsContext.Provider value={{ savings, setSavings }}>
      {children}
    </SavingsContext.Provider>
  );
};

export const useSavings = () => {
  const context = useContext(SavingsContext);
  if (!context) {
    throw new Error('useSavings must be used within a SavingsProvider');
  }
  return context;
};
