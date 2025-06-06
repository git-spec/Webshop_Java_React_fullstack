import type { ReactNode } from "react";
import Box from "@mui/material/Box";

type Props = {
    children: ReactNode;
};



export default function PragraphContainer({children}: Readonly<Props>) {

    return (
        <Box component={'div'} sx={{mb: 2}}>
            {children}
        </Box>
    );
}