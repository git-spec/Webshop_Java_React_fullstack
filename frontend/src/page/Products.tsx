import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
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

type Props = {
    user?: IUserAuth;
    watchlist: IWatchlistItem[] | undefined;
};

function Products({user , watchlist}: Readonly<Props>) {
  const [products, setProducts] = useState<IProduct[]>();
  const { category, group, family } = useParams();

  const getProducts = () => {
    axios.get('/api/products').then(res => {
      const filteredProducts = res.data.filter(
        (product: IProduct) => 
          product.category.toLowerCase() === category &&
          product.group.toLowerCase() === group &&
          product.family.toLowerCase() === family
      );
      setProducts(filteredProducts);
    }).catch(err => console.log(err));
  }

  const addToWatchlist = (product: IProduct) => {
        if (user && user.email) {
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

  useEffect(() => {
    getProducts();
  }, []);

  return (
    <LayoutContainer>
        <Box sx={{paddingTop: 4}}>
            <Grid container columnSpacing={2} rowSpacing={4}>
              {
                products?.map((product: IProduct) => {
                      return <Grid
                          key={product.id}
                          size={{xs: 12, sm: 6, md: 4, lg: 4}}
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
}

export default Products;