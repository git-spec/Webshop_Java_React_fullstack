import { createContext, useMemo, useState } from "react";
import {Routes, Route} from "react-router-dom";

import './App.css';
import type { ICart } from "./interface/ICart.ts";
import RootLayout from "./component/layout/RootLayout.tsx";
import Products from "./page/Products.tsx";
import Home from "./page/Home.tsx";
import Product from "./page/Product.tsx";
import Cart from "./page/Cart.tsx";
import type { CartContextType } from "./type/CartContextType.tsx";
import Checkout from "./page/Checkout.tsx";

export const CartContext = createContext<CartContextType | undefined>(undefined);


export default function App() {
  const [cart, setCart] = useState<ICart[]>([]);
  //  const value = useMemo(() => ({ cart, setCart }), [cart, setCart]);

  const handleCart = (product: ICart) => {
    const itemExsits = cart.find(article => article.id === product.id && article.color === product.color);

    if (itemExsits) {
      // Updates existing article to context.
      itemExsits.amount += product.amount
      setCart(
        prevArr => prevArr.map(
          item => item.id === product.id ? { ...prevArr, ...itemExsits } : item
        )
      );
    } else {
      // Adds new article to context.
      setCart([...cart, product])
    }
  }

  const updateCart = (cart: ICart[]) => {
    setCart(cart)
  }

  // const updateCart = (product: ICart) => {
  //   const itemExsits = cart.find(article => article.id === product.id && article.color === product.color);

  //   if (itemExsits) {
  //     // Updates existing article to context.
  //     itemExsits.amount = product.amount
  //     setCart(
  //       prevArr => prevArr.map(
  //         item => item.id === product.id ? { ...prevArr, ...itemExsits } : item
  //       )
  //     );
  //   } else {
  //     // Adds new article to context.
  //     setCart([...cart, product])
  //   }
  // }

  const contextValue = useMemo(() => ({ cart, updateCart }), [cart, updateCart]);

  return (
      <CartContext.Provider value={contextValue}>
        <Routes>
          {/*APP RootLayout*/}
          <Route element={<RootLayout />}>
            <Route path={'/products/:category/:group/:family'} element={<Products />} />
            <Route path={'/products/:category/:group'} element={<Products />} />
            <Route path="/product/:id" element={<Product addToCart={handleCart} />} />
            <Route path="/cart" element={<Cart />} />
            <Route path="/checkout" element={<Checkout />} />
            <Route path="/" element={<Home />} />
          </Route>
        </Routes>
      </CartContext.Provider>
  )
}
