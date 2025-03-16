import { AppBar, Toolbar, Typography, Button, Box, Container } from "@mui/material";
import { Link } from "react-router-dom";
import { useContext } from "react";
import { FormContext } from "../context/FormContext";

function NavigationBar() {
    const { user, setUser } = useContext(FormContext);

    const handleLogout = () => {
        setUser(null);
    };

    return (
        <AppBar position="static">
            <Container maxWidth="xl">
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        React
                    </Typography>
                    <Box sx={{ display: "flex", gap: 2 }}>
                        <Button 
                            color="inherit" 
                            component={Link} 
                            to="/home"
                        >
                            Inicio
                        </Button>
                        <Button 
                            color="inherit" 
                            component={Link} 
                            to="/about"
                        >
                            Acerca de
                        </Button>
                        <Button 
                            color="inherit" 
                            component={Link} 
                            to="/contact"
                        >
                            Contacto
                        </Button>
                        <Button 
                            color="inherit" 
                            component={Link} 
                            to="/summary"
                        >
                            Resumen
                        </Button>
                        
                        {user && (
                            <Button 
                                color="secondary" 
                                variant="contained"
                                onClick={handleLogout}
                            >
                                Cerrar Sesión
                            </Button>
                        )}
                    </Box>
                </Toolbar>
            </Container>
        </AppBar>
    );
}

export default NavigationBar;