import { useState, useEffect, type ChangeEvent } from "react";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";

import type { IOrder } from "@/interface/IOrder";
import type { IOrderItem } from "@/interface/IOrderItem";
import type { IProduct } from "@/interface/IProduct";
import Price from "../Price";
import Details from "../Details";
import TableBasic from "../TableBasic";

// context of cart
type Props = {
    order: IOrder;
    products: IProduct[];
};

export default function CardContentOrder({order, products}: Readonly<Props>) {
    const [amount, setAmount] = useState<string>('0');
    const [newOrder, setNewOrder] = useState<IOrder>();
    const header = ['Name', 'Nummer', 'Farbe', 'Preis', 'Menge', 'Gesamt'];

    useEffect(() => {
        // order && setAmount(order.amount.toString());
        order.cart.forEach(el => {
            const product = getProduct(el.productID);
            product && (el.product = product);
        });
        setNewOrder(order);
    }, [])
    
    // const handleAmount = (e: ChangeEvent<HTMLInputElement>) => {
    //     if (!Array.isArray(order)) {
    //         const newAmount = e.target.value;
    //         setAmount(newAmount);
    //         // Updates order in cart.
    //         order.amount = +newAmount;
    //     }
    // };
    
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
                newOrder?.cart.map(((article: IOrderItem) => {
                    return (
                        // <Stack key={article.productID} height={'100%'} flexDirection={'row'} justifyContent={'space-between'}>
                        //     {/* Article */} 
                        //     <Details
                        //         name={article.product.name} 
                        //         value={<span style={{fontWeight: '300'}}>{article.productID}</span>}
                        //         direction="column"
                        //         fontWeight={500}
                        //     />
                        //     {/* Color */}
                        //     <Details
                        //         name={'Farbe'} 
                        //         value={article.color}
                        //         direction="column"
                        //     />
                        //     {/* Price */}
                        //     <Details
                        //         name={'Preis'} 
                        //         value={
                        //             <Price 
                        //                 value={article.price} 
                        //                 currency={article.product.currency} 
                        //                 justify={'end'} 
                        //             />
                        //         }
                        //         direction="column"
                        //     />
                        //     {/* Amount */}
                        //     <Details 
                        //         name={'Menge'} 
                        //         direction={'column'}
                        //         value={article.amount}
                        //     />
                        //     {/* Total */}
                        //     <Details
                        //         name={'Gesamt'} 
                        //         value={
                        //             <Price 
                        //                 value={+amount * article.price} 
                        //                 currency={article.product.currency ?? ''} 
                        //                 justify={'end'} 
                        //             />
                        //         }
                        //         direction="column"
                        //     />
                        // </Stack>
                        <>
                            <TableBasic key={newOrder.id} header={header} content={getTableContent(newOrder.cart)} />
                            <Stack flex={'flex'}  flexDirection={'row'} justifyContent={'end'} alignItems={'end'} gap={4}>
                                <Stack flex={'flex'}  flexDirection={'row'} justifyContent={'end'} alignItems={'end'} gap={1}>
                                    <Typography 
                                        variant="body2" 
                                        textAlign={'end'} 
                                        fontWeight={200}
                                    >
                                        Währung:
                                    </Typography><Typography variant="body2" textAlign={'end'} fontWeight={200}>{article.product.currency}</Typography>
                                </Stack>
                                <Stack flex={'flex'}  flexDirection={'row'} justifyContent={'end'} alignItems={'end'} gap={1}>
                                    <Typography 
                                        textAlign={'end'} 
                                        fontWeight={200}
                                    >
                                        MwSt.:
                                    </Typography><Typography textAlign={'end'} fontWeight={200}>{(getTotal(newOrder) * .19).toFixed(2)}</Typography>
                                </Stack>
                                <Stack flex={'flex'}  flexDirection={'row'} justifyContent={'end'} alignItems={'end'} gap={1}>
                                    <Typography 
                                        variant="body2" 
                                        textAlign={'end'} 
                                        fontWeight={500}
                                    >
                                        Total:
                                    </Typography><Typography textAlign={'end'} fontWeight={500}>{getTotal(newOrder).toFixed(2)}</Typography>
                                </Stack>
                            </Stack>
                        </>
                    );
                }))
            }
        </Stack>
    );
}