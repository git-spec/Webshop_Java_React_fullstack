import { Outlet } from "react-router-dom";

import Header from "../ui/Header";


export default function RootLayout() {    
    return (
        <>
            <Header />
            <main style={{paddingTop: '2rem'}}>
                <Outlet />
            </main>
            <footer></footer>
        </>
    );
}
