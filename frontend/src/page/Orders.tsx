import { useEffect, useState } from "react";
import axios from "axios";

import type { IUserAuth } from "@/interface/IUserAuth";
import type { IProduct } from "@/interface/IProduct";
import CardOrder from "@/component/card/CardOrder";
import CardContentOrder from "@/component/card/CardContentOrder";
import type { IOrder } from "@/interface/IOrder";

type Props = {
    user: IUserAuth | null | undefined;
    products: IProduct[] | undefined;
};


export default function Orders({user, products}: Readonly<Props>) {
    const [orders, setOrders] = useState<IOrder[]>();

    useEffect(() => {
        getOrders();
    }, []);

    const getOrders = () => {
        // axios.get(`/api/orders/completed/${import.meta.dev.VITE_EMAIL}`).then(res => {
        axios.get(`/api/orders/completed/${"ikfischer@yahoo.de"}`).then(res => {
            setOrders(res.data);
        }).catch(err => console.log(err));
    }

    return (
        <>
            {
                products && orders?.map((order: IOrder) => (
                    <CardOrder 
                        key={order.id} 
                        orderID={order.id} 
                        content={<CardContentOrder products={products} order={order} />} 
                    />
                ))}
        </>
    );
}