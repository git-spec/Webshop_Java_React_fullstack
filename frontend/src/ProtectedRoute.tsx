import { Navigate, Outlet } from "react-router-dom";
import CircularProgress from '@mui/material/CircularProgress';
import LayoutContainer from "./component/share/LayoutContainer";
import Box from "@mui/material/Box";

type Props = {
    user: string | undefined | null;
};


export default function ProtectedRoute(props: Readonly<Props>) {
    if (props.user === undefined) {
        return (
            <LayoutContainer>
                <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', pt: '30vh' }}>
                    <CircularProgress size={'6rem'} />
                </Box>
            </LayoutContainer>
        )
    }
    return (
        props.user ? <Outlet /> : <Navigate to={'/'} />
    );
}