import React, { useState } from 'react';
import { TextField, Button, Box } from '@mui/material';

interface WeatherFormProps {
    onSearch: (city: string) => void;
}

export const WeatherForm: React.FC<WeatherFormProps> = ({ onSearch }) => {
    const [city, setCity] = useState('');

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (city.trim()) {
            onSearch(city);
        }
    }

    return (
        <Box component="form" onSubmit={handleSubmit} sx={{ 
            display: 'flex', 
            gap: 2,
            width: '100%' 
        }}>
            <TextField
                fullWidth
                size="small"
                label="Ciudad"
                variant="outlined"
                value={city}
                onChange={(e) => setCity(e.target.value)}
                sx={{ flexGrow: 1 }}
            />
            <Button 
                type="submit" 
                variant="contained" 
            >
                Buscar
            </Button>
        </Box>
    )
}