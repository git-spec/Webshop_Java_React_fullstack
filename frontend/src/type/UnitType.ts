export const UnitType = {
    MM: 'mm',
    CM: 'cm',
    M: 'm',
    G: 'g',
    KG: 'kg'
} as const;
type UnitType = typeof UnitType[keyof typeof UnitType];