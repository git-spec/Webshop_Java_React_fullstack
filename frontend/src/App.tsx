import {Routes, Route} from "react-router-dom";

import './App.css';
import Products from "./page/Products.tsx";
import RootLayout from "./component/layout/RootLayout.tsx";


function App() {

  return (
    <Routes>
      {/*APP RootLayout*/}
      <Route element={<RootLayout />}>
        <Route path={'/products/:category/:group/:family'} element={<Products />} />
        <Route path={'/products/:category/:group'} element={<Products />} />
        <Route path={'/products/:category'} element={<Products />} />
        <Route path="/products" element={<Products />} />
      </Route>
    </Routes>
  )
}

export default App
