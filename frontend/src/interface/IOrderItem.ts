import type { IProduct } from "./IProduct";


export interface IOrderItem {
    productID: string;
    color: string;
    amount: number;
    price: number;
    product: IProduct;
}