import { useState, type ReactNode, type SyntheticEvent } from "react";
import Box from "@mui/material/Box";
import Tabs from "@mui/material/Tabs";
import Tab from '@mui/material/Tab';

type Props = {
  tabs: {
    label: string;
    children: ReactNode;
  }[];
};

type TabPanelProps = {
  children?: ReactNode;
  index: number;
  value: number;
};

function CustomTabPanel(props: Readonly<TabPanelProps>) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
      style={{paddingTop: "2rem"}}
    >
      {value === index && <div>{children}</div>}
    </div>
  );
}

function a11yProps(index: number) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}


export default function TabsBasic(props: Readonly<Props>) {
    const [value, setValue] = useState(0);

    const handleChange = (event: SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    return(
        <>
            <Box sx={{ borderBottom: 1, borderColor: 'divider', }}>
            <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
              {
                props.tabs.map((tab, i) => <Tab sx={{fontFamily: "SourceSans3", fontWeight: "500", fontSize: "1.2rem", letterSpacing: ".06rem"}} key={tab.label} label={tab.label} {...a11yProps(i)} />)
              }
            </Tabs>
            </Box>
            {
              props.tabs.map(
                (tab, i) => (
                  <CustomTabPanel key={tab.label} value={value} index={i}>
                    {tab.children}
                  </CustomTabPanel>
                )
              )
            }
        </>
    );
}