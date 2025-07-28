export interface IUserAuth {
    sub: string;
    idToken: string;
    email: string;
    familyName: string;
    givenName: string;
    name: string;
    watchlist?: string[];
    [key: string]: any;
}