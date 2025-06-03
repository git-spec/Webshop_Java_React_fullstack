import {type ReactNode} from "react";
import Container from "@mui/material/Container";

type Props = {
    children: ReactNode;
};


function LayoutContainer({children}: Readonly<Props>) {
    return (
        <Container>
            {children}
        </Container>
    )
}

export default LayoutContainer;