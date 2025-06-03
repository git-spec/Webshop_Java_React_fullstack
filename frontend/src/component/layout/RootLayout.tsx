import { Outlet } from "react-router-dom";

import LayoutContainer from "../share/LayoutContainer";
import Header from "../ui/Header";


function RootLayout() {    
    return (
        <>
            <Header />
            <main>
                <Outlet />
            </main>
            <footer></footer>
        </>
    );
}

export default RootLayout;