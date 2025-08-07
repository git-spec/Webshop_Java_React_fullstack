import { type ReactNode } from "react";
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";

type Props = {
    orderID: string;
    content: ReactNode;
    media?:
        {
            name: string;
            path: string;
        }
    ;
};


export default function CardOrder({orderID, media, content}: Readonly<Props>) {

    return (
        <Card sx={{position: 'relative', mb: 4}}>
            <Grid container columns={1} flexDirection={'row'} spacing={2} mx={2} mt={1} alignItems={'start'}>
                <Typography gutterBottom variant="h4" color={'primary'} fontFamily={'SourceSans3'} fontSize={'1.2rem'}>
                    Bestellnummer:
                </Typography>
                <Typography variant="body2" sx={{color: 'text.secondary', mb: '.25rem'}} fontFamily={'SourceSans3'} fontWeight={300} fontSize={'1.2rem'}>
                    {orderID}
                </Typography>
            </Grid>
            <Grid container columns={1} flexDirection={'row'} spacing={0} justifyContent={'top'}>
                <Grid sx={{ maxWidth: '3rem'}}>
                    {media && <CardMedia component={'img'} alt={media.name} image={media.path} />}
                </Grid>
                <Grid flexDirection={'row'} justifyContent={'space-between'} flexGrow={1} mb={1}>
                    <CardContent sx={{'&:last-child': {py: 1}, height: '100%'}}>
                        {content}
                    </CardContent>
                </Grid>
            </Grid>
        </Card>
    );
}
