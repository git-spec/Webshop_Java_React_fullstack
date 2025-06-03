import { useEffect, useState } from 'react';
import axios from 'axios';

import type { IProduct } from "../interface/IProduct.ts";
import CardMain from '../component/card/CardMain.tsx';
import CardContentProduct from '../component/card/CardContentProduct.tsx';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';


function Products() {
  const [products, setProducts] = useState<IProduct[]>();

  function getProducts() {
    axios.get('/api/products').then(res => setProducts(res.data)).catch(err => console.log(err));
  }

  useEffect(() => {
    getProducts();
  }, []);

  return (
    <Container>
        <Box sx={{paddingTop: 4}}>
            <Grid container columnSpacing={2} rowSpacing={4}>
              {
              products?.map((product: IProduct) => {
                  return <CardMain key={product.id} media={{name: product.name, path: product.images.small}} content={<CardContentProduct {...product} />} />
              })
              }
            </Grid>
        </Box>
    </Container>
  )
}

export default Products;