import { Fragment } from "react";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";

type Props = {
    name: string;
    width: {number: number, unit: string};
    height: {number: number, unit: string};
    length: {number: number, unit: string};
    weight: {number: number, unit: string};
    colors: string[];
    info: string;
    price: number;
    currency: string;
};


function CardContentProduct(props: Readonly<Props>) {

    return (
        <>
            <Fragment>
                <Typography gutterBottom variant="h5" component="div" color={'#765638'}>
                    {props.name}
                </Typography>
                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                    {props.info}
                </Typography>
                <Box component={'div'} sx={{display: 'flex', alignItems: 'center'}}>
                    <Typography variant="caption" fontSize={'inherit'} sx={{color: 'text.secondary', paddingTop: '2px'}}>
                        {props.currency}
                    </Typography>
                    <Typography variant="caption" fontSize={'inherit'} sx={{color: 'text.secondary', paddingTop: '2px'}}>
                        {props.currency}
                    </Typography>
                </Box>
            </Fragment>
        </>
    );
}

export default CardContentProduct;