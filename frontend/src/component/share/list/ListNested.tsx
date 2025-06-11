import {useState, useEffect, Fragment, useCallback} from "react";
import List from "@mui/material/List";
import Collapse from "@mui/material/Collapse";

import type { INavItem } from "@/interface/INavItem";
import ListItemAction from "./ListItemAction";

type Props = {
    data: INavItem[];
    handleClick: () => void;
    onOpen?: () => void;    // trigger of nested list
};


export default function ListNested({data, handleClick, onOpen}: Readonly<Props>) {
    const [items, setItems] = useState<INavItem[]>();
    const [open, setOpen] = useState(false);
    const [currName, setCurrName] = useState<string>();

    /**
     * Gets triggered by list item to toggle collapse.
     */
    const handleOpen = () => {
        // list item inside of this
        setOpen(!open);
        // list item of nested list in collape
        onOpen && onOpen();
    }

    /**
     * Gets name of list item for collapsing.
     * @param name - name of list item
     */
    const handleCurrName = (name: string) => {
        setCurrName(name);
    }

    useEffect(() => {
        setItems(data);
    }, []);

    return (
        <List disablePadding>
            {items?.map((item, index) => (
                <Fragment key={item.name + '_' + index}>
                    <ListItemAction 
                        name={item.name} path={item.path} pl={item.pl ?? '0'} 
                        onCurrName={handleCurrName} 
                        handleOpen={handleOpen} 
                        handleClick={handleClick} 
                    />
                    {item.subnav &&
                        <Collapse in={item.name == currName ? open : false} timeout="auto" unmountOnExit>
                            <ListNested data={item.subnav} handleClick={handleClick} onOpen={handleOpen} />
                        </Collapse>
                    }
                </Fragment>
            ))}
        </List>
    );
}