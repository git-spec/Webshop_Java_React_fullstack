import type { IProduct } from "./IProduct";


export interface IOrderItem {
    productID: string;
    productName: string;
    color: string;
    amount: number;
    currency: string;
    price: number;
    product: IProduct;
}