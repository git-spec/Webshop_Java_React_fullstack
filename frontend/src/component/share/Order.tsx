import Typography from "@mui/material/Typography";

import type { ICart } from "@/interface/ICart";
import Details from "../Details";
import Price from "../Price";
import ButtonAction from "../ButtonAction";

type Props = {
    orders: ICart[];
    checkout: boolean;
    onAction: () => void;
};

export default function Order({orders, checkout, onAction}: Readonly<Props>) {
    const tax = 19;

    const getSumOfOrders = () => {
    return orders.reduce((acc, obj) => acc + obj.amount * obj.price, 0);
}

    return (
        <>
            <Typography 
                variant="h2" 
                fontFamily={'SourceSans3'} 
                fontWeight={500} 
                fontSize={'1.5rem'}
                marginBottom={'1.5rem'}
            >
               {checkout ? 'Bestellung' : 'Bestellübersicht'}
            </Typography>
            {
                orders.map(
                    order => 
                        <Details 
                            key={order.id}
                            name={`${order.name}\u00A0\u00A0\u00A0\u00A0x ${order.amount}`} 
                            value={
                                <Price 
                                    value={order.price * order.amount} 
                                    currency={order.currency} 
                                    justify={'end'} 
                                />
                            } 
                        />
                    )
            }
            {
                checkout && <Details 
                    name={'inkl. Steuern'} 
                    value={
                        <Price 
                            value={getSumOfOrders() / 100 * tax} 
                            currency={orders[0].currency} 
                            justify={'end'} 
                            fontSize={'.8rem'}
                        />
                    } 
                    fontSize={'.8rem'}
                />
            }
            <Details 
                name={'Gesamt'} 
                value={
                    <Price 
                        value={getSumOfOrders()} 
                        currency={orders[0].currency} 
                        justify={'end'} 
                        fontWeight={500}
                    />
                } 
                fontWeight={500}
            />
            <br />
            <ButtonAction value={checkout ? 'kaufen' : 'zur Kasse'} color="success" fitContent={false} click={onAction}  />
        </>
    );
}