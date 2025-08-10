import LayoutContainer from "@/component/share/LayoutContainer";
import { Typography } from "@mui/material";

export default function Home() {

    return (
        <>
            <img alt="house in nature" src="/img/30b.jpg" style={{width: "100vw", objectFit: 'cover', display: 'block'}}></img>
            <div style={{marginTop: "2rem"}}>
                <LayoutContainer>
                <Typography component={'h1'} sx={{textAlign: 'center'}} fontFamily={'SourceSans3'} fontWeight={'600'} fontSize={'3rem'} color="primary">Leben mit der Natur, statt entgegen.</Typography>
                <Typography component={'h1'} sx={{textAlign: 'justify', mt: 2}} >Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.  

    Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer</Typography>

                </LayoutContainer> 
            </div>       
        </>
    );
}