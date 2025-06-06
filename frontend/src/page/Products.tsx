import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';

import type { IProduct } from "../interface/IProduct.ts";
import CardMain from '../component/card/CardMain.tsx';
import CardContentProduct from '../component/card/CardContentProduct.tsx';


function Products() {
  const [products, setProducts] = useState<IProduct[]>();
  const { category, group, family } = useParams();

  function getProducts() {
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

  useEffect(() => {
    getProducts();
  }, []);

  return (
    <Container>
        <Box sx={{paddingTop: 4}}>
            <Grid container columnSpacing={2} rowSpacing={4}>
              {
              products?.map((product: IProduct) => {
                  return <CardMain 
                    key={product.id} 
                    media={{name: product.name, path: product.images.small}} 
                    content={<CardContentProduct {...product} />}
                    state={product}
                    actionAreaPath={`/product/${product.id}`} 
                  />
              })
              }
            </Grid>
        </Box>
    </Container>
  )
}

export default Products;