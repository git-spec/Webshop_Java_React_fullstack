import {useEffect, useState, type ReactNode} from "react";
import Container from "@mui/material/Container";

type Props = {
    children: ReactNode;
    padding?: boolean;
    paddingTop?: string | number;
};


export default function LayoutContainer({children, padding, paddingTop}: Readonly<Props>) {
    const [pt, setPt] = useState<string | number>('4rem');

    useEffect(() => {
        handlePt();
    }, [paddingTop]);

    const handlePt = () => {
        paddingTop && setPt(paddingTop);
    }

    return (
        <Container sx={{px: {md: 8}, pt: `${padding ? pt : ''}`}}>
            {children}
        </Container>
    )
}