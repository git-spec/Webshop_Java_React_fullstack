import {useState, useEffect} from "react";
import { NavLink } from "react-router-dom";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";

type Props = {
    name: string;
    path?: string;
    pl?: string;
    onCurrName: (name: string) => void;
    handleOpen: () => void;   // for collapsing list
    handleClick: () => void   // for parent of list
};


export default function ListItemAction({name, path, onCurrName, handleOpen, handleClick, pl}: Readonly<Props>) {
    const [itemName, setItemName] = useState<string>();
    const [itemPath, setItemPath] = useState<string>();

    useEffect(() => {
        setItemName(name);
        setItemPath(path);
    }, [name, path]);

    /**
     * Send name of list item to parent.
     */
    const sendCurrName = () => {
        onCurrName(itemName ?? '');
    }

    return (
        <ListItem sx={{pl: pl ?? 0, pr: 4}} disablePadding>
            <ListItemButton onClick={() => {sendCurrName(); handleOpen(); itemPath && handleClick()}}>
                {
                    !itemPath ? 
                            <ListItemText primary={itemName} />
                    : 
                        <NavLink to={itemPath} style={{textDecoration: 'none'}} >
                            <ListItemText primary={itemName} />
                        </NavLink>
                }
            </ListItemButton> 
        </ListItem>
    );
}