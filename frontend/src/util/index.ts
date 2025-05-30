import { Currency } from "../interface/Currency";


export function getCurrencyIcon(currency: string) {
    return Object.entries(Currency).map(arr => {return arr.includes(currency) && arr[1]});
}