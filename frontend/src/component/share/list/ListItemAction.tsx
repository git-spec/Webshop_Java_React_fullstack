import { NavLink } from "react-router-dom";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import type { INavItem } from "@/interface/INavItem";

type Props = {
    item: INavItem;
    onCurrName: (name: string) => void;
    handleClick: () => void   // for parent of list
};


export default function ListItemAction({item, onCurrName, handleClick}: Readonly<Props>) {

    /**
     * Send name of list item to parent.
     */
    const sendCurrName = () => {
        onCurrName(item.name ?? '');
    }

    return (
        <ListItem sx={{pl: item.pl ?? 0, pr: 4, pb: 1}} disablePadding>
                {
                    !item.path ? 
                        <ListItemButton onClick={() => {sendCurrName(); !item.subnav && handleClick()}}>
                            <ListItemText primary={item.name} />
                        </ListItemButton> 
                    : 
                        <NavLink to={item.path} style={{textDecoration: 'none'}} onClick={() => {sendCurrName(); !item.subnav && handleClick()}}>
                            <ListItemText primary={item.name} />
                        </NavLink>
                }
        </ListItem>
    );
}