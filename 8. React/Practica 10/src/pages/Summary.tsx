import { Box, Typography, Container, Paper, Divider, Avatar } from "@mui/material";
import { Person } from "@mui/icons-material";
import { useContext } from "react";
import { FormContext } from "../context/FormContext";

export function Summary() {
    const { user } = useContext(FormContext);

    if (!user) {
        return (
            <Container maxWidth="sm">
                <Box sx={{ mt: 4 }}>
                    <Paper elevation={3} sx={{ p: 4, textAlign: "center" }}>
                        <Typography variant="h5" color="text.secondary">
                            No hay datos de usuario disponibles
                        </Typography>
                    </Paper>
                </Box>
            </Container>
        );
    }

    return (
        <Container maxWidth="sm">
            <Box 
                sx={{ 
                    mt: 4,
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center"
                }}
            >
                <Paper elevation={3} sx={{ p: 4, width: "100%" }}>
                    <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", mb: 3 }}>
                        <Avatar sx={{ bgcolor: "primary.main", width: 80, height: 80, mb: 2 }}>
                            <Person sx={{ fontSize: 40 }} />
                        </Avatar>
                        <Typography variant="h4" component="h1" align="center" gutterBottom>
                            Resumen de Usuario
                        </Typography>
                    </Box>
                    
                    <Divider sx={{ mb: 3 }} />
                    
                    <Box sx={{ mb: 2 }}>
                        <Typography variant="subtitle1" color="text.secondary" gutterBottom>
                            Nombre
                        </Typography>
                        <Typography variant="h6">
                            {user.name}
                        </Typography>
                    </Box>
                    
                    <Box sx={{ mb: 2 }}>
                        <Typography variant="subtitle1" color="text.secondary" gutterBottom>
                            Edad
                        </Typography>
                        <Typography variant="h6">
                            {user.age} años
                        </Typography>
                    </Box>
                </Paper>
            </Box>
        </Container>
    );
}