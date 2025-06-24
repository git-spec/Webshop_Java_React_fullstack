import { createContext, useEffect, useMemo, useState } from "react";
import {Routes, Route} from "react-router-dom";
import axios from "axios";

import './App.css';
import type { IOrderItem } from "./interface/IOrderItem.ts";
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
import NotFound from "./page/NotFound.tsx";

export const CartContext = createContext<CartContextType | undefined>(undefined);
export const UserContext = createContext<IUserAuth | null | undefined>(undefined);
export const ProductsContext = createContext<IProduct[] | null | undefined>(undefined);

type WatchlistItemDTO = {
    userEmail: string;
    product: IProduct;
};


export default function App() {
  const [products, setProducts] = useState<IProduct[]>();
  const [cart, setCart] = useState<IOrderItem[]>([]);
  //  const value = useMemo(() => ({ cart, setCart }), [cart, setCart]);
  const [user, setUser] = useState<IUserAuth | null>(); 
  const [listItems, setListItems] = useState<IWatchlistItem[]>();

  useEffect(() => {
    handleUser();
    getProducts();
  }, []);

  // useEffect(() => {
  //   getProducts();
  // }, [products]);

  const getProducts = () => {
    axios.get('/api/products').then(res => {
      setProducts(res.data);
    }).catch(err => console.log(err));
  }

  const handleCart = (order: IOrderItem) => {
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
      setCart([...cart, order]);
    }
  }

  const updateCart = (cart: IOrderItem[]) => {
    setCart(cart);
  }

  // const updateUser = (user: IUserAuth) => {
  //   setUser(user);
  // }

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

  // const loadUser = () => {
  //     axios
  //         .get('/api/auth').then(res => {
  //           setUser(res.data);
  //         })
  //         .catch(err => setUser(null));
  // }

  // function getWatchlist() {
  //     axios.get(`/api/watchlist/${user.email}`).then(res => {
  //         setListItems(res.data);
  //     }).catch(err => console.log(err));
  // }

  const handleUser = async () => {
    try {
      const user = await axios.get('/api/auth');
      setUser(user.data);
      const watchlist = await axios.get(`/api/watchlist/${user.data.email}`);
      setListItems(watchlist.data);
    } catch(err) {
      console.error(err);
    }
  }

  // const updateCart = (product: IOrderItem) => {
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

  const CartContextValue = useMemo(() => ({ cart, updateCart }), [cart, updateCart]);
  const UserContextValue = useMemo(() => ({ user }), [user]);
  // const ProductsContextValue = useMemo(() => ({ products, updateProducts }), [products, updateProducts]);

  return (
      <CartContext.Provider value={CartContextValue}>
        <UserContext.Provider value={UserContextValue.user}>
          {/* <ProductsContextValue.Provider value={ProductsContextValue}> */}
            <Routes>
              {/*APP RootLayout*/}
              <Route element={<RootLayout onLogin={login} onLogout={logout} />}>
                <Route path={'/products/:category/:group/:family'} element={<Products products={products} user={user} watchlist={listItems}  />} />
                <Route path={'/products/:category/:group'} element={<Products products={products} user={user} watchlist={listItems} />} />
                <Route path="/product/:id" element={<Product addToCart={handleCart} />} />
                <Route path="/cart" element={<Cart />} />
                <Route path="/checkout" element={<Checkout />} />
                <Route path="/" element={<Home />} />
                <Route element={<ProtectedRoute user={user?.idToken} />}>
                  <Route path="/dashboard" element={
                    <Dashboard user={user} products={products} watchlist={listItems} onDelete={(watchlist) => (setListItems(watchlist))} />
                  } />
                </Route>
              </Route>
              <Route path="*" element={<NotFound />} />
          </Routes>
          {/* </UProductsContextValue.Provider> */}
        </UserContext.Provider>
      </CartContext.Provider>
  )
}
