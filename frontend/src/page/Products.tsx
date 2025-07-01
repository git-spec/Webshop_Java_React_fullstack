import { useLoaderData, type LoaderFunctionArgs } from 'react-router-dom';
import axios from 'axios';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import StarsIcon from '@mui/icons-material/Stars';

import type { IProduct } from "../interface/IProduct.ts";
import CardMain from '../component/card/CardMain.tsx';
import CardContentProduct from '../component/card/CardContentProduct.tsx';
import LayoutContainer from '@/component/share/LayoutContainer.tsx';
import type { IUserAuth } from '@/interface/IUserAuth.ts';
import type { IWatchlistItemDTO } from '@/interface/IWatchlistItemDTO.ts';
import type { IWatchlistItem } from '@/interface/IWatchlistItem.ts';
import { getUrl } from '@/util/index.ts';

type Props = {
    user: IUserAuth | null | undefined;
    watchlist: IWatchlistItem[] | undefined;
};

export const getSelProducts = async ({request}: LoaderFunctionArgs) => {
  const url = new URL(request.url);
  if (url.pathname.includes('/products')) {
    // for request by navlink
    const response = await axios.get(`/api${url.pathname}`);
    return response.data;
  } else {
    // for direct request
    const path = `/api${getUrl()}`;
    const response = await axios.get(path);
    return response.data;
  }
}


export default function Products({user , watchlist}: Readonly<Props>) {
  const products: IProduct[] | undefined = useLoaderData();

  const addToWatchlist = (product: IProduct) => {
        if (user?.email) {
          const itemExists = watchlist?.some(listItem => product.id === listItem.product.id);
          if (!itemExists) {
              const body: IWatchlistItemDTO = {
                userEmail: user.email,
                product: product,
              };
            axios.post("/api/watchlist", body).then(res => {
              console.log(res.data);
              
            });
          }
        }
  }

  return (
    <LayoutContainer padding={true}>
        <Box sx={{paddingTop: 4}}>
            <Grid container columnSpacing={2} rowSpacing={4}>
              {
                products?.map((product: IProduct) => {
                      return <Grid
                          key={product.id}
                          size={{xs: 12, sm: 6, md: 4, lg: 3}}
                      >
                        <CardMain
                          media={{name: product.name, path: product.images.large[0]}} 
                          content={<CardContentProduct {...product} />}
                          state={product}
                          iconButton={!!user}
                          icon={<StarsIcon sx={{color: "#D7B76F"}} />}
                          path={`/product/${product.id}`} 
                          onAction={addToWatchlist}
                        />
                    </Grid>
                })
              }
            </Grid>
        </Box>
    </LayoutContainer>
  )
};