import * as React from 'react';
import { Card, CardActions, CardContent, CardMedia, Button, Typography } from '@mui/material';

function MiCard() {
    return (
        <Card
            sx={{
                width: '100%',
                maxWidth: { xs: '100%', sm: 405 },
                boxShadow: 8,
                backgroundColor: '#1c2128',
                color: '#c9d1d9',
                borderRadius: 2,
                mx: { xs: 2, sm: 0 },
                '&:hover': {
                    boxShadow: 12,
                    transform: 'translateY(-5px)',
                    transition: 'all 0.3s ease-in-out',
                },
            }}
        >
            <CardMedia
                sx={{
                    height: { xs: 300, sm: 400 },
                    width: '100%',
                    borderRadius: '8px 8px 0 0',
                    objectFit: 'cover',
                    objectPosition: 'center',
                }}
                image="/github.jpg"
                title="Imagen de Git"
            />
            <CardContent>
                <Typography
                    gutterBottom
                    variant="h5"
                    component="div"
                    sx={{
                        fontSize: { xs: '1.5rem', sm: '1.75rem' },
                        fontWeight: 'bold',
                        color: '#e6edf3',
                    }}
                >
                    GIT
                </Typography>
                <Typography
                    variant="body2"
                    sx={{
                        color: '#8b949e',
                        fontSize: { xs: '0.875rem', sm: '1rem' },
                        lineHeight: 1.8,
                    }}
                >
                    Git es un sistema de control de versiones que realiza un seguimiento de los cambios en los archivos.
                    Git es especialmente útil cuando un grupo de personas y tú estáis haciendo cambios en los mismos archivos al mismo tiempo.
                </Typography>
            </CardContent>
            <CardActions sx={{ display: 'flex', justifyContent: 'space-between', px: 2 }}>
                <Button
                    size="small"
                    sx={{
                        color: '#58a6ff',
                        textTransform: 'none',
                        fontWeight: 'bold',
                        '&:hover': {
                            color: '#ffffff',
                        },
                    }}
                >
                    Compartir
                </Button>
                <Button
                    size="small"
                    sx={{
                        color: '#58a6ff',
                        textTransform: 'none',
                        fontWeight: 'bold',
                        '&:hover': {
                            color: '#ffffff',
                        },
                    }}
                >
                    Acerca de
                </Button>
            </CardActions>
        </Card>
    );
}

export default MiCard;
