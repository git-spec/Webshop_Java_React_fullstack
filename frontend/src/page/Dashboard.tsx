import LayoutContainer from "@/component/share/LayoutContainer";
import TabsBasic from "@/component/TabsBasic";
import Watchlist from "./Watchlist";
import type { IWatchlistItem } from "@/interface/IWatchlistItem";

type Props = {
    watchlist: IWatchlistItem[] | undefined;
    onDelete: (watchlist: IWatchlistItem[]) => void;
};


export default function Dashboard({watchlist, onDelete}: Readonly<Props>) {
    const tabsItems = [
        {
            label: "Merkliste",
            children: <Watchlist watchlist={watchlist} onDelete={onDelete} />
        }
    ];

    return (  
        <LayoutContainer>
            <TabsBasic tabs={tabsItems} /> 
        </LayoutContainer>
    );
}