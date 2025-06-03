import {useEffect, useState, type ReactNode} from "react";
import SwipeableDrawer from "@mui/material/SwipeableDrawer";

import type { AnchorType } from "../type/AnchorType";

type Props = {
    anchor: AnchorType
    children: ReactNode;
    open: boolean;
    onOpen: () => void;
};


function SwipeDrawer({anchor, children, open, onOpen}: Readonly<Props>) {
    const [state, setState] = useState({
        top: false,
        left: false,
        bottom: false,
        right: false,
    });
    
    useEffect(() => {
        // Opens drawer.
        setState({ ...state, [anchor]: open });
    }, [open]);

    const toggleDrawer =
        (anchor: AnchorType, open: boolean) =>
        (event: React.KeyboardEvent | React.MouseEvent) => {
            if (
                event &&
                event.type === 'keydown' &&
                ((event as React.KeyboardEvent).key === 'Tab' ||
                (event as React.KeyboardEvent).key === 'Shift')
            ) {
                return;
            }

            setState({ ...state, [anchor]: open });
            // Changes state of parent
            onOpen();
        };

    return (
            <SwipeableDrawer 
                sx={
                    {
                        '& .MuiPaper-root': {
                            pt: 8, 
                            pl: 4,
                            width: {xs: '100vw', sm: '50vw', md: '30vw', xl: '25vw'}
                        }
                    }
                } 
                anchor={anchor}
                open={state[anchor]}
                onClose={toggleDrawer(anchor, false)}
                onOpen={toggleDrawer(anchor, true)}
            >
                {children}
            </SwipeableDrawer>
    );
}

export default SwipeDrawer;