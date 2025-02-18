import { Box, Typography, Card, CardContent } from "@mui/material";
import { Weather } from '../types/Weather';

interface WeatherDisplayProps {
    weather: Weather;
}

export const WeatherDisplay: React.FC<WeatherDisplayProps> = ({ weather }) => {
    return (
        <Card sx={{ 
            mt: 2,
            boxShadow: 'none',
            backgroundColor: 'transparent'
        }}>
            <CardContent sx={{ 
                display: "grid", 
                gap: 2, 
                textAlign: "center",
                color: 'text.primary'
            }}>
                <Typography variant='h4' component='h2' sx={{ fontWeight: 'bold' }}>
                    {weather.city}, {weather.country}
                </Typography>
                <Box 
                    component='img' 
                    alt={weather.conditionText} 
                    src={weather.icon} 
                    sx={{ 
                        margin: "0 auto",
                        width: 100,
                        height: 100,
                        filter: 'drop-shadow(2px 2px 4px rgba(0,0,0,0.2))'
                    }} 
                />
                <Typography variant='h2' component='h2' sx={{ fontWeight: 'bold' }}>
                    {weather.temp}ºC
                </Typography>
                <Typography variant='h5' component='h5'>
                    {weather.conditionText}
                </Typography>
            </CardContent>
        </Card>
    );
}