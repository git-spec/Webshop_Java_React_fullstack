export interface IUser {
    id?: string;
    email: string;
    sub?: string;
    gender?: string;
    givenName: string;
    familyName: string;
    address?: {
        street: string;
        postalCode: string;
        locality: string;
        region: string;
        country: string;
    };
    watchlist: string[];
 }