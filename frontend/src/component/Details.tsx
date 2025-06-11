import type { ReactNode } from "react";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";

type Props = {
    name: string;
    value?: number | string | ReactNode;
    unit?: string;
    direction?: 'row' | 'column';
    fontWeight?: number;
    fontSize?: string;
    fontStyle?: string;
};


export default function Details({name, value, unit, direction, fontWeight, fontSize, fontStyle}: Readonly<Props>) {

    return (
        <Stack 
            flexDirection={`${direction ?? 'row'}`} 
            sx={
                {
                    height: `${direction ? '100%' : 'auto'}`,
                    justifyContent: 'space-between', 
                    alignItems: `${direction ? 'space-between' : 'center'}`, 
                    mb: '.25rem'
                }
            }
        >
            <Typography 
                component={'span'} 
                sx={
                        {
                            fontWeight: `${fontWeight ?? 300}`, 
                            fontSize: `${fontSize ?? 'inherit'}`,
                            fontStyle: `${fontStyle ?? 'inherit'}`,
                            mb: 0
                        }
                    } 
                gutterBottom
            >
                {name}
            </Typography>
                <div>
                    {
                        typeof value === 'number' || typeof value === 'string' ? 
                            <Typography component={'span'} sx={{fontWeight: `${fontWeight ?? 300}`}} gutterBottom>{value}</Typography> 
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