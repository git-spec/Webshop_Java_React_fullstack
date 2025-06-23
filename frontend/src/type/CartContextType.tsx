import type { IOrderItem } from "@/interface/IOrderItem";


export type CartContextType = {
  cart: IOrderItem[];
  updateCart: (cart: IOrderItem[]) => void;
};