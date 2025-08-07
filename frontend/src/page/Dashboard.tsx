import type { IUser } from "@/interface/IUser";
import LayoutContainer from "@/component/share/LayoutContainer";
import TabsBasic from "@/component/TabsBasic";
import Watchlist from "./Watchlist";
import Orders from "./Orders";
import Profile from "./Profile";

type Props = {
    user: IUser | null | undefined;
};


export default function Dashboard({user}: Readonly<Props>) {
    const tabsItems = [
        {
            label: "Merkliste",
            children: <Watchlist />
        },
        {
            label: "Bestellungen",
            children: <Orders />
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