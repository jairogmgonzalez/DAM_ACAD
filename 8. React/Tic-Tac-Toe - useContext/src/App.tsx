import React from 'react';
import { GameProvider } from './context/GameContext';
import Board from './components/Board';

function App() {
    return (
        <GameProvider>
            <Board />
        </GameProvider>
    );
}

export default App;
