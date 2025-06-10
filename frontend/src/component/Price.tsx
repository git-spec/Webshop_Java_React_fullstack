import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";

import { getCurrencyIcon } from "@/util";

type Props = {
    value: number | '';
    currency: string;
    justify: string;
    fontWeight?: number;
    color?: string;
};


export default function Price({value, currency, justify, fontWeight, color}: Readonly<Props>) {
    return (
        <Stack direction={'row'} sx={{justifyContent: `${justify}`, color: `${color ?? 'black'}`}}>
            <Typography
                component={'span'}
                fontSize={'inherit'}
                // color={'#765638'}
                sx={
                    {
                        paddingTop: '2px',
                        fontWeight: `${fontWeight ?? 300}`
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
                            fontWeight: `${fontWeight ?? 300}`
                        }
                    }
            >
                {getCurrencyIcon(currency)}
            </Typography>
        </Stack>
    );
}