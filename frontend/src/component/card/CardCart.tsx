import { useState, type ReactNode } from "react";
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import CardHeader from "@mui/material/CardHeader";
import ClearIcon from '@mui/icons-material/Clear';
import Grid from "@mui/material/Grid";

import ButtonAction from "../ButtonAction";
import CardActionArea from "@mui/material/CardActionArea";
import { Link } from "react-router-dom";
import type { IOrderItem } from "@/interface/IOrderItem";

type Props = {
    content: ReactNode;
    state: IOrderItem;
    deleteOrder?: (order: IOrderItem) => void;
    header?: ReactNode;
    media?:
        {
            name: string;
            path: string;
        }
    ;
};


export default function CardCart({header, media, content, state, deleteOrder}: Readonly<Props>) {
    const [visible, setVisible] = useState<boolean>(true);

    return (
        <>
            {
                visible && <Card sx={{position: 'relative'}}>
                            <Grid container columns={1} flexDirection={'row'} flexWrap={'nowrap'} spacing={0} justifyContent={'top'}>
                                <CardActionArea sx={{width: 'unset'}}>
                                    <Link 
                                        to={`/product/${state?.productID}`} 
                                        state={state} 
                                        style={{textDecoration: 'none'}}
                                    >
                                            <Grid sx={{ maxWidth: {xs: '100%', sm: '8rem'}}}>
                                                {header && <CardHeader>{header}</CardHeader>}
                                                {media && <CardMedia component={'img'} alt={media.name} image={media.path} />}
                                            </Grid>
                                    </Link>
                                </CardActionArea>
                                <Grid flexDirection={'row'} justifyContent={'space-between'} flexGrow={1}>
                                    <CardContent sx={{'&:last-child': {py: 1}, height: '100%'}}>
                                        {content}
                                    </CardContent>
                                </Grid>
                            </Grid>
                    {/* Delete Button */}
                    <CardActions sx={{position: 'absolute', right: 1, top: 1}}>
                        <ButtonAction 
                            variant={'text'} 
                            color={'inherit'} 
                            fitContent={true} 
                            value={<ClearIcon />} 
                            click={() => {setVisible(false); deleteOrder?.(state)}} 
                        />
                    </CardActions>
                </Card>
            }
        </>
    );
}
