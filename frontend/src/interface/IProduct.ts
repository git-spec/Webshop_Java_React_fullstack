export interface IProduct {
    id: string;
    number: string;
    name: string;
    manufacturer: string;
    category: string;
    group: string;
    family: string;
    features: {
        dimension: {
            width: {
                number: number;
                unit: string;
            }
            length: {
                number: number;
                unit: string;
            }
            height: {
                number: number;
                unit: string;
            }
        };
        weight: {
            number: number;
            unit: string;
        },
        materials: string[];
        colors: string[];
    },
    info: string;
    description: string;
    images: {
        small: string[];
        medium: string[];
        large: string[];
    };
    price: number;
    currency: string;
    amount: number;
}