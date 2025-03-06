import { ReactNode } from 'react';
import { useState, useEffect, createContext } from 'react';

export type GameContextType = {
    squares: (string | null)[];
    winner: string | null;
    turn: "X" | "O";
    isRunning: boolean;
    handleClick: (index: number) => void;
    startGame: () => void;
};

export const GameContext = createContext<GameContextType | undefined>(undefined);

export function GameProvider({ children }: { children: ReactNode }) {
    
    const [squares, setSquares] = useState<(string | null)[]>(Array(9).fill(null));
    const [winner, setWinner] = useState<string | null>(null);
    const [turn, setTurn] = useState<"X" | "O">("X");
    const [isRunning, setRunning] = useState(false);

    const handleClick = (index: number) => {
        if (!isRunning || squares[index]) {
            return;
        }

        const newSquares = [...squares];
        newSquares[index] = turn;
        setSquares(newSquares);
    }

    const checkWinner = (squaresToCheck: (string | null)[])  => {
        const lines = [
            [0, 1, 2],
            [3, 4, 5],
            [6, 7, 8],
            [0, 3, 6],
            [1, 4, 7],
            [2, 5, 8],
            [0, 4, 8],
            [2, 4, 6],
        ];

        for (const [a, b, c] of lines) {
            if (squaresToCheck[a] && squaresToCheck[a] === squaresToCheck[b] && squaresToCheck[a] === squaresToCheck[c]) {
                return squaresToCheck[a];
            }
        }
        return null;
    }

    const startGame = () => {
        setSquares(Array(9).fill(null));
        setWinner(null);
        setTurn("X");
        setRunning(true);
    }

    useEffect(() => {
        const result = checkWinner(squares);
        if (result) {
            setWinner(result === "X" ? "Jugador 1" : "Jugador 2");
            setRunning(false);
        } else {
            setTurn(turn === "X" ? "O" : "X");
        }
    }, [squares]);


    return (
        <GameContext.Provider value={{ squares, winner, turn, isRunning, handleClick, startGame }}>
            {children}
        </GameContext.Provider>
    );
    
}

