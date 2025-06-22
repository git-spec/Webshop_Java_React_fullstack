import type { IWatchlistItem } from "@/interface/IWatchlistItem";
import type { IUserAuth } from "@/interface/IUserAuth";
import type { IProduct } from "@/interface/IProduct";
import LayoutContainer from "@/component/share/LayoutContainer";
import TabsBasic from "@/component/TabsBasic";
import Watchlist from "./Watchlist";
import Orders from "./Orders";
import Profile from "./Profile";

type Props = {
    user: IUserAuth | null | undefined;
    products: IProduct[] | undefined;
    watchlist: IWatchlistItem[] | undefined;
    onDelete: (watchlist: IWatchlistItem[]) => void;
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
        <LayoutContainer>
            <TabsBasic tabs={tabsItems} /> 
        </LayoutContainer>
    );
}