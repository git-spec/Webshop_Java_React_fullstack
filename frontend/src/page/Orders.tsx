import { useEffect, useState } from "react";
import axios from "axios";

import type { IUserAuth } from "@/interface/IUserAuth";

type Props = {
    user: IUserAuth;
};


export default function Orders({user}: Readonly<Props>) {
    const [orders, setOrders] = useState();

    useEffect(() => {
        getOrders();
    }, []);

    const getOrders = () => {
        axios.get(`/api/orders/completed/${user.email}`).then(res => {
            setOrders(res.data);

            console.log('Orders: ', res.data);
            
        }).catch(err => console.log(err));
    }

    return (
        <p>Hallo</p>
    );
}