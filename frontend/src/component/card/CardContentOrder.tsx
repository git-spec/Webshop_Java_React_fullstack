import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";

import type { IOrder } from "@/interface/IOrder";
import type { IOrderItem } from "@/interface/IOrderItem";
import TableBasic from "../TableBasic";

// context of cart
type Props = {order: IOrder};

export default function CardContentOrder({order}: Readonly<Props>) {
    const header = ['Name', 'Nummer', 'Farbe', 'Preis', 'Menge', 'Gesamt'];
    let currency = order.cart[0].currency ?? '';
    
    const initialValue = 0;
    const getTotal = (order: IOrder) => {
        const totalArticles = order.cart.map(orderItem => (orderItem.amount * orderItem.price));
        return totalArticles.reduce(
            (accumulator, currentValue) => accumulator + currentValue,
            initialValue,
        );
    };

    const getTableContent = (orderItem: IOrderItem[]) => {
        return orderItem.map(article => (
            [
                article.productName,
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
                order && 
                    <>
                        <TableBasic key={order.id} header={header} content={getTableContent(order.cart)} />
                        <Stack flex={'flex'}  flexDirection={'row'} justifyContent={'end'} alignItems={'end'} gap={4} pt={1}>
                            <Stack flex={'flex'}  flexDirection={'row'} justifyContent={'end'} alignItems={'end'} gap={1}>
                                <Typography 
                                    variant="body2" 
                                    textAlign={'end'} 
                                    fontWeight={200}
                                        fontSize={'.8rem'}
                                >
                                    Währung:
                                </Typography>
                                <Typography 
                                    variant="body2" 
                                    textAlign={'end'} 
                                    fontWeight={200} 
                                    fontSize={'.8rem'}
                                >
                                    {currency}
                                </Typography>
                            </Stack>
                            <Stack flex={'flex'}  flexDirection={'row'} justifyContent={'end'} alignItems={'end'} gap={1}>
                                <Typography 
                                    textAlign={'end'} 
                                    fontWeight={200}
                                        fontSize={'.8rem'}
                                >
                                    MwSt.:
                                </Typography><Typography textAlign={'end'} fontWeight={200} fontSize={'.8rem'}>{(getTotal(order) * .19).toFixed(2)}</Typography>
                            </Stack>
                            <Stack flex={'flex'}  flexDirection={'row'} justifyContent={'end'} alignItems={'end'} gap={1}>
                                <Typography 
                                    variant="body2" 
                                    textAlign={'end'} 
                                    fontWeight={500}
                                >
                                    Total:
                                </Typography><Typography textAlign={'end'} fontWeight={500} fontSize={'1rem'}>{getTotal(order).toFixed(2)}</Typography>
                            </Stack>
                        </Stack>
                    </>
            }
        </Stack>
    );
}