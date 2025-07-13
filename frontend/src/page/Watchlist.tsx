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
    watchlist: IProduct[] | undefined;
    onDelete: (watchlist: IProduct[]) => void;
};

export default function Watchlist({watchlist, onDelete}: Readonly<Props>) {
    const message = "Die Merkliste ist leer.";
    // const [listItems, setListItems] = useState<IWatchlistItem[]>();

    // function getWatchlist() {
    //     axios.get(`/api/watchlist/${user.email}`).then(res => {
    //         setListItems(res.data);
    //     }).catch(err => console.log(err));
    // }

    function deleteWatchlistItem(item: IProduct) {
        axios.delete(`/api/watchlist/${item.id}`);
    }
  
    const deleteFromWL = (item: IProduct) => {
        const newListItems = watchlist?.filter(
            (listItem: IProduct) => listItem.id !== item.id
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
                    watchlist.map((item: IProduct) => {
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