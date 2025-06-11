import {useState, useEffect} from "react";
import { useNavigate } from "react-router-dom";
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
    const navigate = useNavigate();

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

    const handleNavigation = (path: string) => {
        navigate(path);
        handleClick();
    }

    return (
        <ListItem sx={{pl: pl ?? 0, pr: 4}} disablePadding>
            <ListItemButton onClick={() => {sendCurrName(); handleOpen(); itemPath && handleNavigation(itemPath)}}>
                    <ListItemText primary={itemName} />
            </ListItemButton>
        </ListItem>
    );
}