import React, { useState } from 'react';
import Container from '@mui/material/Container';
import { Typography } from '@mui/material';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

const API_WEATHER = `https://api.weatherapi.com/v1/current.json?key=${import.meta.env.VITE_API_KEY}&lang=es&q=`;

function App() {
  const [city, setCity] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState({ error: false, message: '' });
  const [weather, setWeather] = useState({
    city: "",
    country: "",
    temp: "",
    condition: "",
    icon: "",
    conditionText: "",
  })

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!city.trim()) {
      setError({ error: true, message: "El campo ciudad es obligatorio" });
      return;
    }

    setError({ error: false, message: '' });

    setLoading(true);

    try {
      const response = await fetch(API_WEATHER + city);
      const data = await response.json();

      if (data.error) {
        throw new Error("No se encontraron datos para la ciudad ingresada");
      }

      setWeather({
        city: data.location.name,
        country: data.location.country,
        temp: data.current.temp_c,
        condition: data.current.condition.code,
        icon: data.current.condition.icon,
        conditionText: data.current.condition.text,
      });
    } catch (ex) {
      setError({ error: true, message: (ex as Error).message });
    } finally {
      setLoading(false);
    }

  };

  return (
    <Container maxWidth="xs" sx={{ mt: 2, }}>
      <Typography variant="h3" component="h1" align="center" gutterBottom>
        Clima Ciudad
      </Typography>
      <Box sx={{ display: "grid", gap: 2 }} component="form" autoComplete="off" onSubmit={onSubmit}>
        <TextField
          id="city"
          label="Ciudad"
          variant="outlined"
          required
          fullWidth
          size='small'
          value={city}
          onChange={(e) => setCity(e.target.value)} />
        <Button
          type="submit"
          variant="contained"
          loading={loading}
          loadingIndicator="Cargando...">
          Buscar
        </Button>
      </Box>
      {weather.city && (
        <Box sx={{ mt: 2, display: "grid", gap: 2, textAlign: "center" }}>
          <Typography variant='h4' component='h2'>
            {weather.city}, {weather.country}
          </Typography>
          <Box component='img' alt={weather.conditionText} src={weather.icon} sx={{ margin: "0 auto" }} />
          <Typography variant='h2' component='h2'>
            {weather.temp}ºC
          </Typography>
          <Typography variant='h5' component='h5'>
            {weather.conditionText}
          </Typography>
        </Box>
      )}
    </Container>
  )
}

export default App;