import Typography from "@mui/material/Typography";

import type { IProduct } from "@/interface/IProduct";
import Price from "../Price";


function CardContentProduct(props: Readonly<IProduct>) {

    return (
        <>
            <Typography gutterBottom variant="h5" component="div" color={'#765638'}>
                {props.name}
            </Typography>
            <Typography variant="body2" sx={{color: 'text.secondary', mb: '.25rem'}}>
                {props.info}
            </Typography>
            <Price value={props.price} currency={props.currency} justify={'start'} />
        </>
    );
}

export default CardContentProduct;