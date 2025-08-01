import { useCallback, useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import {ThemeProvider} from '@mui/material/styles';
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import MenuOutlinedIcon from '@mui/icons-material/MenuOutlined';
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Badge from "@mui/material/Badge";
import ShoppingCartOutlinedIcon from '@mui/icons-material/ShoppingCartOutlined';
import AccountCircle from "@mui/icons-material/AccountCircle";
// import MoreVertOutlinedIcon from '@mui/icons-material/MoreVertOutlined';

import {sidebarItems} from "@data/navData.ts";
import DefaultMenu from "@/component/menu/DefaultMenu";
import SwipeDrawer from "../share/SwipeDrawer";
import LayoutContainer from "@/component/share/LayoutContainer";
import {CartContext} from "@/App";
import ListNested from "../share/list/ListNested";
import { headerTheme } from "@/theme/headerTheme";
import type { INavItem } from "@/interface/INavItem";

export type NavPoint = {
    navPoint: INavItem | undefined;
    setNavPoint: React.Dispatch<React.SetStateAction<INavItem | undefined>>;
};

type Props = {
    onLogin: () => void;
    onLogout: () => void;
};


export default function Header(props: Readonly<Props>) {
    // anchor for sidebar
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    // const [mobileAnchorEl, setMobileAnchorEl] = useState<null | HTMLElement>(null);
    const [sidebarAnchorEl, setSidebarAnchorEl] = useState<null | HTMLElement>(null);
    const isSidebarOpen = Boolean(sidebarAnchorEl);
    // const isMenuOpen = Boolean(anchorEl);
    // const isMobileMenuOpen = Boolean(mobileAnchorEl);
    const menuId = 'primary-search-account-menu';
    // const mobileMenuId = 'primary-search-account-menu-mobile';
    const navigate = useNavigate();
    const cartContext = useContext(CartContext);
    if (!cartContext) throw new Error("CartContext must be used within a CartProvider");
    const {cart} = cartContext;

    const onSidebarOpen = useCallback(() => {
        setSidebarAnchorEl(null);
    }, [])

    const handleSidebarOpen = (event: React.MouseEvent<HTMLElement>) => {
        setSidebarAnchorEl(event.currentTarget);
    };

    const handleMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = useCallback(() => {
        setAnchorEl(null);
        // handleMobileMenuClose();
    }, []);

    // const handleMobileMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
    //     setMobileAnchorEl(event.currentTarget);
    // };

    // const handleMobileMenuClose = useCallback(() => {
    //     setMobileAnchorEl(null);
    // }, []);

    return (
        <ThemeProvider theme={headerTheme}>
            {/* Sidebar */}
            <SwipeDrawer anchor={'left'} open={isSidebarOpen} onOpen={onSidebarOpen}>
                <ListNested data={sidebarItems} handleClick={onSidebarOpen} />
            </SwipeDrawer>
            <AppBar position="sticky" style={{height: '4rem'}}>
                <LayoutContainer>
                    <Toolbar sx={{display: 'flex', justifyContent: 'space-between'}} disableGutters>
                        {/* Menu Button for Sidebar */}
                        <IconButton
                            size="large"
                            aria-label="account of current user"
                            aria-controls="menu-appbar"
                            aria-haspopup="true"
                            onClick={handleSidebarOpen}
                            color="inherit"
                            sx={{ml: -1}}
                        >
                            <MenuOutlinedIcon />
                        </IconButton>
                        {/* Logo */}
                        <Typography
                            variant="h6"
                            noWrap
                            component="div"
                        >
                            LOGO
                        </Typography>
                        {/* <Box sx={{ display: { xs: 'none', md: 'flex' } }}> */}
                        <Box>
                            {/* Cart */}
                            <IconButton 
                                size="large" 
                                aria-label="show 4 new mails" 
                                color="inherit" 
                                onClick={() => navigate('/cart')}
                            >
                            <Badge badgeContent={cart?.length ?? 0} color="error">
                                <ShoppingCartOutlinedIcon />
                            </Badge>
                            </IconButton>
                            {/* Account Button */}
                            <IconButton
                                size="large"
                                aria-label="show 17 new notifications"
                                aria-controls={menuId}
                                color="inherit"
                                sx={{mr: -1}}
                                onClick={handleMenuOpen}
                            >
                            <AccountCircle />
                            </IconButton>
                        </Box>
                        {/* <Box sx={{ display: { xs: 'flex', md: 'none' } }}>
                            <IconButton
                                size="large"
                                aria-label="show more"
                                aria-controls={mobileMenuId}
                                aria-haspopup="true"
                                onClick={handleMobileMenuOpen}
                                color="inherit"
                            >
                            <MoreVertOutlinedIcon />
                            </IconButton>
                        </Box> */}
                    </Toolbar>
                </LayoutContainer>
            </AppBar>
            {/* Menu for Account */}
            <DefaultMenu id={menuId} anchorEl={anchorEl} onClose={handleMenuClose} onLogin={props.onLogin} onLogout={props.onLogout} />
        </ThemeProvider>
    );
}