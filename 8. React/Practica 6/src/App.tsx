import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import { Typography } from '@mui/material';
import PetsIcon from '@mui/icons-material/Pets';

function App() {
  return (
    <Box>
      <AppBar position='fixed'>
        <Toolbar
          sx={{
            display: 'flex',
            justifyContent: { xs: 'center', sm: 'space-between' },
            alignItems: 'center',
            flexDirection: { xs: 'column', sm: 'row' },
            backgroundColor: '#3f51b5',
            padding: '0 16px',
            minHeight: { xs: 64, sm: 80 },
          }}
        >
          <Box
            sx={{
              display: 'flex',
              alignItems: 'center',
              gap: 2,
            }}>
            <PetsIcon></PetsIcon>
            <Typography variant='h6' component='div'
              sx={{
                fontWeight: 'bold',
              }}>
              Dog API
            </Typography>
          </Box>
          <Typography
            variant='subtitle1'
            sx={{
              display: { xs: 'none', sm: 'block' }
            }}>Welcome, Paco!</Typography>
        </Toolbar>
      </AppBar>
    </Box>
  );
}

export default App;