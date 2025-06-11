import {useState, type ReactNode} from 'react';
import Accordion, {
  type AccordionSlots,
  accordionClasses,
} from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails, {
  accordionDetailsClasses,
} from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Fade from '@mui/material/Fade';

type Props = {
    sumary: string;
    Component: React.ComponentType | ReactNode;
};

export default function AccordionExpand({sumary, Component}: Readonly<Props>) {
  const [expanded, setExpanded] = useState(false);

  const handleExpansion = () => {
    setExpanded((prevExpanded) => !prevExpanded);
  };

  const handleComponentType = (value: React.ReactNode | React.ComponentType) => {
        if (typeof value === 'function' || (typeof value === 'object' && value && 'prototype' in value)) {
            // value is a component
            const Component = value as React.ComponentType;
            return <Component />;
        }
        // value is a ReactNode
        return value;
    }

  return (
        <Accordion
            expanded={expanded}
            onChange={handleExpansion}
            slots={{ transition: Fade as AccordionSlots['transition'] }}
            slotProps={{ transition: { timeout: 400 } }}
            sx={[
            expanded
                ? {
                    [`& .${accordionClasses.region}`]: {
                    height: 'auto',
                    },
                    [`& .${accordionDetailsClasses.root}`]: {
                    display: 'block',
                    },
                }
                : {
                    [`& .${accordionClasses.region}`]: {
                    height: 0,
                    },
                    [`& .${accordionDetailsClasses.root}`]: {
                    display: 'none',
                    },
                },
            ]}
        >
            <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls="panel1-content"
            id="panel1-header"
            >
            <Typography component="span">{sumary}</Typography>
            </AccordionSummary>
            <AccordionDetails>
                {handleComponentType(Component)}
            </AccordionDetails>
        </Accordion>
  );
}
