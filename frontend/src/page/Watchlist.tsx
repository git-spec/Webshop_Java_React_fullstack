import { useContext, useEffect, useState } from "react";
import axios from "axios";
import Grid from "@mui/material/Grid";

import type { IProduct } from "@/interface/IProduct";
import { UserContext } from "@/App";
import CardMain from "@/component/card/CardMain";
import CardContentProduct from "@/component/card/CardContentProduct";
import NoteBig from "@/component/share/NoteBig";


export default function Watchlist() {
    const message = "Die Merkliste ist leer.";
    const [listItems, setListItems] = useState<IProduct[]>();
    const context = useContext(UserContext);
    if (!context) throw new Error("UserContext must be used within a UserProvider");
    const {user, updateUser} = context;

    useEffect(() => {
        user && getWatchlistItems(user.watchlist);
    }, [user]);

    /**
     * Gets products for watchlist.
     * @param {Array<string>} watchlist - list of products
     * @returns {void}
     */
    const getWatchlistItems = (watchlist: Array<string>): void => {
        axios.post('/api/products', watchlist).then(res => setListItems(res.data));
    }

    /**
     * Removes product id from watchlist.
     * @param {Array<string>} item - product
     * @returns {void}
     */
    function removeWatchlistItem(item: IProduct): void {
        const body = {
            userID : user?.id,
            productID: item.id
        };
        axios.post('/api/user/watchlist', body).then(
            res => {
                if (res.status === 200) {
                    const newListItems = listItems?.filter(
                        (listItem: IProduct) => listItem.id !== item.id
                    );
                    setListItems(newListItems);
                    user?.watchlist.splice(user.watchlist.indexOf(item.id), 1);
                    updateUser(user);
                }
            }
        );
    }

    return (
        <Grid 
            container 
            columnSpacing={3} 
            rowSpacing={4} 
            sx={
                {
                    justifyContent: !listItems || listItems.length === 0 ? "center" : "", 
                    height: "100%"
                }
            }
        >
            {
                listItems && listItems.length > 0 ?
                    listItems.map((item: IProduct) => {
                        return <Grid
                                key={item.id}                                   
                                size={{xs: 12, sm: 6, md: 4, lg: 3}}
                                >
                                    <CardMain 
                                        media={{name: item.name, path: item.images.large[0]}} 
                                        content={<CardContentProduct {...item} />}
                                        state={item}
                                        deleteButton={true}
                                        path={`/product/${item.id}`} 
                                        onAction={(removeWatchlistItem)}
                                    />
                                </Grid>
                        })
                :
                    <NoteBig value={message} />
            }
        </Grid>
    );
}