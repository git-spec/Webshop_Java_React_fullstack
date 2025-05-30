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
    state?: unknown;
};


function CardMain({header, media, content, actions, actionAreaPath, state}: Readonly<Props>) {
    const template = 
        <Fragment>
            {header && <CardHeader>{header}</CardHeader>}
            {media && <CardMedia component={'img'} alt={media.name} image={media.path} />}
            <CardContent>
                {content}
            </CardContent>
        </Fragment>
    ;

    return (
        <>
            <Card sx={{ minWidth: {xs: '100%', sm: 'auto'}, maxWidth: {sm: 180}}}>
                {actionAreaPath ? 
                        <CardActionArea>
                            <Link to={actionAreaPath} state={{state}} style={{textDecoration: 'none'}}>
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
