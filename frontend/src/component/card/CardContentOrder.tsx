import { useState, useEffect } from "react";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";

import type { IOrder } from "@/interface/IOrder";
import type { IOrderItem } from "@/interface/IOrderItem";
import type { IProduct } from "@/interface/IProduct";
import TableBasic from "../TableBasic";

// context of cart
type Props = {
    order: IOrder;
    products: IProduct[];
};

export default function CardContentOrder({order, products}: Readonly<Props>) {
    const [newOrder, setNewOrder] = useState<IOrder>();
    const header = ['Name', 'Nummer', 'Farbe', 'Preis', 'Menge', 'Gesamt'];

    useEffect(() => {
        order.cart.forEach(el => {
            const product = getProduct(el.productID);
            product && (el.product = product);
        });
        setNewOrder(order);
    }, [])
    
    const initialValue = 0;
    const getTotal = (order: IOrder) => {
        const totalArticles = order.cart.map(orderItem => (orderItem.amount * orderItem.price));
        return totalArticles.reduce(
            (accumulator, currentValue) => accumulator + currentValue,
            initialValue,
        );
    };

    const getProduct = (productID: string) => {
        const product = products.find(el => el.id === productID);
        return product;
    }

    const getTableContent = (order: IOrderItem[]) => {
        return order.map(article => (
            [
                article.product.name, 
                article.productID, 
                article.color, 
                article.price, 
                article.amount, 
                (article.amount * article.price)
            ]
        ))
    }

    return (
        <Stack height={'100%'} flexGrow={1}>
            {
                newOrder && 
                    <>
                        <TableBasic key={newOrder.id} header={header} content={getTableContent(newOrder.cart)} />
                        <Stack flex={'flex'}  flexDirection={'row'} justifyContent={'end'} alignItems={'end'} gap={4} pt={1}>
                            <Stack flex={'flex'}  flexDirection={'row'} justifyContent={'end'} alignItems={'end'} gap={1}>
                                <Typography 
                                    variant="body2" 
                                    textAlign={'end'} 
                                    fontWeight={200}
                                        fontSize={'.8rem'}
                                >
                                    Währung:
                                </Typography><Typography variant="body2" textAlign={'end'} fontWeight={200} fontSize={'.8rem'}>{newOrder.cart[0].product.currency}</Typography>
                            </Stack>
                            <Stack flex={'flex'}  flexDirection={'row'} justifyContent={'end'} alignItems={'end'} gap={1}>
                                <Typography 
                                    textAlign={'end'} 
                                    fontWeight={200}
                                        fontSize={'.8rem'}
                                >
                                    MwSt.:
                                </Typography><Typography textAlign={'end'} fontWeight={200} fontSize={'.8rem'}>{(getTotal(newOrder) * .19).toFixed(2)}</Typography>
                            </Stack>
                            <Stack flex={'flex'}  flexDirection={'row'} justifyContent={'end'} alignItems={'end'} gap={1}>
                                <Typography 
                                    variant="body2" 
                                    textAlign={'end'} 
                                    fontWeight={500}
                                >
                                    Total:
                                </Typography><Typography textAlign={'end'} fontWeight={500} fontSize={'1rem'}>{getTotal(newOrder).toFixed(2)}</Typography>
                            </Stack>
                        </Stack>
                    </>
            }
        </Stack>
    );
}