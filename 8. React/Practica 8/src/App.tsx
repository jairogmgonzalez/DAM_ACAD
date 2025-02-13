import { useState, useEffect } from "react";
import { Container, Typography, Box, CircularProgress } from "@mui/material";
import { WeatherForm } from "./components/WeatherForm";
import { FetchWeather } from "./components/FetchWeather";
import { WeatherDisplay } from "./components/WeatherDisplay";
import { Weather } from './types/weather';

function App() {
  const [searchCity, setSearchCity] = useState<string>("");
  const [weather, setWeather] = useState<Weather | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!searchCity) return;

    const fetchWeatherData = async () => {
      setLoading(true);
      setError(null);
      
      const data = await FetchWeather(searchCity);

      if (data) {
        setWeather({
          city: data.location.name,
          country: data.location.country,
          temp: data.current.temp_c,
          condition: data.current.condition.code,
          icon: data.current.condition.icon,
          conditionText: data.current.condition.text,
        });
      } else {
        setError("No se encontraron datos para la ciudad ingresada");
        setWeather(null);
      }

      setLoading(false);
    };

    fetchWeatherData();
  }, [searchCity]); 

  const handleSearch = (city: string) => {
    setSearchCity(city);
  };

  return (
    <Container maxWidth="xs" sx={{ 
      mt: 4,
      display: 'flex',
      flexDirection: 'column',
      gap: 3 
    }}>
      <Typography 
        variant="h3" 
        component="h1" 
        align="center" 
        sx={{ 
          fontWeight: 'bold',
          color: 'primary.main' 
        }}
      >
        Clima Ciudad
      </Typography>

      <WeatherForm onSearch={handleSearch} />

      <Box sx={{ display: 'flex', justifyContent: 'center' }}>
        {loading && <CircularProgress />}
        {error && (
          <Typography color="error" sx={{ mt: 2 }}>
            {error}
          </Typography>
        )}
      </Box>
      
      {weather && <WeatherDisplay weather={weather}/>}
    </Container>
  );
}

export default App;