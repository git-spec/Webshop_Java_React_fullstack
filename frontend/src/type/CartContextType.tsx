import type { ICart } from "@/interface/ICart";


export type CartContextType = {
  cart: ICart[];
  updateCart: (cart: ICart[]) => void;
};