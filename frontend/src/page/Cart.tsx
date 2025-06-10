import { useContext } from "react";
import Typography from "@mui/material/Typography";

import { CartContext } from "@/App";
import type { ICart } from "@/interface/ICart";
import CardContentCart from "@/component/card/CardContentCart";
import CardCart from "@/component/card/CardCart";
import LayoutContainer from "@/component/share/LayoutContainer";
import Grid from "@mui/material/Grid";
import Price from "@/component/Price";
import Details from "@/component/Details";
import ButtonAction from "@/component/ButtonAction";


export default function Cart() {
    const context = useContext(CartContext);
    if (!context) throw new Error("CartContext must be used within a CartProvider");
    const {cart, updateCart} = context;

    const addToCart = (order: ICart) => {
        const existingItem = cart.find(item => item.id === order.id);
        const newCart = existingItem && cart.map(item => {
            if (item.id === order.id) {
                item.amount = order.amount;
                return item;
            } else {
                return item;
            }
        })
        // Updates order in cart.
        newCart && newCart.length > 0 && updateCart(newCart);
    };

    const deleteFromCart = (order: ICart) => {
        const existingItem = cart.find(item => item.id === order.id);
        const newCart = existingItem && cart.filter(item => {
            if (item.id !== order.id) return item;
        })
        // Updates order in cart.
        newCart && updateCart(newCart);
    };

    const getSumOfOrders = () => {
        return cart.reduce((acc, obj) => acc + obj.amount * obj.price, 0);
    }

    return (
        <LayoutContainer>
                    {
                        cart.length > 0 ? 
                            <Grid container spacing={3}>
                                <Grid size={8}>
                                    <Typography 
                                        variant="h2" 
                                        fontFamily={'SourceSans3'} 
                                        fontWeight={500} 
                                        fontSize={'1.5rem'}
                                        marginBottom={'1.5rem'}
                                    >
                                        Warenkorb
                                    </Typography>
                                    {
                                        cart.map(
                                            (item: ICart) => {
                                                return (
                                                    <CardCart 
                                                        key={item.id} 
                                                        media={
                                                            {
                                                                name: item.name, 
                                                                path: item.images.large[0]
                                                            }
                                                        } 
                                                        content={<CardContentCart order={item} addOrder={addToCart} />} 
                                                        // content={<CardContentCart {...item} />} 
                                                        state={item}
                                                        deleteOrder={deleteFromCart}
                                                    />
                                                )
                                            }
                                        ) 
                                    }
                                </Grid>
                                <Grid size={4}>
                                    <Typography 
                                        variant="h2" 
                                        fontFamily={'SourceSans3'} 
                                        fontWeight={500} 
                                        fontSize={'1.5rem'}
                                        marginBottom={'1.5rem'}
                                    >
                                        Bestellübersicht
                                    </Typography>
                                    {
                                        cart.map(
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
                                    <Details 
                                        name={'Gesamt'} 
                                        value={
                                            <Price 
                                                value={getSumOfOrders()} 
                                                currency={cart[0].currency} 
                                                justify={'end'} 
                                            />
                                        } 
                                        fontWeight={500}
                                    />
                                    <br />
                                    <ButtonAction value={'zur Kasse'} color="success" fitContent={false} click={() => {}}  />
                                </Grid>
                            </Grid>
                        : 
                            <Typography 
                                variant="h1" 
                                textAlign={'center'} 
                                fontFamily={'SourceSans3'} 
                                fontWeight={500} fontSize={'2rem'} 
                                color="lightgray"
                                marginTop={12}
                            >
                                Der Warenkorb ist leer.
                            </Typography>
                    }
        </LayoutContainer>
    );
}