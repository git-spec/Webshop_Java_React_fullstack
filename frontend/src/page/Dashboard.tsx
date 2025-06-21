import type { IWatchlistItem } from "@/interface/IWatchlistItem";
import type { IUserAuth } from "@/interface/IUserAuth";
import LayoutContainer from "@/component/share/LayoutContainer";
import TabsBasic from "@/component/TabsBasic";
import Watchlist from "./Watchlist";
import Orders from "./Orders";

type Props = {
    user: IUserAuth;
    watchlist: IWatchlistItem[] | undefined;
    onDelete: (watchlist: IWatchlistItem[]) => void;
};


export default function Dashboard({user, watchlist, onDelete}: Readonly<Props>) {
    const tabsItems = [
        {
            label: "Merkliste",
            children: <Watchlist watchlist={watchlist} onDelete={onDelete} />
        },
        {
            label: "Bestellungen",
            children: <Orders user={user} />
        }
    ];

    return (  
        <LayoutContainer>
            <TabsBasic tabs={tabsItems} /> 
        </LayoutContainer>
    );
}