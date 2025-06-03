import {useState, useEffect} from "react";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import { Link } from "react-router-dom";

type Props = {
    name: string;
    path: string;
    pl?: string;
    onCurrName: (name: string) => void;
    handleClick: () => void
};


export default function ListItemLink({name, path, onCurrName, handleClick, pl}: Readonly<Props>) {
    const [itemName, setItemName] = useState<string>();
    const [itemPath, setItemPath] = useState<string>();

    const sendCurrName = () => {
        onCurrName(itemName ?? '');
    }

    useEffect(() => {
        setItemName(name);
        setItemPath(path);
    }, [name, path]);

    return (
        <ListItem key={itemName} sx={{pl: pl ?? 0, pr: 4}} disablePadding>
            <ListItemButton onClick={() => {sendCurrName(); handleClick()}}>
                <Link to={itemPath ?? ''} style={{textDecoration: 'none'}}>
                    <ListItemText primary={itemName} />
                </Link>
            </ListItemButton>
        </ListItem>
    );
}