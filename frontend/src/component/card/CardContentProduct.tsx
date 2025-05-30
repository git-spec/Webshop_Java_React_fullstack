import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";

import type { IProduct } from "@/interface/IProduct";
import { getCurrencyIcon } from "@/util";


function CardContentProduct(props: Readonly<IProduct>) {

    return (
        <>
            <Typography gutterBottom variant="h5" component="div" color={'#765638'}>
                {props.name}
            </Typography>
            <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                {props.info}
            </Typography>
            <Box component={'div'} sx={{display: 'flex', alignItems: 'center', marginTop: 1}}>
                <Typography variant="caption" fontSize={'inherit'} sx={{color: 'text.secondary', paddingTop: '2px'}}>
                    {props.price}
                </Typography>
                <Typography variant="caption" fontSize={'inherit'} sx={{color: 'text.secondary', paddingTop: '2px', marginLeft: '.3rem'}}>
                    {getCurrencyIcon(props.currency)}
                </Typography>
            </Box>
        </>
    );
}

export default CardContentProduct;