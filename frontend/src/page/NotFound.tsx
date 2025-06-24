import ButtonAction from "@/component/ButtonAction";
import LayoutContainer from "@/component/share/LayoutContainer";
import NoteBig from "@/component/share/NoteBig";
import { Stack } from "@mui/material";


export default function NotFound() {
    const redirectMessage = 'Homepage';

     return (
        <LayoutContainer padding={true}>
            <Stack 
                flexDirection={'column'} 
                justifyContent={'center'} 
                alignItems={'center'} 
                gap={4}
            >
                <NoteBig value={'404 | Page not found'} />
                <ButtonAction 
                    value={redirectMessage} 
                    color="primary" 
                    href="/" fitContent={true} 
                />
            </Stack>
        </LayoutContainer>
     );
}