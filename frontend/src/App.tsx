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
import type { UserContextType } from "./type/UserContextType.ts";
import Checkout from "./page/Checkout.tsx";
import Dashboard from "./page/Dashboard.tsx";
import ProtectedRoute from "./ProtectedRoute.tsx";
import type { IProduct } from "./interface/IProduct.ts";
import NotFound from "./page/NotFound.tsx";
import type { IUser } from "./interface/IUser.ts";

export const CartContext = createContext<CartContextType | undefined>(undefined);
export const UserContext = createContext<UserContextType | undefined>(undefined);
export const ProductsContext = createContext<IProduct[] | null | undefined>(undefined);


export default function App() {
  const [products, setProducts] = useState<IProduct[]>();
  const [cart, setCart] = useState<IOrderItem[]>([]);
  //  const value = useMemo(() => ({ cart, setCart }), [cart, setCart]);
  const [userAuth, setUserAuth] = useState<IUserAuth | null>(); 
  const [user, setUser] = useState<IUser | null>(); 

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

  const updateUser = (user: IUser | null | undefined) => {
    setUser(user);
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

  /**
   * Gets user.
   * @returns {void}
   */
  const handleUser = (): void => {
    axios.get('/api/auth').then(
      res => {
        if (res.data) {
          setUserAuth(res.data);
          const user: IUser = {
            email: res.data.email,
            sub: res.data.claims.sub,
            gender: res.data.gender,
            givenName: res.data.givenName,
            familyName: res.data.familyName,
            address: {
              street: res.data.address.street,
              postalCode: res.data.address.postalCode,
              locality: res.data.address.locality,
              region: res.data.address.region,
              country: res.data.address.country,
            },
            watchlist: [],
          };
          // Checks existing email address of user.
          axios.get(`/api/user/${user.email}`).then(
            res => {
              if (res.data) {
                setUser(res.data);
              } else {
                // Creates new user.
                axios.post('api/user', user)
                  .then(res => setUser(res.data))
                  .catch(err => console.log(err));
              }
            }
          ).catch(err => console.log(err));
        }
      }
    ).catch(err => console.log(err));
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
  const UserContextValue = useMemo(() => ({ user, updateUser }), [user, updateUser]);
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
            <Route path="product/:id" element={<Product addToCart={handleCart} />} />
            <Route path="cart" element={<Cart />} />
            <Route path="checkout" element={<Checkout />} />
            <Route index element={<Home />} />
            <Route element={<ProtectedRoute user={userAuth?.idToken} />}>
              <Route path="dashboard" element={
                <Dashboard user={user} />
              } />
            </Route>
        </Route>
        <Route path="*" element={<NotFound />} />
      </>
    )
  );

  return (
      <CartContext.Provider value={CartContextValue}>
        <UserContext.Provider value={UserContextValue}>
          {/* <ProductsContextValue.Provider value={ProductsContextValue}> */}
            <RouterProvider router={router} />
          {/* </UProductsContextValue.Provider> */}
        </UserContext.Provider>
      </CartContext.Provider>
  )
}
