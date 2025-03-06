import { useContext } from 'react';
import Grid from '@mui/material/Grid';
import Square from './Square';
import Button from "@mui/material/Button";
import Typography from '@mui/material/Typography';
import { GameContext } from '../context/GameContext';

function Board() {
    const game = useContext(GameContext);
    if (!game) {
        throw new Error("GameContext debe usarse dentro de un GameProvider");
    }
    
    const { squares, winner, turn, isRunning, handleClick, startGame } = game as {
        squares: ("X" | "O" | null)[];
        winner: string | null;
        turn: "X" | "O";
        isRunning: boolean;
        handleClick: (index: number) => void;
        startGame: () => void;
    };

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
                        color: 'black',
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