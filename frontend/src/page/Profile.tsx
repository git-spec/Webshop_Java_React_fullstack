import Box from "@mui/material/Box";

import PersonDetailsForm from "@/component/PersonDetailsForm";
import type { IUserAuth } from "@/interface/IUserAuth";
import { Stack } from "@mui/material";

type Props = {
    user: IUserAuth | undefined;
};


export default function Profile({user}: Readonly<Props>) {
    
    return (
        <Stack justifyContent={'center'} alignItems={'center'}>
            <Box sx={{width: {sx: '100%', sm: '50%'}}}>
                <PersonDetailsForm props={{firstname: user?.givenName ?? '', lastname: user?.familyName ?? ''}} />
            </Box>
        </Stack>
    );
}