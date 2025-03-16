import { FormContext } from "../context/FormContext";
import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { Box, Typography, TextField, Button, Container, Paper, Avatar } from "@mui/material";
import { HowToReg } from "@mui/icons-material";

export function Register() {
    const { setUser } = useContext(FormContext);
    const navigate = useNavigate();

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();

        const formData = new FormData(event.currentTarget as HTMLFormElement);
        const name = formData.get("name") as string;
        const age = Number(formData.get("age"));

        if (!name || age <= 0) return;

        setUser({ name, age });
        navigate("/home");
    };

    return (
        <Container maxWidth="sm">
            <Box
                sx={{
                    mt: 8,
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                }}
            >
                <Paper elevation={3} sx={{ p: 4, width: "100%" }}>
                    <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", mb: 3 }}>
                        <Avatar sx={{ bgcolor: "primary.main", width: 56, height: 56, mb: 2 }}>
                            <HowToReg />
                        </Avatar>
                        <Typography variant="h4" component="h1" fontWeight="500">
                            Registro
                        </Typography>
                    </Box>
                    
                    <Box
                        component="form"
                        onSubmit={handleSubmit}
                        sx={{
                            display: "flex",
                            flexDirection: "column",
                            gap: 3,
                        }}
                    >
                        <TextField
                            required
                            fullWidth
                            label="Nombre"
                            name="name"
                            variant="outlined"
                            autoFocus
                        />
                        
                        <TextField
                            required
                            fullWidth
                            label="Edad"
                            name="age"
                            type="number"
                            variant="outlined"
                            inputProps={{ min: 1 }}
                        />
                        
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            size="large"
                            sx={{ mt: 2 }}
                        >
                            Registrarse
                        </Button>
                    </Box>
                </Paper>
            </Box>
        </Container>
    );
}