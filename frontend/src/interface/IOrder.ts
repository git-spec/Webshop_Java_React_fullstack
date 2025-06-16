import type { IProduct } from "./IProduct";


export interface IOrder {
    productID: string;
    color: string;
    amount: number;
    price: number;
    product: IProduct;
}