import { Navigate, Outlet } from "react-router-dom";

type Props = {
    user: string | undefined | null;
};


export default function ProtectedRoute(props: Readonly<Props>) {
    if (props.user === undefined) {
        return <h3>loading...</h3>
    }
    return (
        props.user ? <Outlet /> : <Navigate to={'/'} />
    );
}