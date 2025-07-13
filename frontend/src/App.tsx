import { createContext, useEffect, useMemo, useState } from "react";
import {
  Route, 
  createBrowserRouter, 
  createRoutesFromElements, 
  RouterProvider
} from "react-router-dom";
import axios from "axios";

import './App.css';
import type { IOrderItem } from "./interface/IOrderItem.ts";
import type { IUserAuth } from "./interface/IUserAuth.ts";
import RootLayout from "./component/layout/RootLayout.tsx";
import Products, {getSelProducts} from "./page/Products.tsx";
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
import type { IUser } from "./interface/IUser.ts";

export const CartContext = createContext<CartContextType | undefined>(undefined);
export const UserContext = createContext<IUser | null | undefined>(undefined);
export const ProductsContext = createContext<IProduct[] | null | undefined>(undefined);

// type WatchlistItemDTO = {
//     userEmail: string;
//     product: IProduct;
// };


export default function App() {
  const [products, setProducts] = useState<IProduct[]>();
  const [cart, setCart] = useState<IOrderItem[]>([]);
  //  const value = useMemo(() => ({ cart, setCart }), [cart, setCart]);
  const [userAuth, setUserAuth] = useState<IUserAuth | null>(); 
  const [user, setUser] = useState<IUser | null>(); 
  const [listItems, setListItems] = useState<IProduct[]>();

  useEffect(() => {
    handleUser();
  }, []);

  // useEffect(() => {
  //   getProducts();
  // }, [products]);

  // const getProducts = () => {
  //   axios.get('/api/products').then(res => {
  //     setProducts(res.data);
  //   }).catch(err => console.log(err));
  // }

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

  /**
   * Gets user and watchlist.
   * @returns {Promise<void>}
   */
  const handleUser = async (): Promise<void> => {
    try {
      // Gets user by oAuth.
      const userAuth = await axios.get('/api/auth');
      setUserAuth(userAuth.data);
      const user: IUser = {
        email: userAuth.data.email,
        sub: userAuth.data.claims.sub,
        gender: userAuth.data.gender,
        givenName: userAuth.data.givenName,
        familyName: userAuth.data.familyName,
        address: {
          street: userAuth.data.address.street,
          postalCode: userAuth.data.address.postalCode,
          locality: userAuth.data.address.locality,
          region: userAuth.data.address.region,
          country: userAuth.data.address.country,
        },
        watchlist: [],
      };
      // Checks existing email address of user.
      const exist = await axios.get(`/api/user/${user.email}`);
      if (exist.data) {
        setUser(exist.data);
      // Gets watchlist.
        const items = getWatchlistItems(exist.data.watchlist);
        items.then(items => setListItems(items));
      } else {
        // Creates new user.
        const res = await axios.post('api/user', user);
        setUser(res.data);
      // Gets watchlist.
        const items = getWatchlistItems(res.data.watchlist);
        items.then(items => setListItems(items));
      }
    } catch(err) {
      console.error(err);
    }
  }

  /**
   * Gets products for watchlist.
   * @param {Array<string>} watchlist - list of products
   * @returns {Promise<Array<IProduct>>}
   */
  const getWatchlistItems = async (watchlist: Array<string>): Promise<Array<IProduct>> => {
    const res = await axios.post('/api/products', watchlist);
    return res.data;
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

  const router = createBrowserRouter(
    createRoutesFromElements(
      <>
        <Route path="/" element={<RootLayout onLogin={login} onLogout={logout} />}>
            <Route 
              path={'products/:category/:group/:family'} 
              element={<Products user={user} />} 
              loader={(request) => getSelProducts(request)}  
            />
            <Route 
              path={'products/:category/:group'} 
              element={<Products user={user} />} 
              loader={(request) => getSelProducts(request)}  
            />
            {/* <Route path={'products/:category/:group'} element={<Products products={products} user={user} watchlist={listItems} />} /> */}
            <Route path="product/:id" element={<Product addToCart={handleCart} />} />
            <Route path="cart" element={<Cart />} />
            <Route path="checkout" element={<Checkout />} />
            <Route index element={<Home />} />
            <Route element={<ProtectedRoute user={userAuth?.idToken} />}>
              <Route path="dashboard" element={
                <Dashboard 
                  user={user} 
                  products={products} 
                  watchlist={listItems} 
                  onDelete={(watchlist) => (setListItems(watchlist))} 
                />
              } />
            </Route>
        </Route>
        <Route path="*" element={<NotFound />} />
      </>
    )
  );

  return (
      <CartContext.Provider value={CartContextValue}>
        <UserContext.Provider value={UserContextValue.user}>
          {/* <ProductsContextValue.Provider value={ProductsContextValue}> */}
            <RouterProvider router={router} />
          {/* </UProductsContextValue.Provider> */}
        </UserContext.Provider>
      </CartContext.Provider>
  )
}
