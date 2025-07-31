import type { IOrderItem } from "./IOrderItem";


export interface IOrder {
    id: string;
    cart: IOrderItem[];
    paypal: unknown;
}