export interface IColor {
    name: string;
    image: string;
    caption?: string;
    size?: string;
    filter?: {
        bright?: string;
        flat?: string;
    };
}