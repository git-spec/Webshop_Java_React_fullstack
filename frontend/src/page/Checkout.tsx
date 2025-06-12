import { useLocation } from 'react-router-dom';
import Grid from '@mui/material/Grid';

import Order from "@/component/share/Order";
import LayoutContainer from '@/component/share/LayoutContainer';
import AccordionExpand from '@/component/AccordionExpand';
import PersonDetailsForm from '@/component/PersonDetailsForm';
import type { FormEvent } from 'react';
import TextField from '@mui/material/TextField';
import ButtonAction from '@/component/ButtonAction';
import PayPal from '@/component/PayPal';


export default function Checkout() {
    const location = useLocation();
    
    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
    };

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
            Component: PayPal
        }
    ];

    return (
        <LayoutContainer>
            <form onSubmit={handleSubmit}>
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
        </LayoutContainer>
    );
}