import { UnitType } from "@/type/UnitType";
import { CurrencyType } from "@/type/CurrencyType";


export function getCurrencyIcon(currency: string) {
    return Object.entries(CurrencyType).map(arr => {return arr.includes(currency) && arr[1]});
}

export function getUnitIcon(unit: string) {
    return Object.entries(UnitType).map(arr => {return arr.includes(unit) && arr[1]});
}

export function getMajuscule(material: string) {
    const firstChar = material.slice(0, 1).toUpperCase();
    return firstChar.concat(material.slice(1).toLowerCase());
}

export function getUrl() { return window.location.pathname };