import type { ReactNode } from "react";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";

type Props = {
    name: string;
    value?: number | string | ReactNode;
    unit?: string;
};


export default function Details({name, value, unit}: Readonly<Props>) {

    return (
        <Stack direction={'row'} sx={{justifyContent: 'space-between', alignItems: 'center', mb: '.25rem'}}>
            <Typography component={'span'} sx={{fontWeight: 300, mb: 0}} gutterBottom>{name}</Typography>
                <div>
                    {
                        typeof value === 'number' || typeof value === 'string' ? 
                            <Typography component={'span'} sx={{fontWeight: 300}} gutterBottom>{value}</Typography> 
                        : 
                            value
                    }
                    {unit && 
                        <Typography 
                            component={'span'} 
                            sx={{fontWeight: 300, ml: '.25rem'}} 
                            gutterBottom
                        >
                            {unit}
                        </Typography>
                    }
                </div>
        </Stack>
    );
}