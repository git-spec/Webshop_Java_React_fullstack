import type { INavItem } from "../src/interface/INavItem";


export const sidebarItems: INavItem[] = [
    {
        name: "Home",
        path: "/"
    },
    {
        name: "Sitzgelegenheiten",
        subnav: [
            {
                name: "Stühle",
                path: "/furniture/seating/chair",
                pl: "1rem"
            },
            {
                name: "Hocker",
                path: "/furniture/seating/stool",
                pl: "1rem"
            },
            {
                name: "Sofas",
                path: "/furniture/seating/sofa",
                pl: "1rem"
            }
        ]
    },
    {
        name: "Aufbewahrungen",
        subnav: [
            {
                name: "Regale",
                path: "/furniture/storage/shelve",
                pl: "1rem"
            },
            {
                name: "Schränke",
                path: "/furniture/storage/cupboard",
                pl: "1rem"
            },
            {
                name: "Kommoden",
                path: "/furniture/storage/comode",
                pl: "1rem"
            },
            {
                name: "Anrichten",
                path: "/furniture/storage/sideboard",
                pl: "1rem"
            }
        ]
    },
    {
        name: "Philosophie",
        path: "/"
    }
    // {
    //     name: "Produkte",
    //     path: "/products",
    //     subnav: [
    //         {
    //             name: "Möbel",
    //             path: "/products/furniture",
    //             subnav: [
    //                 {
    //                     name: "Sitzgelegenheiten",
    //                     path: "/products/furniture/seating",
    //                     subnav: [
    //                         {
    //                             name: "Stühle",
    //                             path: "/products/furniture/seating/chairs",
    //                             pl: "3rem"
    //                         },
    //                         {
    //                             name: "Hocker",
    //                             path: "/products/furniture/seating/stools",
    //                             pl: "3rem"
    //                         },
    //                         {
    //                             name: "Sofas",
    //                             path: "/products/furniture/seating/sofas",
    //                             pl: "3rem"
    //                         }
    //                     ],
    //                     pl: "2rem"
    //                 },
    //                 {
    //                     name: "Aufbewahrungen",
    //                     path: "/products/furniture/storage",
    //                     subnav: [
    //                         {
    //                             name: "Regale",
    //                             path: "/products/furniture/storage/shelves",
    //                             pl: "3rem"
    //                         },
    //                         {
    //                             name: "Schränke",
    //                             path: "/products/furniture/storage/cupboards",
    //                             pl: "3rem"
    //                         },
    //                         {
    //                             name: "Kommoden",
    //                             path: "/products/furniture/storage/comodes",
    //                             pl: "3rem"
    //                         },
    //                         {
    //                             name: "Anrichten",
    //                             path: "/products/furniture/storage/sideboards",
    //                             pl: "3rem"
    //                         }
    //                     ],
    //                     pl: "2rem"
    //                 }
    //             ],
    //             pl: "1rem"
    //         },
    //         {
    //             name: "Bekleidung",
    //             path: "/products/clothes",
    //             pl: "1rem"
    //         },
    //         {
    //             name: "Schuhe",
    //             path: "/products/shoes",
    //             pl: "1rem"
    //         },
    //         {
    //             name: "Kosmetika",
    //             path: "/products/cosmetics",
    //             pl: "1rem"
    //         }

    //     ]
    // },
];