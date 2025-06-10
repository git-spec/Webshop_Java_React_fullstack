import type { IProduct } from "./IProduct";


export interface ICart extends IProduct {
    amount: number;
    color: string;
}