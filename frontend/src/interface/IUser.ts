export interface IUser {
    email: any;
    sub?: any;
    gender?: any;
    givenName: any;
    familyName: any;
    address?: {
        street: any;
        postalCode: any;
        locality: any;
        region: any;
        country: any;
    };
    watchlist: string[];
 }