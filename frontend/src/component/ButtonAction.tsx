import Button from "@mui/material/Button";
import type { ReactNode } from "react";

type Props = {
    key?: string | number;
    value: string | ReactNode;
    color?: "inherit" | "primary" | "secondary" | "success" | "error" | "info" | "warning";
    variant?: "outlined" | "text" | "contained";
    disableRipple?: boolean; 
    click?: () => void;
    href?: string;
    fitContent?: boolean;
};

export default function ButtonAction(props: Readonly<Props>) {

    return (
        <>
            {
                props.click && 
                    <Button 
                        key={props.key ?? ''}
                        variant={props.variant ?? 'outlined'} 
                        size="small" 
                        color={props.color} 
                        sx={
                            {
                                fontFamily: 'SourceSans3',
                                 minWidth: `${props.fitContent ? 'fit-content' : '100%'}`
                            }
                        } 
                        onClick={props.click}
                        disableRipple={props.disableRipple ?? false}
                    >
                        {props.value}
                    </Button>
            }
            {
                props.href && 
                    <Button 
                        variant={props.variant ?? 'outlined'} 
                        size="small" sx={{fontFamily: 'SourceSans3'}} 
                        href={props.href}>{props.value}
                    </Button>
            }
        </>
    );
}