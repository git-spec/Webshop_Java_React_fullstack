import { useEffect, useState } from "react";
import axios from "axios";

import type { IUserAuth } from "@/interface/IUserAuth";
import type { IWatchlistItem } from "@/interface/IWatchlistItem";
import type { IProduct } from "@/interface/IProduct";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import CardMain from "@/component/card/CardMain";
import CardContentProduct from "@/component/card/CardContentProduct";
import NoteBig from "@/component/share/NoteBig";

type Props = {
    watchlist: IWatchlistItem[] | undefined;
    onDelete: (watchlist: IWatchlistItem[]) => void;
};

export default function Watchlist({watchlist, onDelete}: Readonly<Props>) {
    const message = "Die Merkliste ist leer.";
    // const [listItems, setListItems] = useState<IWatchlistItem[]>();

    // function getWatchlist() {
    //     axios.get(`/api/watchlist/${user.email}`).then(res => {
    //         setListItems(res.data);
    //     }).catch(err => console.log(err));
    // }

    function deleteWatchlistItem(item: IWatchlistItem) {
        axios.delete(`/api/watchlist/${item.id}`);
    }
  
    const deleteFromWL = (item: IWatchlistItem) => {
        const newListItems = watchlist?.filter(
            (listItem: IWatchlistItem) => listItem.product.id !== item.product.id
        );
        // newListItems && setListItems(newListItems);
        newListItems && deleteWatchlistItem(item);
        newListItems && onDelete(newListItems);
    };

//   useEffect(() => {
//     getWatchlist();
//   }, []);

    return (
        <Grid container columnSpacing={3} rowSpacing={4} sx={{justifyContent: !watchlist ? "center" : "", height: "100%"}}>
            {
                watchlist && watchlist.length > 0 ?
                    watchlist.map((item: IWatchlistItem) => {
                        return <Grid
                                key={item.product.id}                                   
                                size={{xs: 12, sm: 6, md: 4, lg: 3}}
                                >
                                    <CardMain 
                                        media={{name: item.product.name, path: item.product.images.large[0]}} 
                                        content={<CardContentProduct {...item.product} />}
                                        state={item}
                                        deleteButton={true}
                                        path={`/product/${item.product.id}`} 
                                        onAction={(deleteFromWL)}
                                    />
                                </Grid>
                        })
                :
                    <NoteBig value={message} />
            }
        </Grid>
    );
}