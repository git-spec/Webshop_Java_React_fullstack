import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import CardActionArea from "@mui/material/CardActionArea";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import CardHeader from "@mui/material/CardHeader";

import {Link} from "react-router-dom";
import type { ReactNode } from "react";
import { Fragment } from "react";

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
    state: unknown;
};


function CardMain({header, media, content, actions, actionAreaPath, state}: Readonly<Props>) {
    const template = 
        <Fragment>
            {header && <CardHeader props={header} />}
            {media && <CardMedia component={'img'} alt={media.name} />}
            <CardContent>
                {content}
            </CardContent>
        </Fragment>
    ;

    return (
        <>
            <Card sx={{ minWidth: {xs: '100%', sm: 'auto'}, maxWidth: {sm: 180}}}>
                {actionAreaPath ? <CardActionArea>
                            <Link to={actionAreaPath} state={{state}} style={{textDecoration: 'none'}}>
                            {template}
                                {/* <CardContent>
                                    <Typography gutterBottom variant="h5" component="div" color={'#765638'}>
                                        {recipe.name}
                                    </Typography>
                                    <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                                        {recipe.description}
                                    </Typography>
                                    <Box component={'div'} sx={{display: 'flex', alignItems: 'center'}}>
                                        <Typography variant="caption" fontSize={'inherit'} sx={{color: 'text.secondary', paddingTop: '2px'}}>
                                            {recipe.duration} Min.
                                        </Typography>
                                        <TimerOutlinedIcon fontSize={'small'} sx={{marginLeft: '.2rem', color: '#765638'}}></TimerOutlinedIcon>
                                    </Box>
                                </CardContent> */}
                            </Link>
                        </CardActionArea>
                    :
                        template
                }
            </Card>
            {actions && <CardActions props={actions} />}
        </>
    );
}

export default Card;
