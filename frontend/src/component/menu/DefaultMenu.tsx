import { useContext } from "react";
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

export default function DefaultMenu({id, anchorEl, onClose, onLogin, onLogout}: Readonly<Props>) {
    const navigate = useNavigate();
    const context = useContext(UserContext);
    if (!context) throw new Error("UserContext must be used within a UserProvider");
    const {user} = context;
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
            { !user && <MenuItem onClick={() => {handleMenuClose(); onLogin()}}>Login</MenuItem>}
            {
                user && menuItems
            }
        </Menu>
    );
}