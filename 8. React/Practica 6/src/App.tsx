import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import { Typography } from '@mui/material';
import PetsIcon from '@mui/icons-material/Pets';
import DogSearchForm from './components/DogSearchForm';


function App() {

  return (
      <Box>
        <AppBar
        position='absolute'>
          <Toolbar
            sx={{
              display: 'flex',
              justifyContent: { xs: 'center', sm: 'space-between' },
              alignItems: 'center',
              flexDirection: { xs: 'column', sm: 'row' },
              backgroundColor: 'white',
              padding: '0 16px',
              height: { xs: 64, sm: 80 },
            }}
          >
            <Box
              sx={{
                display: 'flex',
                alignItems: 'center',
                gap: 2,
              }}>
              <PetsIcon sx={{ color: '#6366f1' }}/>
              <Typography variant='h6' component='div'
                sx={{
                  fontWeight: 'bold',
                  color: '#6366f1',
                }}>
                Dog API
              </Typography>
            </Box>
            <Typography
              variant='subtitle1'
              sx={{
                display: { xs: 'none', sm: 'block' }
              }}>
              Welcome, Paco!
            </Typography>
          </Toolbar>
        </AppBar>

        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            width: '100%',
            height: {
              xs: 'calc(100vh - 64px)',
              sm: 'calc(100vh - 80px)'
            },
            paddingTop: { xs: '64px', sm: '80px' },
          }}>
          <DogSearchForm />
        </Box>
      </Box>
  );
}

export default App;