import Typography from "@mui/material/Typography";

import type { IProduct } from "@/interface/IProduct";
import Price from "../Price";
import Stack from "@mui/material/Stack";
import Box from "@mui/material/Box";


export default function CardContentProduct(props: Readonly<IProduct>) {

    return (
        <Stack flexDirection={'column'} height={'100%'} justifyContent={'space-between'}>
            <Box>
            <Typography gutterBottom variant="h5" component="div" color={'#765638'} fontSize={'1rem'}>
                {props.name}
            </Typography>
            <Typography variant="body2" sx={{color: 'text.secondary', mb: '.25rem'}} fontSize={'.8rem'}>
                {props.info}
            </Typography>
            </Box>
            <Price value={props.price} currency={props.currency} justify={'end'} />
        </Stack>
    );
}