import type { IOrder } from "@/interface/IOrder";


export type CartContextType = {
  cart: IOrder[];
  updateCart: (cart: IOrder[]) => void;
};