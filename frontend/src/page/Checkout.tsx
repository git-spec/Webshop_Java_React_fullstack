import { useLocation } from 'react-router-dom';
import Grid from '@mui/material/Grid';

import Order from "@/component/share/Order";
import LayoutContainer from '@/component/share/LayoutContainer';
import AccordionExpand from '@/component/AccordionExpand';
import PersonDetailsForm from '@/component/PersonDetailsForm';
import { useState, type FormEvent } from 'react';
import TextField from '@mui/material/TextField';
import ButtonAction from '@/component/ButtonAction';
import PayPal from '@/component/PayPal';
import type { IOrder } from '@/interface/IOrder';
import axios from 'axios';
import Typography from '@mui/material/Typography';


export default function Checkout() {
    const location = useLocation();
    const cart: IOrder[] = location.state;
    const [orderCompleted, setOrderCompleted] = useState<boolean>();

    /**
     * Posts completed order to db.
     * @param paypalOrder - paypal object after completing order
     */
    const handleOrder = (paypalOrder: any) => {
        const body = {
            cart: cart.map(
                        (item: IOrder) => {
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
            Component: <PayPal cart={cart} onOrder={handleOrder} />
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