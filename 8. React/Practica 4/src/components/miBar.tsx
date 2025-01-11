import React from 'react';
import { AppBar, Toolbar, Container, IconButton, Menu, MenuItem, Typography, Button, Box } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import GitHubIcon from '@mui/icons-material/GitHub';

const pagesMap: Map<string, string> = new Map([
    ["Dashboard", "https://github.com/dashboard"],
    ["Community", "https://github.com/community"],
    ["Copilot", "https://github.com/copilot"]
]);

const settingsMap: Map<string, string> = new Map([
    ["Profile", "https://github.com/jairogmgonzalez"],
    ["Settings", "https://github.com/settings/profile"],
    ["Logout", "https://github.com"]
]);

function MiBar() {
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);

    const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    return (
        <Box>
            <AppBar position='absolute' sx={{ bgcolor: '#161b22', borderBottom: '1px solid #30363d' }}>
                <Container maxWidth={false}>
                    <Toolbar sx={{ display: 'flex', alignItems: 'center', minHeight: 64 }}>
                        <Box sx={{ display: 'flex', alignItems: 'center', flex: 1 }}>
                            <IconButton onClick={handleClick}>
                                <MenuIcon sx={{ color: '#c9d1d9' }} />
                            </IconButton>
                        </Box>

                        <Box sx={{ display: 'flex', alignItems: 'center', flex: 1, justifyContent: 'center' }}>
                            <a
                                href="https://github.com/"
                                target="_blank"
                                style={{
                                    textDecoration: 'none',
                                    color: '#c9d1d9',
                                }}
                            >
                                <GitHubIcon
                                    sx={{
                                        fontSize: { xs: '2rem', md: '2.5rem' },
                                        '&:hover': { color: '#ffffff' },
                                    }}
                                    aria-label="GitHub Icon"
                                />
                            </a>
                        </Box>

                        <Box sx={{
                            display: { xs: 'none', md: 'flex' },
                            alignItems: 'center',
                            flex: 1,
                            justifyContent: 'flex-end',
                            gap: 1
                        }}>
                            {Array.from(pagesMap.entries()).map(([page, url]) => (
                                <Button
                                    key={page}
                                    href={url}
                                    target='_blank'
                                    sx={{
                                        color: '#c9d1d9',
                                        fontWeight: 400,
                                        '&:hover': {
                                            color: '#ffffff',
                                            bgcolor: '#21262d'
                                        }
                                    }}
                                >
                                    {page}
                                </Button>
                            ))}
                        </Box>

                        <Menu
                            id="basic-menu"
                            anchorEl={anchorEl}
                            open={open}
                            onClose={handleClose}
                            PaperProps={{
                                sx: {
                                    bgcolor: '#161b22',
                                    border: '1px solid #30363d',
                                    '& .MuiMenuItem-root': {
                                        color: '#c9d1d9'
                                    }
                                }
                            }}
                        >
                            {Array.from(settingsMap.entries()).map(([setting, url]) => (
                                <MenuItem
                                    key={setting}
                                    onClick={handleClose}
                                    sx={{
                                        '&:hover': {
                                            bgcolor: '#21262d'
                                        }
                                    }}
                                >
                                    <Typography
                                        component="a"
                                        href={url}
                                        target='_blank'
                                        sx={{
                                            color: '#c9d1d9',
                                            textDecoration: 'none'
                                        }}>
                                        {setting}
                                    </Typography>
                                </MenuItem>
                            ))}
                        </Menu>
                    </Toolbar>
                </Container>
            </AppBar>
        </Box>
    );
}

export default MiBar;