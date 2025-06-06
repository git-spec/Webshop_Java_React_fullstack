import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";

import { getCurrencyIcon } from "@/util";

type Props = {
    value: number | '';
    currency: string;
    justify: string;
    underline?: boolean;
};


export default function Price({value, currency, justify, underline}: Readonly<Props>) {
    return (
        <Stack direction={'row'} sx={{justifyContent: `${justify}`}}>
            <Typography 
                component={'span'} 
                fontSize={'inherit'} 
                sx={
                    {
                        paddingTop: '2px', textDecoration: underline ? 'underline' : 'none', 
                        textUnderlineOffset: underline ? '3px' : 'unset'
                    }
                }
            >
            {value}
            </Typography>
            <Typography component={'span'} fontSize={'inherit'} sx={{paddingTop: '2px', marginLeft: '.3rem'}}>
                {getCurrencyIcon(currency)}
            </Typography>
        </Stack>
    );
}