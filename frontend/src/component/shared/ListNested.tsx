import {useState, useEffect, Fragment, type ReactNode, useCallback} from "react";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import Collapse from "@mui/material/Collapse";

import type { INavItem } from "@/interface/INavItem";
import ListItemLink from "./ListItemLink";

type Props = {
    data: INavItem[];
};


export default function ListNested({data}: Readonly<Props>) {
    const [items, setItems] = useState<INavItem[]>();
    const [open, setOpen] = useState(false);
    const [currName, setCurrName] = useState<string>();

    const handleOpen = useCallback(() => {
        setOpen(!open);
    }, [open]);

    const handleCurrName = (name: string) => {
        setCurrName(name);
    }

    useEffect(() => {
        setItems(data);
    }, []);

    return (
        <List disablePadding>
            {items?.map((item, index) => (
                <Fragment>
                    <ListItemLink 
                        key={item.name + '_' + index} name={item.name} path={item.path} pl={item.pl ?? '0'} 
                        onCurrName={handleCurrName} 
                        handleClick={handleOpen} 
                    />
                    {item.subnav &&
                        <Collapse in={item.name == currName && open} timeout="auto" unmountOnExit>
                            <ListNested data={item.subnav} />
                        </Collapse>
                    }
                </Fragment>
            ))}
        </List>
    );
}