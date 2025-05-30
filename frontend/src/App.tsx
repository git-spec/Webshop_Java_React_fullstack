import {Routes, Route} from "react-router-dom";

import './App.css';
import Products from "./page/Products.tsx";


function App() {

  return (
    <>
      <p style={{textAlign: 'center', marginTop: '8rem', fontFamily: 'SourceSerif4', fontWeight: 200, fontStyle: 'normal', fontSize: 24, letterSpacing: '.03em'}}>
          Hello World!
      </p>
      <Routes>
            <Route path={'/products/:categotry'} element={<Products />} />
            <Route path={'/products/:group'} element={<Products />} />
            <Route path={'/products/:family'} element={<Products />} />
            <Route path="/products" element={<Products />} />
      </Routes>
    </>
  )
}

export default App
