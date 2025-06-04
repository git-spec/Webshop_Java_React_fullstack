import { useEffect } from "react";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";

type Props = {
    id: string;
    anchorEl: HTMLElement | null;
    onClose: (close: null) => void;
};

function DefaultMenu({id, anchorEl, onClose}: Readonly<Props>) {
    let anchor: null | HTMLElement = null;
    const menuId = id;
    const isOpen = Boolean(anchorEl);

    useEffect(() => {
        if (anchorEl) {
            anchor = anchorEl;
        }
    }, [anchorEl]);

    const handleMenuClose = () => {
        anchor = null;
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
            <MenuItem onClick={handleMenuClose}>Profile</MenuItem>
            <MenuItem onClick={handleMenuClose}>My account</MenuItem>
        </Menu>
    );
}

export default DefaultMenu;