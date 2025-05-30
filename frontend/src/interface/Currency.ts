export const Currency = {
    EUR: '€',
    GBP: '£',
    JPY: '¥',
    USD: '$'
} as const;
type Currency = typeof Currency[keyof typeof Currency];