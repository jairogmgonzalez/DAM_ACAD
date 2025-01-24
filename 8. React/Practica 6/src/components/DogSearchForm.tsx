import React, { useState } from 'react';
import { Box, FormControl, TextField, InputLabel, Select, MenuItem, Button, SelectChangeEvent } from '@mui/material';
import { Grid, Card, CardMedia, CardContent, Typography } from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles';

function DogSearchForm() {
  const [breed, setBreed] = React.useState<string>('');
  const [finalBreed, setFinalBreed] = React.useState<string>('');
  const [quantity, setQuantity] = React.useState<number>(1);
  const [error, setError] = useState({ error: false, message: '' });
  const [dogImages, setDogImages] = React.useState<string[]>([]);


  const API_REQUEST = (breed: string, quantity: number) =>
    `https://dog.ceo/api/breed/${breed}/images/random/${quantity}`;

  const handleBreedChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setBreed(event.target.value.trim().toLowerCase());
  };

  const handleQuantityChange = (event: SelectChangeEvent<string>) => {
    const value = Number(event.target.value);
    setQuantity(value || 1);
  };

  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    setDogImages([]);

    if (breed.trim() === '') {
      setError({ error: true, message: 'Por favor, introduce una raza válida.' });
      return;
    }

    setError({ error: false, message: '' });
    setFinalBreed(breed);

    try {
      const response = await fetch(API_REQUEST(breed, quantity));
      const data = await response.json();

      if (data.status !== "success") {
        throw new Error('Error al obtener las fotos.');
      }

      setDogImages(data.message);

    } catch (error) {
      console.error('Fetch error:', error);
      setError({ error: true, message: 'Ha ocurrido un error al buscar las imagenes' });
    }
  }

  const getGridSize = (quantity: number) => {
    switch (quantity) {
      case 1:
        return { xs: 12, sm: 8, md: 6, lg: 4 };
      case 2:
        return { xs: 12, sm: 6, md: 6, lg: 4 };
      case 3:
        return { xs: 12, sm: 6, md: 4, lg: 4 };
      case 5:
        return { xs: 12, sm: 6, md: 4, lg: 4 };
      case 8:
        return { xs: 12, sm: 6, md: 3, lg: 3 };
      case 10:
        return { xs: 12, sm: 6, md: 3, lg: 2.5 };
      default:
        return { xs: 12, sm: 6, md: 4, lg: 3 };
    }
  };

  const theme = createTheme({
    palette: {
      primary: {
        main: '#3f51b5',
      },
    },
    components: {
      MuiSelect: {
        styleOverrides: {
          select: {
            '&:focus': {
              backgroundColor: 'transparent',
            },
          },
        },
      },
    },
  });

  return (
    <ThemeProvider theme={theme}>

      <Box
        sx={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          flexDirection: { xs: 'column', sm: 'column' },
          gap: 2,
          mt: 4,
        }}>
        <Box
          component="form"
          autoComplete='off'
          onSubmit={onSubmit}
          sx={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            flexDirection: { xs: 'column', sm: 'row' },
            gap: 2
          }}>
          <FormControl variant="standard" sx={{ m: 1, minWidth: 120 }}>
            <TextField
              id="standard-basic"
              label="Raza"
              variant="standard"
              value={breed}
              onChange={handleBreedChange} />
          </FormControl>
          <FormControl variant="standard" sx={{ m: 1, minWidth: 120 }}>
            <InputLabel id="demo-simple-select-standard-label">Cantidad</InputLabel>
            <Select
              labelId="demo-simple-select-standard-label"
              label="Cantidad"
              value={quantity.toString()}
              onChange={handleQuantityChange}
            >
              <MenuItem value={1}>1</MenuItem>
              <MenuItem value={2}>2</MenuItem>
              <MenuItem value={3}>3</MenuItem>
              <MenuItem value={5}>5</MenuItem>
              <MenuItem value={8}>8</MenuItem>
              <MenuItem value={10}>10</MenuItem>
            </Select>
          </FormControl>
          <Button
            type="submit"
            variant="contained"
            sx={{
              ml: 2,
              mt: 2,
              backgroundColor: '#6366f1',
              borderRadius: '10%',
            }}>
            Buscar
          </Button>
        </Box>
        <Box
          sx={{
            mt: 6,
            width: '100%',
            display: 'flex',
            justifyContent: 'center',
          }}
        >
          <Grid
            container
            rowSpacing={3}
            columnSpacing={{ xs: 2, sm: 3, md: 4 }}
            justifyContent="center"
          >
            {dogImages.length > 0 &&
              dogImages.map((imgUrl, index) => {
                const gridSize = getGridSize(dogImages.length);
                return (
                  <Grid item {...gridSize} key={index} sx={{ display: 'flex', justifyContent: 'center' }}>
                    <Card sx={{ maxWidth: 345, boxShadow: 3, borderRadius: '10%' }}>
                      <CardMedia component="img" image={imgUrl} title={breed || 'Dog Image'} sx={{ height: 250, width: 400 }} />
                      <CardContent>
                        <Typography gutterBottom variant="h5" component="div">
                          {finalBreed.charAt(0).toUpperCase() + finalBreed.slice(1).toLowerCase()}
                        </Typography>
                      </CardContent>
                    </Card>
                  </Grid>
                );
              })}

            {error.error &&
              <Grid item xs={12} sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                <Typography variant="h6" textAlign="center" mt={3} color="error">
                  {error.message}
                </Typography>
              </Grid>
            }
          </Grid>
        </Box>
      </Box >
    </ThemeProvider>

  );

}

export default DogSearchForm;