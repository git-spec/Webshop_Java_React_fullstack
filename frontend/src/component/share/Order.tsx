import Typography from "@mui/material/Typography";

import type { IOrderItem } from "@/interface/IOrderItem";
import Details from "../Details";
import Price from "../Price";

type Props = {
    orders: IOrderItem[];
    checkout: boolean;
};

export default function Order({orders, checkout}: Readonly<Props>) {
    const tax = 19;

    const getSumOfOrders = () => {
    if (orders) {
        return orders.reduce((acc, obj) => acc + obj.amount * obj.price, 0);
    } else {
        return 0;
    }
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
                orders?.map(
                    order => 
                        <Details 
                            key={order.productID}
                            name={`${order.product.name}\u00A0\u00A0\u00A0\u00A0x ${order.amount}`} 
                            value={
                                <Price 
                                    value={order.price * order.amount} 
                                    currency={order.product.currency} 
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
                            currency={orders[0]?.product.currency ?? ''} 
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
                        currency={orders[0]?.product.currency ?? ''} 
                        justify={'end'} 
                        fontWeight={500}
                    />
                } 
                fontWeight={500}
            />
        </>
    );
}