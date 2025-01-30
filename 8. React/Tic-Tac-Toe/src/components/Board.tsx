import { useEffect, useState } from 'react';
import Grid from '@mui/material/Grid';
import Square from './Square';
import Button from "@mui/material/Button";
import Typography from '@mui/material/Typography';
import { Box, Paper } from '@mui/material';

function Board() {
    const [squares, setSquares] = useState(Array(9).fill(null));
    const [winner, setWinner] = useState<string | null>(null);
    const [turn, setTurn] = useState<"X" | "O">("X");
    const [isRunning, setRunning] = useState(false);

    const checkWinner = (squares: (string | null)[]) => {
        // Lineas ganadoras
        const lines = [
            [0, 1, 2], // Fila 1
            [3, 4, 5], // Fila 2
            [6, 7, 8], // Fila 3
            [0, 3, 6], // Columna 1
            [1, 4, 7], // Columna 2
            [2, 5, 8], // Columna 3
            [0, 4, 8], // Diagonal principalç
            [2, 4, 6], // Diagonal secundaria
        ];

        // Recorre cada línea y verifica si hay un ganador
        for (let line of lines) {
            const [a, b, c] = line;
            if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
                return squares[a];
            }
        }

        return null;
    }

    // Verifica si hay un ganador
    useEffect(() => {
        const result = checkWinner(squares);
        if (result) {
            setWinner(result === "X" ? "Jugador 1" : "Jugador 2");
            setRunning(false);
        }
    }, [squares])

    // Función para manejar un turno entero
    const handleClick = (index: number) => {
        // Verifica si hay una partida activa
        if (!isRunning) {
            return;
        }

        // Verifica si la casilla ya está ocupada
        if (squares[index]) {
            return;
        }

        // Actualiza el estado de squares con el símbolo del turno actual
        const newSquares = [...squares];
        newSquares[index] = turn;
        setSquares(newSquares);

        // Cambia de turno
        setTurn(turn === 'X' ? 'O' : 'X');
    }

    // Función para iniciar una nueva partida
    const startGame = () => {
        setSquares(Array(9).fill(null));
        setWinner(null);
        setTurn('X');
        setRunning(true);
    }

    return (
        <div style={{ textAlign: "center" }}>
            <Typography
                variant="h4"
                gutterBottom
                sx={{
                    color: "#2196f3",
                    letterSpacing: "0.05em",
                    mb: 3,
                    fontWeight: "bold",
                    mt: 2
                }}
            >
                3 en Raya
            </Typography>

            {isRunning && (
                <div className="board-info">
                    {turn === "X" ? "NEXT: Player 2" : "NEXT: Player 1"}
                </div>
            )}

            {!isRunning && (
                <Button
                    onClick={startGame}
                    variant='outlined'>
                    Nueva Partida
                </Button>
            )}

            <Grid container spacing={1} justifyContent="center" alignItems="center" sx={{ width: "300px", margin: "20px auto" }}>
                {squares.map((square, index) => (
                    <Grid item xs={4} key={index}>
                        <Square value={square} handleClick={() => handleClick(index)} />
                    </Grid>
                ))}
            </Grid>

            {winner && (
                <Typography
                    sx={{
                        color: 'white',
                        fontSize: '1.5rem',
                        fontWeight: 'bold',
                        mt: 3,
                        letterSpacing: '0.05em',
                    }}
                >
                    {winner} es el ganador!
                </Typography>
            )}
        </div>
    );
}

export default Board;