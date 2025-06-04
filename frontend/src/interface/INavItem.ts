export interface INavItem {
    name: string;
    path?: string;
    icon?: string;
    subnav?: INavItem[];
    pl?: string;
};