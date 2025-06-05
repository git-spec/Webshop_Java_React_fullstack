export const CurrencyType = {
    EUR: '€',
    GBP: '£',
    JPY: '¥',
    USD: '$'
} as const;
type CurrencyType = typeof CurrencyType[keyof typeof CurrencyType];