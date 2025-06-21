import {Link} from "react-router-dom";
import { useState, type ReactNode } from "react";
import Grid from "@mui/material/Grid";
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import CardActionArea from "@mui/material/CardActionArea";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import CardHeader from "@mui/material/CardHeader";
import ButtonAction from "../ButtonAction";
import ClearIcon from '@mui/icons-material/Clear';

type Props<T> = {
    header?: ReactNode;
    media?:
        {
            name: string;
            path: string;
        }
    ;
    content: ReactNode;
    actions?: ReactNode;
    deleteButton?: boolean;
    iconButton?: boolean;
    icon?: ReactNode;
    path?: string;
    state: T;
    onAction?: (state: T) => void;
};


function CardMain<T>(
    {
        header, 
        media, 
        content, 
        actions, 
        path, 
        state, 
        deleteButton, 
        iconButton, 
        icon, 
        onAction
    }: Readonly<Props<T>>) {
    const [visible, setVisible] = useState<boolean>(true);
    const template = 
        <Grid container columns={1} flexDirection={"column"} spacing={0} height={'100%'} justifyContent={'top'}>
            <Grid>
                {header && <CardHeader>{header}</CardHeader>}
                {media && <CardMedia component={'img'} alt={media.name} image={media.path} />}
            </Grid>
            <Grid flexDirection={"column"} justifyContent={'space-between'} flexGrow={1}>
                <CardContent sx={{'&:last-child': {py: 1}, height: '100%'}}>
                    {content}
                </CardContent>
            </Grid>
        </Grid>
    ;

    return (
        <>
            {
                visible && 
                    <Card sx={{ minWidth: {xs: '100%', sm: 'auto'}, maxWidth: {sm: 180}, minHeight: '18rem', position: "absolute"}}>
                    {path ? 
                            <CardActionArea sx={{height: '100%'}}>
                                <Link 
                                    to={path} 
                                    state={state} 
                                    style={{textDecoration: 'none', display: 'block', height: 'inherit'}}
                                >
                                {template}
                                </Link>
                            </CardActionArea>
                        :
                            template
                    }
                    {/* Delete Button */}
                    {
                        deleteButton && 
                            <CardActions sx={{position: 'absolute', right: 1, top: 1, mt: 'auto'}}>
                            <ButtonAction 
                                variant={'text'} 
                                color={'inherit'} 
                                fitContent={true} 
                                value={<ClearIcon />} 
                                click={() => {setVisible(false); onAction?.(state)}} 
                                />
                            </CardActions>
                    }
                    {/* Icon Button */}
                    {
                        iconButton && 
                            <CardActions sx={{position: 'absolute', right: 1, top: 1}}>
                                <ButtonAction 
                                    variant={'text'} 
                                    color={'inherit'} 
                                    fitContent={true} 
                                    value={icon} 
                                    click={() => {onAction?.(state)}} 
                                />
                            </CardActions>
                    }
                    {actions && <CardActions>{actions}</CardActions>}
                </Card>
            }
        </>
    );
}

export default CardMain;
