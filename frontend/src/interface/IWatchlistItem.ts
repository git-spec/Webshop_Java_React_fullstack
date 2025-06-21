import type { IProduct } from "./IProduct";


export interface IWatchlistItem {
    id: string;
    userEmail: string;
    product: IProduct;
}
