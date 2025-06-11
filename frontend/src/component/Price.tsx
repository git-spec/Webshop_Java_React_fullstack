import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";

import { getCurrencyIcon } from "@/util";

type Props = {
    value: number | '';
    currency: string;
    justify: string;
    fontWeight?: number;
    fontSize?: string;
    fontStyle?: string;
    color?: string;
};


export default function Price(
    {
        value, 
        currency, 
        justify, 
        fontWeight, 
        fontSize, 
        fontStyle, 
        color
    }: Readonly<Props>
) {
    return (
        <Stack direction={'row'} sx={{justifyContent: `${justify}`, color: `${color ?? 'black'}`}}>
            <Typography
                component={'span'}
                fontSize={'inherit'}
                // color={'#765638'}
                sx={
                    {
                        paddingTop: '2px',
                        fontWeight: `${fontWeight ?? 300}`,
                        fontSize: `${fontSize ?? 'inherit'}`,
                        fontStyle: `${fontStyle ?? 'inherit'}`
                    }
                }
            >
            {value}
            </Typography>
            <Typography
                component={'span'}
                fontSize={'inherit'}
                // color={'#765638'}
                sx={
                        {
                            paddingTop: '2px',
                            marginLeft: '.3rem',
                            fontWeight: `${fontWeight ?? 300}`,
                            fontSize: `${fontSize ?? 'inherit'}`,
                            fontStyle: `${fontStyle ?? 'inherit'}`
                        }
                    }
            >
                {getCurrencyIcon(currency)}
            </Typography>
        </Stack>
    );
}