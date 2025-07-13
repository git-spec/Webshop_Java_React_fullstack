import type { IUser } from "@/interface/IUser";
import type { IProduct } from "@/interface/IProduct";
import LayoutContainer from "@/component/share/LayoutContainer";
import TabsBasic from "@/component/TabsBasic";
import Watchlist from "./Watchlist";
import Orders from "./Orders";
import Profile from "./Profile";

type Props = {
    user: IUser | null | undefined;
    products: IProduct[] | undefined;
    watchlist: IProduct[] | undefined;
    onDelete: (watchlist: IProduct[]) => void;
};


export default function Dashboard({user, products, watchlist, onDelete}: Readonly<Props>) {
    const tabsItems = [
        {
            label: "Merkliste",
            children: <Watchlist watchlist={watchlist} onDelete={onDelete} />
        },
        {
            label: "Bestellungen",
            children: <Orders user={user} products={products} />
        },
        {
            label: "Profil",
            children: <Profile user={user} />
        }
    ];

    return (  
        <LayoutContainer padding={true}>
            <TabsBasic tabs={tabsItems} /> 
        </LayoutContainer>
    );
}