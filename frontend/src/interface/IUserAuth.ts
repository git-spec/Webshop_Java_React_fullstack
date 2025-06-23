export interface IUserAuth {
    sub: string;
    idToken: string;
    email: string;
    familyName: string;
    givenName: string;
    name: string;
    [key: string]: any;
}