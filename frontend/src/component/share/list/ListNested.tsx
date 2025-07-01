import {useState, Fragment} from "react";
import List from "@mui/material/List";
import Collapse from "@mui/material/Collapse";

import type { INavItem } from "@/interface/INavItem";
import ListItemAction from "./ListItemAction";

type Props = {
    data: INavItem[];
    handleClick: () => void;
};


export default function ListNested({data, handleClick}: Readonly<Props>) {
    const [open, setOpen] = useState(false);
    const [currName, setCurrName] = useState<string>();

    /**
     * Gets name of list item for collapsing and navigation.
     * @param name - name of list item
     */
    const handleCurrName = (name: string) => {
        if (name === currName) {
            // for toggling collapse
            setOpen(!open);
        } else {
            // name of selected navigation point
            setCurrName(name);
            // for collapsing subnavigations
            setOpen(true);
        }
        
    }

    return (
        <List disablePadding>
            {data?.map((item, index) => (
                <Fragment key={item.name + '_' + index}>
                    <ListItemAction 
                        item={item} 
                        onCurrName={handleCurrName}
                        handleClick={handleClick} 
                    />
                    {
                        item.subnav &&
                            <Collapse 
                                in={item.name == currName ? open : false} 
                                timeout="auto" 
                                unmountOnExit
                            >
                                <ListNested 
                                    data={item.subnav} 
                                    handleClick={
                                        () => {
                                            handleClick(); 
                                            setCurrName(undefined); 
                                            setOpen(false)
                                        }
                                    }
                                />
                            </Collapse>
                    }
                </Fragment>
            ))}
        </List>
    );
}