import { Typography, Box } from "@mui/material";
import WeatherForm from "./components/WeatherForm"

function App() {
  return (
    <>
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'center',
          alignItems: 'center',
        }}>
        <Typography
          variant="h2"
          component="h1"
          gutterBottom
          sx={{ color: '#1976D2', fontWeight: 'bold' }}
        >
          Clima Ciudad
        </Typography>
        <WeatherForm/>
      </Box>
    </>
  );
}

export default App;