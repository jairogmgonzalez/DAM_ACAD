import React from 'react';
import { useState } from 'react';
import Stack from '@mui/material/stack';

function App() {

  return (
    <Stack
      direction={{ xs: 'column', sm: 'row' }}>
      <div>Hola</div>
      <div>qué</div>
      <div>haces</div>
    </Stack>
  )
}

export default App;