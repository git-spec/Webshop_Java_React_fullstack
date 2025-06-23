import { Outlet } from "react-router-dom";

import Header from "../ui/Header";

type Props = {
    onLogin: () => void;
    onLogout: () => void;
};


export default function RootLayout(props: Readonly<Props>) {    
    return (
        <>
            <Header onLogin={props.onLogin} onLogout={props.onLogout} />
            <main>
                <Outlet />
            </main>
            <footer></footer>
        </>
    );
}
