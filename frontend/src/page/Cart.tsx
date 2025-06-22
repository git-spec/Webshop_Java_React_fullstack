import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import Typography from "@mui/material/Typography";

import { CartContext } from "@/App";
import type { IOrderItem } from "@/interface/IOrderItem";
import CardContentCart from "@/component/card/CardContentCart";
import CardCart from "@/component/card/CardCart";
import LayoutContainer from "@/component/share/LayoutContainer";
import Grid from "@mui/material/Grid";
import Order from "@/component/share/Order";
import ButtonAction from "@/component/ButtonAction";
import NoteBig from "@/component/share/NoteBig";


export default function Cart() {
    const message = "Der Warenkorb ist leer.";
    const navigate = useNavigate();
    const context = useContext(CartContext);
    if (!context) throw new Error("CartContext must be used within a CartProvider");
    const {cart, updateCart} = context;

    const addToCart = (order: IOrderItem) => {
        const existingItem = cart.find(item => item.productID === order.productID);
        const newCart = existingItem && cart.map(item => {
            if (item.productID === order.productID) {
                item.amount = order.amount;
                return item;
            } else {
                return item;
            }
        })
        // Updates order in cart.
        newCart && newCart.length > 0 && updateCart(newCart);
    };

    const deleteFromCart = (order: IOrderItem) => {
        const existingItem = cart.find(item => item.productID === order.productID);
        const newCart = existingItem && cart.filter(item => {
            if (item.productID !== order.productID) return item;
        })
        // Updates order in cart.
        newCart && updateCart(newCart);
    };

    return (
        <LayoutContainer>
                    {
                        cart.length > 0 ? 
                            <Grid container spacing={4}>
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
                                            (item: IOrderItem) => {
                                                return (
                                                    <CardCart 
                                                        key={item.productID} 
                                                        media={
                                                            {
                                                                name: item.product.name, 
                                                                path: item.product.images.large[0]
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
                                    <Grid size={12} sx={{mb: 2}}>             
                                        <Order orders={cart} checkout={false} />
                                    </Grid> 
                                    <Grid size={12}>
                                        <ButtonAction value={'zur Kasse'} color="success" click={() => {navigate('/checkout')}} />
                                    </Grid>
                                </Grid>
                            </Grid>
                        : 
                            <NoteBig value={message} />
                    }
        </LayoutContainer>
    );
}