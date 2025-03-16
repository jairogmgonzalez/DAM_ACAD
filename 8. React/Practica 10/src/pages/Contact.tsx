import { Box, Typography, Container, Paper, Grid, Card, CardContent, Avatar } from "@mui/material";
import { Email, Phone, LocationOn } from "@mui/icons-material";

export function Contact() {
    return (
        <Container maxWidth="md">
            <Box 
                sx={{ 
                    mt: 4,
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center"
                }}
            >
                <Paper elevation={3} sx={{ p: 4, width: "100%" }}>
                    <Typography variant="h4" component="h1" align="center" gutterBottom>
                        Contacto
                    </Typography>
                    <Typography variant="body1" paragraph align="center">
                        Puedes contactarnos a través de los siguientes medios:
                    </Typography>

                    <Grid container spacing={3} sx={{ mt: 2 }}>
                        <Grid item xs={12} md={4}>
                            <Card sx={{ height: "100%" }}>
                                <CardContent sx={{ textAlign: "center" }}>
                                    <Avatar sx={{ bgcolor: "primary.main", mx: "auto", mb: 2 }}>
                                        <Email />
                                    </Avatar>
                                    <Typography variant="h6" gutterBottom>
                                        Email
                                    </Typography>
                                    <Typography variant="body2" color="text.secondary">
                                        info@ejemplo.com
                                    </Typography>
                                </CardContent>
                            </Card>
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <Card sx={{ height: "100%" }}>
                                <CardContent sx={{ textAlign: "center" }}>
                                    <Avatar sx={{ bgcolor: "primary.main", mx: "auto", mb: 2 }}>
                                        <Phone />
                                    </Avatar>
                                    <Typography variant="h6" gutterBottom>
                                        Teléfono
                                    </Typography>
                                    <Typography variant="body2" color="text.secondary">
                                        +34 123 456 789
                                    </Typography>
                                </CardContent>
                            </Card>
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <Card sx={{ height: "100%" }}>
                                <CardContent sx={{ textAlign: "center" }}>
                                    <Avatar sx={{ bgcolor: "primary.main", mx: "auto", mb: 2 }}>
                                        <LocationOn />
                                    </Avatar>
                                    <Typography variant="h6" gutterBottom>
                                        Dirección
                                    </Typography>
                                    <Typography variant="body2" color="text.secondary">
                                        Calle Real, 123
                                        <br />
                                        18200 Maracena
                                    </Typography>
                                </CardContent>
                            </Card>
                        </Grid>
                    </Grid>
                </Paper>
            </Box>
        </Container>
    );
}