import Avatar from "@mui/material/Avatar";
import Stack from "@mui/material/Stack";
import Typography from "@mui/material/Typography";

import type { IColor } from "@/interface/IColor";


export default function AvatarCaption({name, image, caption, size, filter}: Readonly<IColor>) {

    return (
        <Stack sx={{alignItems: 'center'}}>
            <Avatar 
                alt={name} 
                src={image} 
                sx={
                    {
                        filter: `brightness(${filter?.bright ?? 1}) grayscale(${filter?.flat ?? 0})`, 
                        width: `${size ?? ''}`, 
                        height: `${size ?? ''}`
                    }
                } 
            />
            <Typography variant={'caption'} sx={{fontStyle: 'italic', pt: '.25rem'}}>{caption}</Typography>
        </Stack>
    );
}