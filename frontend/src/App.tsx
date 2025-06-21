import { createContext, useEffect, useMemo, useState } from "react";
import {Routes, Route} from "react-router-dom";
import axios from "axios";

import './App.css';
import type { IOrder } from "./interface/IOrder.ts";
import type { IUserAuth } from "./interface/IUserAuth.ts";
import RootLayout from "./component/layout/RootLayout.tsx";
import Products from "./page/Products.tsx";
import Home from "./page/Home.tsx";
import Product from "./page/Product.tsx";
import Cart from "./page/Cart.tsx";
import type { CartContextType } from "./type/CartContextType.tsx";
import Checkout from "./page/Checkout.tsx";
import Dashboard from "./page/Dashboard.tsx";
import ProtectedRoute from "./ProtectedRoute.tsx";
import type { IProduct } from "./interface/IProduct.ts";
import type { IWatchlistItem } from "./interface/IWatchlistItem.ts";

export const CartContext = createContext<CartContextType | undefined>(undefined);
export const UserContext = createContext<IUserAuth | null | undefined>(undefined);

type WatchlistItemDTO = {
    userEmail: string;
    product: IProduct;
};


export default function App() {
  const [cart, setCart] = useState<IOrder[]>([]);
  //  const value = useMemo(() => ({ cart, setCart }), [cart, setCart]);
  const [user, setUser] = useState<IUserAuth | undefined | null>(undefined); 
  const [listItems, setListItems] = useState<IWatchlistItem[]>();

  useEffect(() => {
      loadUser();
      user && getWatchlist();
  }, [user]);

  const handleCart = (order: IOrder) => {
    const itemExsits = cart.find(article => article.productID === order.productID && article.color === order.color);

    if (itemExsits) {
      // Updates existing article to context.
      itemExsits.amount += order.amount
      setCart(
        prevArr => prevArr.map(
          item => item.productID === order.productID ? { ...prevArr, ...itemExsits } : item
        )
      );
    } else {
      // Adds new article to context.
      setCart([...cart, order])
    }
  }

  const updateCart = (cart: IOrder[]) => {
    setCart(cart)
  }

  function login() {
      const host: string = window.location.host === 'localhost:5173'
            ? 
              'http://localhost:8080' 
          :   window.location.origin
      ;
      window.open(host + '/oauth2/authorization/google', '_self')
  }

  function logout() {
      const host: string = window.location.host === 'localhost:5173'
            ? 
              'http://localhost:8080' 
          :   window.location.origin
      ;
      window.open(host + '/logout', '_self')
  }

  const loadUser = () => {
      axios
          .get('/api/auth').then(res => {
            setUser(res.data);
          })
          .catch(err => setUser(null));
  }

  function getWatchlist() {
      axios.get(`/api/watchlist/${user.email}`).then(res => {
          setListItems(res.data);
      }).catch(err => console.log(err));
  }

  // const updateCart = (product: IOrder) => {
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

  const CartcontextValue = useMemo(() => ({ cart, updateCart }), [cart, updateCart]);
  // const UsercontextValue = useMemo(() => ({ user, updateUser }), [user, updateUser]);

  return (
      <CartContext.Provider value={CartcontextValue}>
        {/* <UsercontextValue.Provider value={UsercontextValue}> */}
          <Routes>
            {/*APP RootLayout*/}
            <Route element={<RootLayout onLogin={login} onLogout={logout} />}>
              <Route path={'/products/:category/:group/:family'} element={<Products user={user} watchlist={listItems}  />} />
              <Route path={'/products/:category/:group'} element={<Products user={user} watchlist={listItems} />} />
              <Route path="/product/:id" element={<Product addToCart={handleCart} />} />
              <Route path="/cart" element={<Cart />} />
              <Route path="/checkout" element={<Checkout />} />
              <Route path="/" element={<Home />} />
              <Route element={<ProtectedRoute user={user} />}>
                <Route path="/dashboard" element={
                  <Dashboard watchlist={listItems} onDelete={(watchlist) => (setListItems(watchlist))} />
                } />
              </Route>
            </Route>
          </Routes>
        {/* </UsercontextValue.Provider> */}
      </CartContext.Provider>
  )
}
