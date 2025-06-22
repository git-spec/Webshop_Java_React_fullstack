import { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { UserContext } from "@/App";


type Props = {
    id: string;
    anchorEl: HTMLElement | null;
    onClose: (close: null) => void;
    onLogin: () => void;
    onLogout: () => void;
};

function DefaultMenu({id, anchorEl, onClose, onLogin, onLogout}: Readonly<Props>) {
    const navigate = useNavigate();
    const user = useContext(UserContext);
    const menuId = id;
    const isOpen = Boolean(anchorEl);
    const menuItems = [
        <MenuItem key={'dashboard'} onClick={() => {handleMenuClose(); navigate('/dashboard')}}>Dashboard</MenuItem>,
        <MenuItem key={'logout'} onClick={() => {handleMenuClose(); onLogout()}}>Logout</MenuItem>
    ];

    const handleMenuClose = () => {
        onClose(null);
    };

    return (
        <Menu
            anchorEl={anchorEl}
            anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
            }}
            id={menuId}
            keepMounted
            transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
            }}
            open={isOpen}
            onClose={handleMenuClose}
        >
            {/* <MenuItem onClick={handleMenuClose}>Profile</MenuItem> */}
            { !user && <MenuItem onClick={() => {handleMenuClose(); onLogin()}}>Login</MenuItem>}
            {
                user && menuItems
            }
        </Menu>
    );
}

export default DefaultMenu;