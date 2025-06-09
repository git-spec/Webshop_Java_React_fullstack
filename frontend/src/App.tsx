import { createContext, useState } from "react";
import {Routes, Route} from "react-router-dom";

import './App.css';
import type { ICart } from "./interface/ICart.ts";
import RootLayout from "./component/layout/RootLayout.tsx";
import Products from "./page/Products.tsx";
import Home from "./page/Home.tsx";
import Product from "./page/Product.tsx";
import Cart from "./page/Cart.tsx";

export const CartContext = createContext<ICart[]>([]);


export default function App() {
  const [cart, setCart] = useState<ICart[]>([]);

  const handleCart = (product: ICart) => {
    const itemExsits = cart.find(article => article.id === product.id && article.color === product.color);

    if (itemExsits) {
      // Updates existing article to context.
      itemExsits.amount += product.amount
      setCart(
        prevArr => prevArr.map(
          item => item.id === product.id ? { ...item, ...itemExsits } : item
        )
      );
    } else {
      // Adds new article to context.
      setCart([...cart, product])
    }
  }

  return (
      <CartContext value={cart}>
        <Routes>
          {/*APP RootLayout*/}
          <Route element={<RootLayout />}>
            <Route path={'/products/:category/:group/:family'} element={<Products />} />
            <Route path={'/products/:category/:group'} element={<Products />} />
            <Route path="/product/:id" element={<Product addToCart={handleCart} />} />
            <Route path="/cart" element={<Cart />} />
            <Route path="/" element={<Home />} />
          </Route>
        </Routes>
      </CartContext>
  )
}
