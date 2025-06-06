import { UnitType } from "@/type/UnitType";


export interface IMeasure {
    number: number;
    unit: typeof UnitType;
}