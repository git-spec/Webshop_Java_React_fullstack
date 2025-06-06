import {Routes, Route} from "react-router-dom";

import './App.css';
import Products from "./page/Products.tsx";
import RootLayout from "./component/layout/RootLayout.tsx";
import Home from "./page/Home.tsx";
import Product from "./page/Product.tsx";


export default function App() {

  return (
    <Routes>
      {/*APP RootLayout*/}
      <Route element={<RootLayout />}>
        <Route path={'/products/:category/:group/:family'} element={<Products />} />
        <Route path={'/products/:category/:group'} element={<Products />} />
        <Route path="/product/:id" element={<Product />} />
        <Route path="/" element={<Home />} />
      </Route>
    </Routes>
  )
}
