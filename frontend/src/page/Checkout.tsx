import { useContext, useEffect, useState, type FormEvent } from 'react';
import axios from 'axios';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';

import Order from "@/component/share/Order";
import LayoutContainer from '@/component/share/LayoutContainer';
import AccordionExpand from '@/component/AccordionExpand';
import PersonDetailsForm from '@/component/PersonDetailsForm';
import ButtonAction from '@/component/ButtonAction';
import PayPal from '@/component/PayPal';
import type { IOrderItem } from '@/interface/IOrderItem';

import { CartContext } from '@/App';


export default function Checkout() {
    const context = useContext(CartContext);
    const [orderCompleted, setOrderCompleted] = useState<boolean>();

    useEffect(() => {
        orderCompleted && context?.updateCart([]);
    }, [orderCompleted]);

    /**
     * Posts completed order to db.
     * @param paypalOrder - paypal object after completing order
     */
    const handleOrder = (paypalOrder: any) => {
        const body = {
            cart: context?.cart.map(
                        (item: IOrderItem) => {
                            return {
                                productID: item.productID,
                                color: item.color,
                                amount: item.amount,
                                price: item.price
                            }
                        }
                    ),
            paypal: paypalOrder
        };
        axios.post(`/api/order/completed`, body).then(res => {
            res.status === 200 && setOrderCompleted(true);
        }).catch(err => console.log(err));
    }
    
    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
    };
    // components for accordion
    const Accs = [
        {
            id: 'contact',
            sumary: 'Kontakt',
            Component: <TextField label={'E-Mail-Adresse'} size={'small'} fullWidth />
        },
        {
            id: 'address',
            sumary: 'Adresse',
            Component: PersonDetailsForm
        },
        {
            id: 'payment',
            sumary: 'Zahlung',
            Component: <PayPal cart={context?.cart} onOrder={handleOrder} />
        }
    ];

    return (
        <LayoutContainer>
            { 
                !orderCompleted ? <form onSubmit={handleSubmit}>
                        <Grid container spacing={4}>
                            <Grid size={7}>
                                {
                                    Accs.map(acc => <AccordionExpand key={acc.id} sumary={acc.sumary} Component={acc.Component} />)
                                }                    
                            </Grid>
                            <Grid size={5}>  
                                <Grid size={12} sx={{mb: 2}}>             
                                    <Order orders={location.state} checkout={true} />
                                </Grid> 
                                <Grid size={12}>
                                    <ButtonAction type='submit' value={'kaufen'} color="success" fitContent={false}  />
                                </Grid>
                            </Grid>
                        </Grid>
                    </form>
                : 
                    <Typography 
                        variant="h1" 
                        textAlign={'center'} 
                        fontFamily={'SourceSans3'} 
                        fontWeight={500} fontSize={'2rem'} 
                        color="lightgray"
                        marginTop={12}
                    >
                        Vielen Dank für Ihren Einkauf!
                    </Typography>
            }
        </LayoutContainer>
    );
}