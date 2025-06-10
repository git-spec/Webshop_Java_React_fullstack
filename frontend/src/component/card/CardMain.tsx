import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import CardActionArea from "@mui/material/CardActionArea";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import CardHeader from "@mui/material/CardHeader";

import {Link} from "react-router-dom";
import type { ReactNode } from "react";
import Grid from "@mui/material/Grid";

type Props = {
    header?: ReactNode;
    media?:
        {
            name: string;
            path: string;
        }
    ;
    content: ReactNode;
    actions?: ReactNode;
    actionAreaPath?: string;
    state?: unknown;
};


function CardMain({header, media, content, actions, actionAreaPath, state}: Readonly<Props>) {
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
            <Card sx={{ minWidth: {xs: '100%', sm: 'auto'}, maxWidth: {sm: 180}, minHeight: '20rem'}}>
                {actionAreaPath ? 
                        <CardActionArea sx={{height: '100%'}}>
                            <Link 
                                to={actionAreaPath} 
                                state={state} 
                                style={{textDecoration: 'none', display: 'block', height: 'inherit'}}
                            >
                            {template}
                            </Link>
                        </CardActionArea>
                    :
                        template
                }
            </Card>
            {actions && <CardActions>{actions}</CardActions>}
        </>
    );
}

export default CardMain;
