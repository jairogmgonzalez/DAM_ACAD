import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Switch from '@mui/material/Switch';

function App() {
  const [count, setCount] = useState(0);
  const increment = () => setCount((count) => count + 1);
  const decrement = () => setCount((count) => count - 1);
  const reset = () => setCount((count) => count = 0);

  const [inputValue, setInputValue] = useState('');
  const [greeting, setGreeting] = useState('Hola');

  const handleSwitch = () => {
    setGreeting(greeting === 'Hola' ? 'Adiós' : 'Hola');
  };

  return (
    <div>
      <div>
        <h2>Contador</h2>
        <p>Valor del contador: {count}</p>
        <button onClick={increment}>Incrementar</button>
        <button onClick={reset}>Restablecer</button>
        <button onClick={decrement}>Decrementar</button>
        <br />
      </div>

      <div>
        <h2>Input</h2>
        <p>Valor del input: {inputValue}</p>
        <input 
          type="text" 
          value={inputValue} 
          onChange={(e) => setInputValue(e.target.value)} 
        />
      </div>

      <div>
        <h2>{greeting}</h2>
        <Switch onChange={handleSwitch} />
      </div>
    </div>
  );
}

export default App;