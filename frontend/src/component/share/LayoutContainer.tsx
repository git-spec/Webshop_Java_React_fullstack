import {type ReactNode} from "react";
import Container from "@mui/material/Container";

type Props = {
    children: ReactNode;
    pt?: string | number;
};


function LayoutContainer({children, pt}: Readonly<Props>) {
    return (
        <Container sx={{px: {md: 8}, pt: `${pt}`}}>
            {children}
        </Container>
    )
}

export default LayoutContainer;