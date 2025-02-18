import React, { useEffect, useState } from 'react';
import { TextField, Button, Box } from '@mui/material';
import { Weather } from '../types/Weather';
import { WeatherFetch } from '../services/WeatherFetch';
import { WeatherDisplay } from './WeatherDisplay';

export default function WeatherForm() {
    const [inputCity, setInputCity] = useState('');
    const [searchCity, setSearchCity] = useState('');
    const [weather, setWeather] = useState<Weather | null>(null);
    const [error, setError] = useState({ error: false, message: '' });

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (inputCity.trim()) {
            setSearchCity(inputCity);
        }
    };

    useEffect(() => {
        if (!searchCity.trim()) return;

        const getWeather = async () => {
            try {
                setWeather(null);
                setError({ error: false, message: '' });

                const data = await WeatherFetch(searchCity);

                if (!data) {
                    setError({ error: true, message: 'No se encontraron datos para la ciudad ingresada' });
                    return;
                }

                setWeather({
                    city: data.location.name,
                    country: data.location.country,
                    temp: data.current.temp_c,
                    condition: data.current.condition.code,
                    icon: data.current.condition.icon,
                    conditionText: data.current.condition.text,
                });
            } catch (err) {
                setError({ error: true, message: 'Error al obtener los datos' });
            }
        };

        getWeather();
    }, [searchCity]);

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', gap: 2 }}>
                <TextField
                    label="Ciudad"
                    variant="outlined"
                    size="medium"
                    value={inputCity}
                    onChange={(e) => setInputCity(e.target.value)}
                />
                <Button type='submit' variant='contained'>
                    Buscar Clima
                </Button>
            </Box>

            {error.error && <p style={{ color: "red" }}>{error.message}</p>}
            {weather && <WeatherDisplay weather={weather} />}
        </Box>
    );
}