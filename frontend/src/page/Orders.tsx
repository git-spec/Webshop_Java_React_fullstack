import { useEffect, useState } from "react";
import axios from "axios";

import type { IOrder } from "@/interface/IOrder";
import CardOrder from "@/component/card/CardOrder";
import CardContentOrder from "@/component/card/CardContentOrder";


export default function Orders() {
    const [orders, setOrders] = useState<IOrder[]>();

    useEffect(() => {getOrders()}, []);

    const getOrders = () => {
        axios.get(`/api/orders/completed/${import.meta.env.VITE_EMAIL}`).then(res => {
            setOrders(res.data);
        }).catch(err => console.log(err));
    }

    return (
        <>
            {
                orders?.map((order: IOrder) => (
                    <CardOrder 
                        key={order.id} 
                        orderID={order.id} 
                        content={<CardContentOrder order={order} />} 
                    />
                ))}
        </>
    );
}