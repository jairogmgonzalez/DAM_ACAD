import { Box, Typography, Container, Paper, List, ListItem, ListItemIcon, ListItemText } from "@mui/material";
import { Info, School, Code } from "@mui/icons-material";

export function About() {
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
                        Acerca de
                    </Typography>
                    <Typography variant="body1" paragraph>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                    </Typography>

                    <Typography variant="h6" gutterBottom sx={{ mt: 2 }}>
                        Lorem ipsum:
                    </Typography>
                    <List>
                        <ListItem>
                            <ListItemIcon>
                                <Code />
                            </ListItemIcon>
                            <ListItemText primary="Lorem Ipsum" secondary="Lorem Ipsum" />
                        </ListItem>
                        <ListItem>
                            <ListItemIcon>
                                <Code />
                            </ListItemIcon>
                            <ListItemText primary="Lorem Ipsum" secondary="Lorem Ipsum" />
                        </ListItem>
                        <ListItem>
                            <ListItemIcon>
                                <Code />
                            </ListItemIcon>
                            <ListItemText primary="Lorem Ipsum" secondary="Lorem Ipsum" />
                        </ListItem>
                        <ListItem>
                            <ListItemIcon>
                                <Code />
                            </ListItemIcon>
                            <ListItemText primary="Lorem Ipsum" secondary="Lorem Ipsum" />
                        </ListItem>
                    </List>
                </Paper>
            </Box>
        </Container>
    );
}
