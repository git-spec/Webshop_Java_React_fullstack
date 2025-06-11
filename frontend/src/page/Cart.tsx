import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import Typography from "@mui/material/Typography";

import { CartContext } from "@/App";
import type { ICart } from "@/interface/ICart";
import CardContentCart from "@/component/card/CardContentCart";
import CardCart from "@/component/card/CardCart";
import LayoutContainer from "@/component/share/LayoutContainer";
import Grid from "@mui/material/Grid";
import Order from "@/component/share/Order";


export default function Cart() {
    const navigate = useNavigate();
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
                                    <Order orders={cart} onAction={() => {navigate('/checkout', {state: cart})}} checkout={false} />
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