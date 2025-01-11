import React from 'react';
import { Container, Grid, Typography, Box } from '@mui/material';

import MiCard from './miCard';

function MiContent() {

    return (
        <Container
            className="content-container"
            disableGutters
            maxWidth={false}
            sx={{
                minHeight: 'calc(100vh - 64px)',
                display: 'flex',
                alignItems: 'center',
                px: { xs: 2, md: 6 },
                py: { xs: 4, md: 6 },
                bgcolor: '#0d1117'
            }}
        >
            <Grid
                container
                spacing={4}
                justifyContent="center"
                alignItems="center"
                sx={{ height: '100%' }}
            >
                <Grid
                    item
                    xs={12}
                    md={6}
                    sx={{ display: 'flex', justifyContent: 'flex-start', alignItems: 'center' }}
                >
                    <Box sx={{ maxWidth: 800, p: { xs: 2, md: 4 }, justifyContent: 'flex-start' }}>
                        <Typography
                            component="h1"
                            sx={{
                                fontSize: { xs: '2.5rem', md: '3.75rem' },
                                fontWeight: '900',
                                letterSpacing: '-0.02em',
                                color: '#e6edf3',
                                lineHeight: 1.1,
                                mb: 2,
                            }}
                        >
                            Control de versiones
                        </Typography>
                        <Typography
                            sx={{
                                fontSize: { xs: '1.25rem', md: '1.75rem' },
                                color: '#8b949e',
                                fontWeight: '500',
                                lineHeight: 1.5,
                                mb: 3,
                            }}
                        >
                            Gestiona tus proyectos de forma eficiente y profesional
                        </Typography>
                        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                            <Typography
                                sx={{
                                    fontSize: { xs: '1rem', md: '1.2rem' },
                                    lineHeight: 1.6,
                                    color: '#c9d1d9',
                                }}
                            >
                                Git es un sistema de control de versiones que realiza un
                                seguimiento preciso de los cambios en los archivos. Git se vuelve
                                especialmente valioso cuando múltiples personas colaboran en los
                                mismos archivos simultáneamente.
                            </Typography>

                            <Typography
                                sx={{
                                    fontSize: { xs: '1rem', md: '1.2rem' },
                                    lineHeight: 1.6,
                                    color: '#c9d1d9',
                                }}
                            >
                                Con Git, mantienes un historial detallado de todas las
                                modificaciones, puedes trabajar en diferentes características
                                simultáneamente y colaborar de manera eficiente con otros
                                desarrolladores en proyectos de cualquier escala.
                            </Typography>
                        </Box>
                    </Box>
                </Grid>
                <Grid
                    item
                    xs={12}
                    md={6}
                    sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100%' }}
                >
                    <Box sx={{
                        width: '100%',
                        maxWidth: { xs: '100%', sm: 405 },  // Limitamos el ancho máximo
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center'
                    }}>
                        <MiCard />
                    </Box>
                </Grid>
            </Grid>
        </Container>
    );
}

export default MiContent;
