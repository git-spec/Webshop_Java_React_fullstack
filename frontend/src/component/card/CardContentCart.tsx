import { useState, useEffect, type ChangeEvent } from "react";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";

import type { IOrder } from "@/interface/IOrder";
import Price from "../Price";
import Details from "../Details";

// context of cart
type Props = {
    order: IOrder;
    addOrder: (cart: IOrder) => void;
};

export default function CardContentCart(props: Readonly<Props>) {
    const [amount, setAmount] = useState<string>('0');

    useEffect(() => {
        setAmount(props.order.amount.toString());
    }, [])
    
    const handleAmount = (e: ChangeEvent<HTMLInputElement>) => {
        const newAmount = e.target.value;
        setAmount(newAmount);
        // Updates order in cart.
        props.order.amount = +newAmount;
        props.addOrder(props.order);
    };

    return (
        <Stack height={'100%'} flexGrow={1}>
            <Box sx={{mb: '.5rem'}}>
            <Typography gutterBottom variant="h5" component="div" color={'#765638'} fontSize={'1rem'}>
                {props.order.product.name}
            </Typography>
            <Typography variant="body2" sx={{color: 'text.secondary', mb: '.25rem'}} fontSize={'.8rem'}>
                {props.order.product.info}
            </Typography>
            </Box>
            <Stack height={'100%'} flexDirection={'row'} justifyContent={'space-between'}>
                <Details
                    name={'Farbe'} 
                    value={props.order.color}
                    direction="column"
                />
                {/* Price */}
                <Details
                    name={'Preis'} 
                    value={
                        <Price 
                            value={props.order.price} 
                            currency={props.order.product.currency} 
                            justify={'end'} 
                        />
                    }
                    direction="column"
                />
                {/* Amount */}
                <Details 
                    name={'Menge'} 
                    direction={'column'}
                    value={<TextField 
                        id="amount" 
                        variant="outlined" 
                        size="small" 
                        type={'number'} 
                        placeholder="0"
                        sx={{width: '3.5rem'}} 
                        slotProps={{
                            htmlInput: {
                                sx: { px: '.4rem', py: '.25rem', fontWeight: 300},
                                min: 0,
                                max: 99
                            }
                        }} 
                        value={amount}
                        onChange={handleAmount}
                        required
                    />}
                />
                {/* Total */}
                <Details
                    name={'Gesamt'} 
                    value={
                        <Price 
                            value={+amount * props.order.price} 
                            currency={props?.order.product.currency ?? ''} 
                            justify={'end'} fontWeight={500} 
                        />
                    }
                    direction="column"
                    fontWeight={500}
                />
            </Stack>
        </Stack>
    );
}